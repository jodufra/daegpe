'use strict';


gpeServices.factory('AttendanceFactory', function ($resource) {
    return $resource(baseUrl + '/gpeapi/attendances/user/:userID', {}, {
        query: {method: 'GET', params: {userID: '@userID'}, isArray: true}
    });
});

gpeServices.factory('AttendanceUnique', function ($resource) {
    return $resource(baseUrl + '/gpeapi/attendances/:attendanceID/:eventID/:userID', {}, {
        unique: {method: 'GET', params: {attendanceID: '@attendanceID', eventID: '@eventID', userID: '@userID'}, isArray: false}
    });
});

gpeServices.factory('AttendanceUpdate', function ($resource) {
    return $resource(baseUrl + '/gpeapi/attendances/:attendanceID/:eventID/:userID/:newState', {}, {
        update: {method: 'PUT', params: {attendanceID: '@attendanceID', eventID: '@eventID', userID: '@userID', newState: '@newState'}, isArray: false}
    });
});

