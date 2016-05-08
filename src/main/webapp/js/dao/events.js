function daoassist(id, done, fail, always) {
    done = typeof done !== 'undefined' ? done : function() {};
    fail = typeof fail !== 'undefined' ? fail : function() {};
    always = typeof always !== 'undefined' ? always : function() {};

    $.ajax({
            url: 'rest/events/assist/' + id,
            type: 'POST'
        })
        .done(done)
        .fail(fail)
        .always(always);
}

function getEvent(id, done, fail, always) {
    done = typeof done !== 'undefined' ? done : function() {};
    fail = typeof fail !== 'undefined' ? fail : function() {};
    always = typeof always !== 'undefined' ? always : function() {};

    $.ajax({
            url: 'rest/events/'+id,
            type: 'GET'
        })
        .done(done)
        .fail(fail)
        .always(always);
}

function listEvents(done, fail, always) {
    done = typeof done !== 'undefined' ? done : function() {};
    fail = typeof fail !== 'undefined' ? fail : function() {};
    always = typeof always !== 'undefined' ? always : function() {};

    $.ajax({
            url: 'rest/events',
            type: 'GET'
    })
        .done(done)
        .fail(fail)
        .always(always);
}

function searchEvents(text, done, fail, always) {
    done = typeof done !== 'undefined' ? done : function() {};
    fail = typeof fail !== 'undefined' ? fail : function() {};
    always = typeof always !== 'undefined' ? always : function() {};

    $.ajax({
            url: 'rest/events?search='+text,
            type: 'GET'

        })
        .done(done)
        .fail(fail)
        .always(always);
}


function addEvent(event, done, fail, always) {
    done = typeof done !== 'undefined' ? done : function() {};
    fail = typeof fail !== 'undefined' ? fail : function() {};
    always = typeof always !== 'undefined' ? always : function() {};

    $.ajax({
            url: 'rest/events',
            type: 'POST',
            data: event
    })
        .done(done)
        .fail(fail)
        .always(always);
}

function modifyEvent(event, done, fail, always) {
    done = typeof done !== 'undefined' ? done : function() {};
    fail = typeof fail !== 'undefined' ? fail : function() {};
    always = typeof always !== 'undefined' ? always : function() {};

    $.ajax({
            url: 'rest/events/' + event.id,
            type: 'PUT',
            data: user
    })
        .done(done)
        .fail(fail)
        .always(always);
}


function deleteEvent(id, done, fail, always) {
    done = typeof done !== 'undefined' ? done : function() {};
    fail = typeof fail !== 'undefined' ? fail : function() {};
    always = typeof always !== 'undefined' ? always : function() {};

    $.ajax({
            url: 'rest/events/' + id,
            type: 'DELETE'
    })
        .done(done)
        .fail(fail)
        .always(always);
}

function listPopularEvents(done, fail, always) {
    done = typeof done !== 'undefined' ? done : function() {};
    fail = typeof fail !== 'undefined' ? fail : function() {};
    always = typeof always !== 'undefined' ? always : function() {};

    $.ajax({
            url: 'rest/events?type=popular',
            type: 'GET'
    })
        .done(done)
        .fail(fail)
        .always(always);
}

function listFeaturedEvents(done, fail, always) {
    done = typeof done !== 'undefined' ? done : function() {};
    fail = typeof fail !== 'undefined' ? fail : function() {};
    always = typeof always !== 'undefined' ? always : function() {};

    $.ajax({
            url: 'rest/events?type=featured',
            type: 'GET'
        })
        .done(done)
        .fail(fail)
        .always(always);
}
