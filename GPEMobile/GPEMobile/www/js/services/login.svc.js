'use strict';

gpeServices.factory('LoginFactory', ['$resource', function ($resource) {
    return $resource(baseUrl + '/gpeapi/users/login', {}, {
       update: {method: 'PUT', params: {internalId: '@internalId', newPassword: '@newPassword', name: '@name'}}
    });
}]);




/*
 var login = {
 method: 'POST',
 params: {
 internalID: '@username',
 password: '@password'
 },
 isArray: false
 };
 console.log("TESTE: " + login.params.internalID);
 var logout = {
 method: 'GET',
 params: {},
 isArray: false
 };

 return $resource(baseUrl + '/gpeapi/users/login', {}, {
 create: login,
 show: logout
 });*/