'use strict';

gpeServices.factory('LoginFactory', ['$resource', function ($resource) {
    return $resource(baseUrl + '/gpeapi/login', {}, {
        update: {
            method: 'PUT',
            params: { username: '@username', password: '@password' }
        }
    });
}]);