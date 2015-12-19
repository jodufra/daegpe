'use strict';

/*
 * 
 * UCs
    query: { method: 'GET', isArray: true },
    create: { method: 'POST' }
 * UC
    show: { method: 'GET' },
    update: { method: 'PUT', params: { id: '@id' } },
    delete: { method: 'DELETE', params: { id: '@id' } }
 * 
 */

gpeServices.factory('StudentUCsFactory', function ($resource, SessionFactory) {
    return $resource(baseUrl + '/gpeapi/student/ucs', {}, {
        query: {
            method: 'GET',
            params: {},
            isArray: true,
            headers: { "CurrentUser": SessionFactory.getUser().idUser }
        }
    });
});

gpeServices.factory('StudentUCFactory', function ($resource, SessionFactory) {
    return $resource(baseUrl + '/gpeapi/student/ucs/:id', {}, {
        show: {
            method: 'GET',
            params: { id: '@id' },
            headers: { "CurrentUser": SessionFactory.getUser().idUser }
        }
    });
});