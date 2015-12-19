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

gpeServices.factory('EventsFactory', function ($resource) {
    return $resource(baseUrl + '/gpeapi/events/:id', {}, {
        query: {method: 'GET', params: {id: '@id'}, isArray: true}
    });
});

gpeServices.factory('EventFactory', function ($resource) {
    return $resource(baseUrl + '/gpeapi/events/detail/:id', {}, {
        show: {method: 'GET', params: {id: '@id'}}
    });
});

gpeServices.factory('EventSubscribe', function ($resource) {
    return $resource(baseUrl + '/gpeapi/events/:userID/:ucID', {}, {
        update: {method: 'PUT', params: {userID: '@userID', ucID: '@ucID'}, isArray: false}
    });
});