
function createEventThumbnail(event){
    var asistencia = asistenciaPorcentaje(event);
    return '<div class="col-sm-6 col-md-4">\
    <div class="thumbnail">\
    <img class = "letta-image-sizer" src="http://www.grey-hare.co.uk/wp-content/uploads/2012/09/Event-management.png" alt="un evento">\
    <div class="caption">\
    <h3>'+ event.title +'</h3>\
    <p>'+ event.description +'</p>\
    <p>Aforo:\
    <div class="progress">\
        <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40"\
    aria-valuemin="0" aria-valuemax="100" style="width:'+asistencia+'">\
        </div>\
        </div>\
        '+ asistencia +'\
        </p>\
        <p><a href="#" class="btn btn-primary" role="button">Asistir</a> <a href="#" class="btn btn-default" role="button">Ver mas</a></p>\
    </div>\
    </div>\
    </div>';
}

function asistenciaPorcentaje(event){
    return 0;
}

function addNearestEvent(event){
   $('#proximos-eventos').append(createEventThumbnail(event));

}

function initEvents(){
    $.getScript('js/dao/events.js', function(){
        listEvents(function (events) {
            $.each(events, function (key, event) {
                addNearestEvent(event);
            });
        }, function(){
            alertify.error('Error listando eventos');
        });
    });
}