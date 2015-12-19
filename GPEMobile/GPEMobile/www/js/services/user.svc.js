'use strict';

gpeServices.factory('UserFactory', function ($resource) {
    return $resource(baseUrl + '/gpeapi/users/:id', {}, {
        show: { method: 'GET', params: { id: '@id' } }
    });
});
