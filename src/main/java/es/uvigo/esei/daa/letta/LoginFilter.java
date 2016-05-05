package es.uvigo.esei.daa.letta;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.uvigo.esei.daa.letta.DAO.DAOException;
import es.uvigo.esei.daa.letta.DAO.UserDAO;

/**
 * Security filter that implements a login protocol based on the HTTP Basic
 * Authentication protocol. In this case, the login and password can be provided
 * as plain request parameters or as a cookie named "token" that should contain
 * both values in the same format as HTTP Basic Authentication
 * ({@code base64(login + ":" + password)}).
 * 
 * @author Miguel Reboiro Jato
 *
 */
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {
	private static final String LOGOUT_PATH = "/logout";
	private static final String LOGIN_PATH = "/login";
	private static final String REST_PATH = "/rest";
	private static final String[] PUBLIC_PATHS = new String[] {
		"/index.html", // Add the paths that can be publicly accessed (e.g. /js, /css...)
		"/js",
		"/fonts",
		"/img",
		"/css"
	};

	@Override
	public void doFilter(
		ServletRequest request, 
		ServletResponse response,
		FilterChain chain
	) throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		try {
			if (isLogoutPath(httpRequest)) {
				destroySession(httpRequest);
				removeTokenCookie(httpRequest, httpResponse);
				redirectToIndex(httpRequest, httpResponse);
			} else if (isPublicPath(httpRequest) || checkToken(httpRequest) || isRestPath(httpRequest)) {
				chain.doFilter(request, response);
			} else if (isLoginPath(httpRequest)){
				continueWithRedirect(httpRequest, httpResponse, checkLogin(httpRequest, httpResponse));
			/*} else if (isRestPath(httpRequest)) {
				destroySession(httpRequest);
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);*/
			} else {
				destroySession(httpRequest);
				redirectToIndex(httpRequest, httpResponse);
			}
		} catch (IllegalArgumentException iae) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
		} catch (DAOException e) {
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}
	
	private boolean isLogoutPath(HttpServletRequest request) {
		return request.getServletPath().equals(LOGOUT_PATH);
	}
	
	private boolean isLoginPath(HttpServletRequest request) {
		return request.getServletPath().equals(LOGIN_PATH);
	}
	
	private boolean isPublicPath(HttpServletRequest request) {
		for (String path : PUBLIC_PATHS) {
			if (request.getServletPath().startsWith(path))
				return true;
		}
		
		return false;
	}
	
	private boolean isRestPath(HttpServletRequest request) {
		return request.getServletPath().startsWith(REST_PATH);
	}

	private void redirectToIndex(
		HttpServletRequest request,
		HttpServletResponse response
	) throws IOException {
		response.sendRedirect(request.getContextPath());
	}

	private void continueWithRedirect(
		HttpServletRequest request,
		HttpServletResponse response,
		boolean successLogin
	) throws IOException {
		/*String redirectPath = request.getRequestURI();
		if (request.getQueryString() != null)
			redirectPath += "?" + request.getQueryString();
		response.sendRedirect(redirectPath);*/
		String redirectPath = request.getContextPath();
		if (request.getQueryString() != null)
			redirectPath += "?" + request.getQueryString();
		if (!successLogin){
			String nexo = "?";
			if(request.getQueryString() != null)
				nexo = "&";
			redirectPath += nexo + "error=1";
		}
		response.sendRedirect(redirectPath);
	}
	
	private void removeTokenCookie(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		final Cookie cookie = getTokenCookie(request);
		if (cookie != null) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}
	
	private void destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
	private boolean checkLogin(
		HttpServletRequest request, 
		HttpServletResponse response
	) throws DAOException {
		final String login = request.getParameter("login");
		final String password = request.getParameter("password");
		
		if (login != null && password != null) {
			final UserDAO dao = new UserDAO();
			
			if (dao.checkLogin(login, password)) {
				final Credentials credentials = new Credentials(login, password);
				
				response.addCookie(new Cookie("token", credentials.toToken()));
				request.getSession().setAttribute("login", login);
				
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	private Cookie getTokenCookie(HttpServletRequest request) {
		final Cookie[] cookies = Optional.ofNullable(request.getCookies())
			.orElse(new Cookie[0]);
		
		for (Cookie cookie : cookies) {
			if ("token".equals(cookie.getName())) {
				return cookie;
			}
		}
		
		return null;
	}
	
	private boolean checkToken(HttpServletRequest request)
	throws DAOException, IllegalArgumentException {
		final Cookie cookie = getTokenCookie(request);
		
		if (cookie != null) {
			final Credentials credentials = new Credentials(cookie.getValue());
			
			final UserDAO dao = new UserDAO();
			
			if (dao.checkLogin(credentials.getLogin(), credentials.getPassword())) {
				request.getSession().setAttribute("login", credentials.getLogin());
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}
	
	private static class Credentials {
		private final String login;
		private final String password;
		
		public Credentials(String token) {
			final String decodedToken = decodeBase64(token);
			final int colonIndex = decodedToken.indexOf(':');
			
			if (colonIndex < 0 || colonIndex == decodedToken.length()-1) {
				throw new IllegalArgumentException("Invalid token");
			}
			
			this.login = decodedToken.substring(0, colonIndex);
			this.password = decodedToken.substring(colonIndex + 1);
		}
		
		public Credentials(String login, String password) {
			this.login = requireNonNull(login, "Login can't be null");
			this.password = requireNonNull(password, "Password can't be null");
		}
		
		public String getLogin() {
			return login;
		}
		
		public String getPassword() {
			return password;
		}
		
		public String toToken() {
			return encodeBase64(this.login + ":" + this.password);
		}
		
		private final static String decodeBase64(String text) {
			return new String(Base64.getDecoder().decode(text.getBytes()));
		}
		
		private final static String encodeBase64(String text) {
			return Base64.getEncoder().encodeToString(text.getBytes());
		}
	}
}
