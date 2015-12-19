'use strict';

gpeApp.controller('LoginController', ['$scope', 'LoginFactory', function ($scope, LoginFactory) {
    $scope.username = '';
    $scope.password = '';
    $scope.login = function () {
        LoginFactory.create({ username: $scope.username, password: $scope.password });
        $scope.go("/student");
    }
}]);