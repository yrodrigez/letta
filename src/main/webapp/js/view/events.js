
function createEventThumbnail(event){
    var asistencia = asistenciaPorcentaje(event);
    var img_path = "img/"+event.category+".png";
    if(event.image)
    	img_path = 'rest/events/'+event.id+'/image';
    return '<div class="col-sm-6 col-md-4">\
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
    return '<a onclick="showEventDetails('+id+')" class="btn btn-default" role="button">Ver mas</a>';
}

function createButtonAsistir(id) {
    /*TODO: CREAR ENLACE*/
    return '<a class="btn btn-primary" role="button">Asistir</a>';
}

function asistenciaPorcentaje(event){
    return 0;
}

function addPopularEvent(event){
    $('#proximos-eventos').append(createEventThumbnail(event));
}

function addSearch(event,cont){
	if(cont%2==0){
		$('#main').append(createSearchResultRigth(event));
	}else{
		$('#main').append(createSearchResultLeft(event));
	}
}

function formSearchtoText(){
    return $('#buscar').val();
}

function vaciarMain() {
    document.getElementById("main").innerHTML="";
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
                        "<div class='text-right letta-view-event-assist-button'>"+createButtonAsistir(event.id)+"</div>"+
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
        	if(events.length<1){
	            $("#main").append("<h2 class='busqueda-no-encontrada'>No se han encontrado coincidencias con: " + text +"</h2>");
        	}else{
	            $("#main").append("<h2>Resultados para: " + text +"</h2><br/>");
	        	var cont=0;
	            $.each(events, function (key, event) {
	                addSearch(event,cont);
	                cont=cont+1;
	            });
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
        }, function(){
            alertify.error('Error listando eventos populares');
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

$('#buscar').keypress(function(e) {
    if (e.keyCode == '13') {
       e.preventDefault();
       showSearch();
     }
  });