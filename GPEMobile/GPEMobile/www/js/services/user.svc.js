'use strict';

gpeServices.factory('StudentUserFactory', function ($resource, SessionFactory) {
    return $resource(baseUrl + '/gpeapi/student/users/:id', {}, {
        show: {
            method: 'GET',
            params: { id: '@id' },
            headers: { "CurrentUser": SessionFactory.getUser().idUser }
        }
    });
});
