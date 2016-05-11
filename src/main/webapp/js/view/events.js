function createEventThumbnail(event){
    var asistencia = asistenciaPorcentaje(event);
    var img_path = "img/"+event.category+".png";
    if(event.image)
    	img_path = 'rest/events/'+event.id+'/image';
        

    return'<div class="col-sm-6 col-md-4">\
        <div class="letta-event-thumbnail thumbnail">\
           <div class="letta-thumbnail-buttons-container">\
           <div class="overlay">\
                    '+createButtonAsistir(event.id)+'\
                    '+createButtonVerMas(event.id)+'\
                </div>\
                <img class = "letta-image-sizer" src="' + img_path + '" alt="un evento"/>\
           </div>\
           <div class="caption">\
                <h3>'+ event.title +'</h3>\
                <p>'+ event.description.substring(0, 47) +'...</p>\
                <p>Aforo máximo: '+event.num_assistants+'\
                <br/>Completado:'+ asistencia +'% </p>\
           </div>\
        </div>\
    </div>';
}

function createButtonVerMas(id) {
    return '<a onclick="showEventDetails('+ id +')" class="btn btn-default" role="button">Ver mas</a>';
}

function createButtonAsistir(id) {
    return '<a id="event-' +id+ '" onclick="attend('+ id +')" class="btn btn-primary" role="button">Asistir</a>';
}

function changeAttendButton() {
    getAttendance(function (events) {
        $.each(events, function (key, event) {
            $("#event-"+event.id).text("Asistiendo").removeClass("btn-primary").addClass("btn-info");
        });
    });
}

function asistenciaPorcentaje(event){
    return 0;
}

function addPopularEvent(event){
    $('#proximos-eventos').append(createEventThumbnail(event));
}

function addEventToList(event,cont){
	if(cont%2==0){
		$('#view-container').append(createSearchResultRigth(event));
	}else{
		$('#view-container').append(createSearchResultLeft(event));
	}
}

function formSearchtoText(){
    return $('#buscar').val();
}

function vaciarMain() {
    document.getElementById("main").innerHTML="";
}

function attend(id ) {
    daoAttend(id, function () {
        alertify.success("Te acabas de registrar en: "+ id +"...");
        changeAttendButton();
    }, function () {
        alertify.error("Error creando asistencia...");
    });
}

function showEventDetails(id) {
    vaciarMain();
    getEvent(id, function (event) {
        var img_path = "img/"+event.category+".png";
        if(event.image)
            img_path = 'rest/events/'+event.id+'/image';
        $("#main").append(
            "<div class='letta-main-container container-fluid letta-container-fluid letta-view-event-container'>"+
                "<div class='row'>" +
                    "<div class='col-md-12 letta-view-event-imgcover' style='background-image: url("+img_path+");'>" +
                        "<div class='row'>"+
                            "<div class ='col col-md-4 col-md-offset-4 text-center view-event-title'>"+ event.title+"</div>"+
                        "</div>"+
                    "</div>"+
                    "<div class='col-md-10 col-md-offset-1 letta-view-event-description'>"+
                        "<p>"+event.description+"</p>"+
                        "<p><span style='font-weight: bold'>En: </span>"+event.place+"</p>"+
                        "<p><span style='font-weight: bold'>Inicio: </span>"+event.start+"<span style='font-weight: bold'> fin: </span>"+event.end+"</p>"+
                        "<p><span style='font-weight: bold'>Aforo máximo: </span>"+event.num_assistants+"</p>"+
                        "<p><span style='font-weight: bold'>Completado: </span>" +
                            "<div class='progress'>"+
                                "<div class='progress-bar' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width: 60%;'>"+
                                    "<span class='sr-only'>60% Completado</span>"+
                                "</div>"+
                            "</div>"+
                        "</p>"+
                        "<div class='text-right letta-view-event-attend-button'>"+createButtonAsistir(event.id)+"</div>"+
                    "</div>"+
                "</div>"+
            "</div>"
        );
    }, function () {
        alertify.error("No he encontrado ese evento");
    } );

}

function showSearch(){
    var text = formSearchtoText();
    $.getScript('js/dao/events.js', function(){
        searchEvents(text,function (events) {
            vaciarMain();
            $("#main").append("<div class='letta-view-event-container'><div class='row'><div id='view-container' class='col-md-12'></div></div></div>")
        	if(events.length<1){
	            $("#main").append("<h2 class='busqueda-no-encontrada'>No se han encontrado coincidencias con: " + text +"</h2>");
        	}else{
	            $("#main").append("<h2>Resultados para: " + text +"</h2><br/>");
	        	var cont=0;
	            $.each(events, function (key, event) {
	            	addEventToList(event,cont);
	                cont=cont+1;
	            });
	            changeAttendButton()
        	}
        }, function(){
            alertify.error('Error listando eventos');
        });
    });
}

