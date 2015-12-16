var host = "http://localhost:8080/";

var gpe = angular.module('GPE', ['ngRoute', "mobile-angular-ui", 'mobile-angular-ui.gestures', 'GPE.app', 'GPE.app.student', 'GPE.app.student.uc', 'GPE.app.student.event', 'GPE.app.student.eventgroup', 'GPE.app.student.user']);
var gpeServices = angular.module('GPE.services', ['ngResource']);
var gpeApp = angular.module('GPE.app', ['GPE.services']);
var gpeAppStudent = angular.module('GPE.app.student', ['GPE.services']);
var gpeAppStudentUc = angular.module('GPE.app.student.uc', ['GPE.services']);
var gpeAppStudentEventgroup = angular.module('GPE.app.student.eventgroup', ['GPE.services']);
var gpeAppStudentEvent = angular.module('GPE.app.student.event', ['GPE.services']);
var gpeAppStudentUser = angular.module('GPE.app.student.user', ['GPE.services']);

gpe.run(function ($transform) {
    window.$transform = $transform;
});

gpe.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/login', {
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
    $routeProvider.when('/student/ucs/:ucId', {
        templateUrl: 'js/app/student/uc/uc-detail.tpl.html',
        controller: 'StudentUcDetailController'
    });
    $routeProvider.when('/student/eventgroups', {
        templateUrl: 'js/app/student/eventgroup/eventgroup-list.tpl.html',
        controller: 'StudentEventGroupListController'
    });
    $routeProvider.when('/student/eventgroups/create', {
        templateUrl: 'js/app/student/eventgroup/eventgroup-create.tpl.html',
        controller: 'StudentEventGroupDetailController'
    });
    $routeProvider.when('/student/events', {
        templateUrl: 'js/app/student/event/event-list.tpl.html',
        controller: 'StudentEventListController'
    });
    $routeProvider.when('/student/events/:eventId', {
        templateUrl: 'js/app/student/event/event-detail.tpl.html',
        controller: 'StudentEventDetailController'
    });
    $routeProvider.when('/student/users', {
        templateUrl: 'js/app/student/user/user-list.tpl.html',
        controller: 'StudentUserListController'
    });
    $routeProvider.when('/student/users/:userId', {
        templateUrl: 'js/app/student/user/user-detail.tpl.html',
        controller: 'StudentUserDetailController'
    });
    $routeProvider.otherwise({
        redirectTo: '/student'
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
    // Update DOM on a Received Event
    receivedEvent: function (id) {
        initializeAngular();
        console.log('Received Event: ' + id);
    },
    initializeAngular: function () {

    }
};