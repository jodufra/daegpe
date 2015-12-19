'use strict';


gpeServices.factory('StudentAttendancesFactory', function ($resource, SessionFactory) {
    return $resource(baseUrl + '/gpeapi/student/attendances', {}, {
        query: {
            method: 'GET',
            isArray: true,
            headers: { "CurrentUser": SessionFactory.getUser().idUser }
        }
    });
});

gpeServices.factory('StudentAttendanceFactory', function ($resource, SessionFactory) {
    return $resource(baseUrl + '/gpeapi/student/attendances/:idAttendance/:state', {}, {
        show: {
            method: 'GET',
            params: { idAttendance: '@idAttendance', state: '' },
            isArray: false,
            headers: { "CurrentUser": SessionFactory.getUser().idUser }
        },
        update: {
            method: 'PUT',
            params: { idAttendance: '@idAttendance', state: '@state' },
            isArray: false,
            headers: { "CurrentUser": SessionFactory.getUser().idUser }
        }
    });
});

