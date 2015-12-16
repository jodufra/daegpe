'use strict';

gpeApp.controller('MainController', ['$scope', '$rootScope', '$window', '$location', function ($scope, $rootScope, $window, $location) {

    $rootScope.loading = false;
    $rootScope.$on('$routeChangeStart', function () {
        $rootScope.loading = true;
    });

    $rootScope.$on('$routeChangeSuccess', function () {
        $rootScope.loading = false;
    });

    $scope.slide = '';
    $rootScope.back = function () {
        $scope.slide = 'slide-right';
        $window.history.back();
    }
    $rootScope.go = function (path) {
        $scope.slide = 'slide-left';
        $location.url(path);
    }
}]);