function initEvents(){
    $.getScript('js/dao/events.js', function(){
        listPopularEvents ( function (events) {
            $.each(events, function (key, event) {
                addPopularEvent(event);
            });
            changeAttendButton();
        }, function(){
            alertify.error('Error listando eventos populares');
        });
    });
    fillCarrousel();
}

function fillCarrousel(){
	$.getScript('js/dao/events.js', function(){
        listFeaturedEvents ( function (events) {
            $("#carrouselIndicators").append(getCarrouselIndicators(events.length + 1));
            $("#carrouselElements").append(getCarrouselElements(events));
        });
	});
}

function createSearchResultRigth(event){
    var asistencia = asistenciaPorcentaje(event);
    var img_path = "img/"+event.category+".png";
    if(event.image)
    	img_path = 'rest/events/'+event.id+'/image';
	return '<div class="showcase2">\
	  <div class="thumbnail2" style="background-image:url('+ img_path +');">\
	    <div class="thumbnail__overlay2">\
	      '+createButtonAsistir(event.id)+'\
          '+createButtonVerMas(event.id)+'\
	    </div>\
	  </div>\
	  <div class="desc2">\
		  <h3>'+ event.title +'</h3>\
	      <p>'+ event.description +'</p>\
	      <p>Aforo máximo: '+event.num_assistants+'\
	      <br/>Completado:'+ asistencia +'% </p>\
	  </div>\
	</div>';
}

function createSearchResultLeft(event){
    var asistencia = asistenciaPorcentaje(event);
    var img_path = "img/"+event.category+".png";
    if(event.image)
    	img_path = 'rest/events/'+event.id+'/image';
	return '<div class="showcase2 showcase--inverted2">\
	  <div class="thumbnail2" style="background-image:url('+ img_path +');">\
	    <div class="thumbnail__overlay2">\
	      '+createButtonAsistir(event.id)+'\
          '+createButtonVerMas(event.id)+'\
	    </div>\
	  </div>\
	  <div class="desc2">\
		  <h3>'+ event.title +'</h3>\
	      <p>'+ event.description +'</p>\
	      <p>Aforo máximo: '+event.num_assistants+'\
	      <br/>Completado:'+ asistencia +'% </p>\
	  </div>\
	</div>';
}

function getCarrouselIndicators(numIndicators){
	var ret = "";
	for(var i = 0; i < numIndicators; i++){
		var active = "";
		if(i == 0) active = " class='active'";
		ret += "<li data-target='#bs-carousel' data-slide-to='" + i + "' " + active + "></li>";
	}
	return ret;
}

function getCarrouselElements(events){
	var ret = "";
    $.each(events, function (key, event) {
    	var active = "";
    	if(key == 0) active = " active";
        var img_path = "img/"+event.category+".png";
        if(event.image)
        	img_path = 'rest/events/'+event.id+'/image';
    	ret +="<div class='item slides" + active + "'>\
    		<div class='slide' style='background-image: url(" + img_path + ");'></div>\
    		<div class='hero'>\
    			<hgroup>\
    				<h1>" + event.title + "</h1>\
    				<h3>" + event.description + "</h3>\
    			</hgroup>\
    			<button class='btn btn-hero btn-lg' role='button'>Descubre</button>\
    		</div>\
    	</div>";
    });
	active = "";
	if(ret == "") active = "active";
	ret += "<div class='item slides" + active + "'>\
		<div class='slide' style='background-image: url(img/logo.png);'></div>\
		<div class='hero'>\
			<hgroup>\
				<h1>¿Tienes cinco minutos?</h1>\
				<h3>\
					Crea un evento con Le<i style='color: green;'>t</i>ta\
				</h3>\
			</hgroup>\
			<button class='btn btn-hero btn-lg' role='button'>Crear\
				un evento</button>\
		</div>\
	</div>";
	return ret;
}

function initAttendanceEvents(){
    $.getScript('js/dao/events.js', function(){
        listAttendanceEvents( function (events) {
        	var cont=0;
            $.each(events, function (key, event) {
                addEventToList(event,cont);
                cont=cont+1;
            });
            changeAttendButton()
        }, function(){
            alertify.error('Error listando eventos a los que se asistira');
        });
    });
    
}