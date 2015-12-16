var host = "http://localhost:8080/";

var gpe = angular.module('GPE', ['ngRoute', "mobile-angular-ui", 'mobile-angular-ui.gestures', 'GPE.app', 'GPE.app.admin']);
var gpeServices = angular.module('GPE.services', ['ngResource']);
var gpeApp = angular.module('GPE.app', ['GPE.services']);
var gpeAppAdmin = angular.module('GPE.app.admin', ['GPE.services']);
var gpeAppAdminUc = angular.module('GPE.app.admin.uc', ['GPE.services']);
var gpeAppAdminEventgroup = angular.module('GPE.app.admin.eventgroup', ['GPE.services']);
var gpeAppAdminEvent = angular.module('GPE.app.admin.event', ['GPE.services']);
var gpeAppAdminUser = angular.module('GPE.app.admin.user', ['GPE.services']);

gpe.run(function ($transform) {
    window.$transform = $transform;
});

gpe.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/login',                       { templateUrl: 'js/app/login.tpl.html',                                 controller: 'LoginController' });
    $routeProvider.when('/admin',                       { templateUrl: 'js/app/admin/dashboard.tpl.html',                       controller: 'AdminDashboardController' });
    // $routeProvider.when('/admin/ucs',                { templateUrl: 'js/app/admin/uc/uc-list.tpl.html',                      controller: 'AdminUcListController' });
    // $routeProvider.when('/admin/ucs/:ucId',          { templateUrl: 'js/app/admin/uc/uc-detail.tpl.html',                    controller: 'AdminUcDetailController' });
    // $routeProvider.when('/admin/eventgroups',        { templateUrl: 'js/app/admin/eventgroup/eventgroup-list.tpl.html',      controller: 'AdminEventgroupListController' });
    // $routeProvider.when('/admin/eventgroups/create', { templateUrl: 'js/app/admin/eventgroup/eventgroup-create.tpl.html',    controller: 'AdminEventgroupDetailController' });
    // $routeProvider.when('/admin/events',             { templateUrl: 'js/app/admin/event/event-list.tpl.html',                controller: 'AdminEventListController' });
    // $routeProvider.when('/admin/events/:eventId',    { templateUrl: 'js/app/admin/event/event-detail.tpl.html',              controller: 'AdminEventDetailController' });
    // $routeProvider.when('/admin/users',              { templateUrl: 'js/app/admin/user/user-list.tpl.html',                  controller: 'AdminUserListController' });
    // $routeProvider.when('/admin/users/:userId',      { templateUrl: 'js/app/admin/user/user-detail.tpl.html',                controller: 'AdminUserDetailController' });
    $routeProvider.otherwise({ redirectTo: '/login' });
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
