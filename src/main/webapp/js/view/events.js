
function createEventThumbnail(event){
    var asistencia = asistenciaPorcentaje(event);
    return '<div class="col-sm-6 col-md-4">\
        <div class="letta-event-thumbnail thumbnail">\
           <div class="letta-thumbnail-buttons-container">\
                <object class = "letta-image-sizer" data="rest/events/'+event.id+'/image" type="image/png">\
                    <img class = "letta-image-sizer" src="img/logo.png" alt="un evento"/>\
                </object>\
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

function addSearch(event){
    $('#main').append(createEventThumbnail(event));
}

function formSearchtoText(){
    return $('#buscar').val();
}

function showSearch(){
    var text = formSearchtoText();
    $.getScript('js/dao/events.js', function(){
        searchEvents(text,function (events) {
            document.getElementById("main").innerHTML="";
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

$('#buscar').keypress(function(e) {
    if (e.keyCode == '13') {
       e.preventDefault();
       showSearch();
     }
  });