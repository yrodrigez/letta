
function createEventThumbnail(event){
    var asistencia = asistenciaPorcentaje(event);
    img_path = "img/"+event.category+".png";
    if(event.image)
    	img_path = 'rest/events/'+event.id+'/image';
    return '<div class="col-sm-6 col-md-4">\
        <div class="letta-event-thumbnail thumbnail">\
           <div class="letta-thumbnail-buttons-container">\
                <img class = "letta-image-sizer" src="' + img_path + '" alt="un evento"/>\
                <div class="overlay">\
                    <a href="#" class="btn btn-primary" role="button">Asistir</a>\
                    <a href="#" class="btn btn-default" role="button">Ver mas</a>\
                </div>\
           </div>\
           <div class="caption">\
                <h3>'+ event.title +'</h3>\
                <p>'+ event.description +'</p>\
                <p>Aforo máximo: '+event.num_assistants+'\
                <br/>Completado:'+ asistencia +'% </p>\
           </div>\
        </div>\
    </div>';
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

function showSearch(){
    var text = formSearchtoText();
    $.getScript('js/dao/events.js', function(){
        searchEvents(text,function (events) {
            document.getElementById("main").innerHTML="";
        	var cont=0;
            $.each(events, function (key, event) {
                addSearch(event,cont);
                cont=cont+1;
            });

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
	return '<div class="showcase">\
	  <div class="thumbnail">\
			<object class = "letta-image-sizer" data="rest/events/'+event.id+'/image" type="image/png">\
    			<img class = "letta-image-sizer" src="img/logo.png" alt="un evento"/>\
			</object>\
	    <div class="thumbnail__overlay">\
	      <a class="btn" href="#0">Asistir</a>\
    	  <a class="btn" href="#0">Ver mas</a>\
	    </div>\
	  </div>\
	  <div class="desc">\
		  <h3>'+ event.title +'</h3>\
	      <p>'+ event.description +'</p>\
	      <p>Aforo máximo: '+event.num_assistants+'\
	      <br/>Completado:'+ asistencia +'% </p>\
	  </div>\
	</div>';
}

function createSearchResultLeft(event){
    var asistencia = asistenciaPorcentaje(event);
	return '<div class="showcase showcase--inverted">\
	  <div class="thumbnail">\
			<object class = "letta-image-sizer" data="rest/events/'+event.id+'/image" type="image/png">\
  			<img class = "letta-image-sizer" src="img/logo.png" alt="un evento"/>\
			</object>\
	    <div class="thumbnail__overlay">\
	      <a class="btn" href="#0">Asistir</a>\
  	  <a class="btn" href="#0">Ver mas</a>\
	    </div>\
	  </div>\
	  <div class="desc">\
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