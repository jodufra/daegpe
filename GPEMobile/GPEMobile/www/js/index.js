var baseUrl = "http://192.168.1.75:8080/GPE_RESTWS";

var gpe = angular.module('GPE', ['ngRoute', "mobile-angular-ui", 'mobile-angular-ui.gestures', 'GPE.app', 'GPE.app.student', 'GPE.app.student.uc', 'GPE.app.student.user', 'GPE.app.student.attendance']);
var gpeServices = angular.module('GPE.services', ['ngResource']);
var gpeApp = angular.module('GPE.app', ['GPE.services']);
var gpeAppStudent = angular.module('GPE.app.student', ['GPE.services']);
var gpeAppStudentUc = angular.module('GPE.app.student.uc', ['GPE.services']);
var gpeAppStudentUser = angular.module('GPE.app.student.user', ['GPE.services']);
var gpeAppStudentAttendance = angular.module('GPE.app.student.attendance', ['GPE.services']);

gpe.run(function ($transform) {
    window.$transform = $transform;
});

gpe.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'js/app/login.tpl.html',
        controller: 'LoginController'
    });
    $routeProvider.when('/student', {
        templateUrl: 'js/app/student/dashboard.tpl.html',
        controller: 'StudentDashboardController'
    });
    $routeProvider.when('/student/ucs', {
        templateUrl: 'js/app/student/uc/uc-list.tpl.html',
        controller: 'StudentUcListController'
    });
    $routeProvider.when('/student/ucs/:idUC', {
        templateUrl: 'js/app/student/uc/uc-detail.tpl.html',
        controller: 'StudentUcDetailController'
    });
    $routeProvider.when('/student/users/:idUser', {
        templateUrl: 'js/app/student/user/user-detail.tpl.html',
        controller: 'StudentUserDetailController'
    });
    $routeProvider.when('/student/attendances', {
        templateUrl: 'js/app/student/attendance/attendance-list.tpl.html',
        controller: 'StudentAttendanceListController'
    });
    $routeProvider.when('/student/attendances/:idAttendance', {
        templateUrl: 'js/app/student/attendance/attendance-detail.tpl.html',
        controller: 'StudentAttendanceDetailController'
    });
    $routeProvider.otherwise({
        redirectTo: '/'
    });
}]);


var app = {
    initialize: function () {
        this.bindEvents();
    },
    bindEvents: function () {
        document.addEventListener('deviceready', this.onDeviceReady, false);
    },
    onDeviceReady: function () {
        app.receivedEvent('deviceready');
    },
    receivedEvent: function (id) {
        initializeAngular();
        console.log('Received Event: ' + id);
    }
};