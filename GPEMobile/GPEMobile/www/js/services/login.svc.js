'use strict';

gpeServices.factory('LoginSvc', ['$resource', function ($resource) {

    var login = { method: 'POST', params: { username: '@username', password: '@password' }, isArray: false };
    var logout = { method: 'GET', params: {}, isArray: false };

    return $resource(host + 'api/login/', {}, { login: login, logout: logout });
}]);