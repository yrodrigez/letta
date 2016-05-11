package es.uvigo.esei.daa.letta.controllers;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import es.uvigo.esei.daa.letta.DAO.DAOException;
import es.uvigo.esei.daa.letta.DAO.EventDAO;
import es.uvigo.esei.daa.letta.entities.Event;
import es.uvigo.esei.daa.letta.entities.Event.Categories;
import es.uvigo.esei.daa.letta.entities.Image;
import es.uvigo.esei.daa.letta.entities.Image.ExtensionTypes;

import javax.servlet.http.HttpServletRequest;

public class EventController implements Controller<Event>{

	private EventDAO dao;
	
	public EventController(){
		this.dao = new EventDAO();
	}
	@Override
	public List<Event> list() throws DAOException {
		return this.dao.list();
	}
	
	public List<Event> getFeatured() throws DAOException {
		return this.dao.getFeatured();
	}
	
	public List<Event> getPopular() throws DAOException {
		return this.dao.getPopular();
	}

	public List<Event> getSearch(String text) throws DAOException {
		return this.dao.getSearch(text);
	}

	@Override
	public Event get(String id) throws DAOException{
		return this.dao.get(id);
	}

	@Override
	public void delete(String id) throws DAOException{
		this.dao.delete(id);
	}
	
	public Event modify(
			String id,
			String title,
			String description,
			String place,
			int num_assistants,
			Date start,
			Date end,
			String user_id,
			Categories category
	) throws DAOException {
		Event event = this.dao.get(id);
		event.setTitle(title);
		event.setDescription(description);
		event.setPlace(place);
		event.setNum_assistants(num_assistants);
		event.setStart(start);
		event.setEnd(end);
		event.setUser_id(user_id);
		event.setCategory(category);
		this.dao.modify(event);
		return event;
	}
	
	public Image getImage(String id) throws DAOException {
		return this.dao.getImage(id);
	}

	
	public Event add(String title, String description, String place, int num_assistants,
		Date start, Date end, Categories category, String img, String img_ext, HttpServletRequest request)
	throws DAOException, NotLoggedInException{
		String user_id = getUser(request);
		byte[] img2 = null;
		ExtensionTypes img_ext2 = null;
		if(img != null){
			img2 = decodeBase64(img);
		}
		if(img_ext != null){
			img_ext2 = ExtensionTypes.valueOf(img_ext);
		}
		return this.dao.add(title, description, place, num_assistants, start, end, user_id, category, img2, img_ext2);
	}


	public void assist(int id, HttpServletRequest request) throws DAOException, NotLoggedInException {
		String user = getUser(request);

		this.dao.attend(id, user);
	}
	public List<Event> getAssistEvents(HttpServletRequest request) throws DAOException, NotLoggedInException {
		String assist = getUser(request);
		return this.dao.getAssistEvents(assist);
	}
	
	private String getUser(HttpServletRequest request) throws NotLoggedInException{
		String user = (String) request.getSession().getAttribute("login");
		if(user == null) throw new NotLoggedInException();
		return user;
	}
	
	private final byte[] decodeBase64(String text) {
		return Base64.getDecoder().decode(text.getBytes());
	}
}
