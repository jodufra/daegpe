'use strict';

gpeApp.controller('LoginController', ['$scope', 'LoginSvc', function ($scope, LoginSvc) {
    $scope.username = '';
    $scope.password = '';
    $scope.login = function () {
        LoginSvc.login({ username: $scope.username, password: $scope.password });
        $scope.go("/student");
    }
}]);