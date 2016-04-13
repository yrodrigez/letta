function listUsers(done, fail, always) {
    done = typeof done !== 'undefined' ? done : function() {};
    fail = typeof fail !== 'undefined' ? fail : function() {};
    always = typeof always !== 'undefined' ? always : function() {};

    $.ajax({
            url: 'rest/users',
            type: 'GET'
    })
        .done(done)
        .fail(fail)
        .always(always);
}

function addUser(user, done, fail, always) {
    done = typeof done !== 'undefined' ? done : function() {};
    fail = typeof fail !== 'undefined' ? fail : function() {};
    always = typeof always !== 'undefined' ? always : function() {};

    $.ajax({
            url: 'rest/users',
            type: 'POST',
            data: user
    })
        .done(done)
        .fail(fail)
        .always(always);
}

function modifyUser(user, done, fail, always) {
    done = typeof done !== 'undefined' ? done : function() {};
    fail = typeof fail !== 'undefined' ? fail : function() {};
    always = typeof always !== 'undefined' ? always : function() {};

    $.ajax({
            url: 'rest/users/' + user.login,
            type: 'PUT',
            data: user
    })
        .done(done)
        .fail(fail)
        .always(always);
}


function deleteUser(id, done, fail, always) {
    done = typeof done !== 'undefined' ? done : function() {};
    fail = typeof fail !== 'undefined' ? fail : function() {};
    always = typeof always !== 'undefined' ? always : function() {};

    $.ajax({
            url: 'rest/users/' + id,
            type: 'DELETE'
    })
        .done(done)
        .fail(fail)
        .always(always);
}