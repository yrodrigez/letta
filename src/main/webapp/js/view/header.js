function drawHeader(){
	var divs = "<div class='container-fluid'>\
	<div class='navbar-header'>\
		<button type='button' class='navbar-toggle collapsed'\
			data-toggle='collapse' data-target='#bs-example-navbar-collapse-1'\
			aria-expanded='false'>\
			<span class='sr-only'>Toggle navigation</span> <span\
				class='icon-bar'></span> <span class='icon-bar'></span> <span\
				class='icon-bar'></span>\
		</button>\
		<a class='navbar-brand' href='#'>Le<i style='color: green;'>t</i>ta\
		</a>\
	</div>\
	<div class='collapse navbar-collapse'\
		id='bs-example-navbar-collapse-1'>\
		<ul class='nav navbar-nav'>\
			<li class='letta-descubre-blinker active'><a href='#'>Descubre</a></li>\
			<li><a href='newEvent.html'>Crea un evento</a></li>\
		</ul>\
		<div class='navbar-form navbar-left form-group'>\
			<input type='text' id='buscar' class='form-control'\
				placeholder='Búsqueda...' value='' />\
			<button type='button' onClick='showSearch()'\
				class='btn btn-default'>Buscar</button>\
		</div>\
		<ul class='nav navbar-nav navbar-right'>";
		var user = getCurrentUser();
		if (user == "")
			divs+="<li><a href='#'>Regístrate</a></li>\
			<li class='letta-hide-on-mobile dropdown'>\
				<a href='#' class='dropdown-toggle'\
				data-toggle='dropdown' role='button' aria-haspopup='true'\
				aria-expanded='false'>Login <span class='caret'></span></a>\
				<ul class='letta-dropdown-login dropdown-menu'>\
					<li>\
						<form class='form-signin' action = 'login' method = 'post'>\
							<h2 class='form-signin-heading'>Loguéate</h2>\
							<label for='login' class='sr-only'>Login</label> <input\
								id='login' name='login' class='form-control'\
								placeholder='Login' required='' autofocus=''\
								type='login'> <label for='password' class='sr-only'>Contraseña</label>\
							<input id='password' name='password' class='form-control'\
								placeholder='Contraseña' required='' type='password'>\
							<div class='checkbox'>\
								<label> <input value='remember-me' type='checkbox'>\
									Recuérdame\
								</label>\
							</div>\
							<button class='btn btn-default' type='submit' onclick='doLogin()'>Entrar</button>\
						</form>\
					</li>\
				</ul>\
			</li>";
		else divs +="<li><a href='#'><i class='glyphicon glyphicon-user'></i>&nbsp;&nbsp;" + user + "</a></li>\
				<li><a href='logout'><i class='glyphicon glyphicon-log-out'></i>&nbsp;&nbsp;Cerrar sesión</a></li>";
		divs+="</ul>\
	</div>\
</div>";
	
	$("#header").append(divs);
}

function doLogin(){
	$("#password").val(md5($("#password").val()));
}

function getCurrentUser(){
	var decodedToken = getToken();
	if (decodedToken != "")
		return decodedToken.split(":")[0];
	else return "";
}

function getToken(){
	var token = getCookie("token");
	if(token != "")
		return(atob(token.split('"').join("")));
	else return "";
}

function getCookie(name) {
    var value = "; " + document.cookie;
    var parts = value.split("; " + name + "=");
    if (parts.length == 2) return parts.pop().split(";").shift();
    else return "";
}
