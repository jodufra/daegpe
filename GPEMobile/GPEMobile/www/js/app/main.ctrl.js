'use strict';
gpeApp.controller('MainController', ['$scope', '$rootScope', '$window', '$location', 'SessionFactory', function ($scope, $rootScope, $window, $location, SessionFactory) {

    $rootScope.session = SessionFactory;
    $rootScope.loading = false;

    $rootScope.$on('$routeChangeStart', function () {
        $rootScope.loading = true;
    });

    $rootScope.$on('$routeChangeSuccess', function () {
        $rootScope.loading = false;
    });

    $rootScope.back = function () {
        $window.history.back();
    }

    $rootScope.go = function (path) {
        $location.url(path);
    }
}]);