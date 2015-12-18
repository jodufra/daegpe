'use strict';


gpeServices.factory('AttendanceFactory', function ($resource) {
    return $resource(baseUrl + '/gpeapi/attendances/:userID', {}, {
        query: {method: 'GET', params: {userID: '@userID'}, isArray: true}
    });
});

gpeServices.factory('AttendanceUnique', function ($resource) {
    return $resource(baseUrl + '/gpeapi/attendances/:attendanceID', {}, {
        unique: {method: 'GET', params: {attendanceID: '@attendanceID'}}
    });
});

