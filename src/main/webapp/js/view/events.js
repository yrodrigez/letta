
function createEventThumbnail(event){
    var asistencia = asistenciaPorcentaje(event);
    return '<div class="col-sm-6 col-md-4">\
    <div class="thumbnail">\
    <object class = "letta-image-sizer" data="rest/events/'+event.id+'/image" type="image/png">\
        <img class = "letta-image-sizer" src="img/logo.png" alt="un evento">\
    </object>\
    <div class="caption">\
    <h3>'+ event.title +'</h3>\
    <p>'+ event.description +'</p>\
    <p>Aforo máximo: '+event.num_assistants+'<br>Completado:\
    <div class="progress">\
        <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40"\
    aria-valuemin="0" aria-valuemax="100" style="width:'+asistencia+'">\
        </div>\
        </div>\
        '+ asistencia +'%\
        </p>\
        <p><a href="#" class="btn btn-primary" role="button">Asistir</a> <a href="#" class="btn btn-default" role="button">Ver mas</a></p>\
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

function addSearch(event){
	$('#searchResult').append(createEventThumbnail(event));
}

function formSearchtoText(){
	var form = $('#busqueda');
	return form.find('input[name="buscar"]').val();
}

function showSearch(){
	var text = formSearchtoText();
    $.getScript('js/dao/events.js', function(){
        searchEvents(text,function (events) {
            document.getElementById("searchResult").innerHTML="";
            $.each(events, function (key, event) {
            	addSearch(event);
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