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

gpeServices.factory('UCsFactory', function ($resource) {
    return $resource(baseUrl + '/gpeapi/ucs', {}, {
        query: { method: 'GET', params: {}, isArray: true }
    });
});

gpeServices.factory('UCFactory', function ($resource) {
    return $resource(baseUrl + '/gpeapi/ucs/:id', {}, {
        show: { method: 'GET', params: { id: '@id' } }
    });
});