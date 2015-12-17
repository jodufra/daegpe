'use strict';

gpeServices.factory('LoginFactory', ['$resource', function ($resource) {

    var login = { method: 'POST', params: { username: '@username', password: '@password' }, isArray: false };
    var logout = { method: 'GET', params: {}, isArray: false };

    return $resource(baseUrl + 'webapi/session/', {}, {
        create: login,
        show: logout
    });
}]);