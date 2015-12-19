'use strict';

var Session = function () {
    return { user: null };
};

gpeServices.factory('SessionFactory', [function () {
    var service = {};
    var session = new Session();

    service.getUser = function () {
        return session.user;
    }

    service.setUser = function (user) {
        session.user = user;
    }

    service.hasUser = function () {
        return session != null && session.user != null && session.user.idUser != null;
    }

    service.clear = function () {
        session = new Session();
    }

    return service;
}]);