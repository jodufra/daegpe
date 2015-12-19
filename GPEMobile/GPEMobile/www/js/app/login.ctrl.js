'use strict';

var UserLoginModel = function () {
    return { username: "", password: "" };
}

gpeApp.controller('LoginController', ['$scope', 'LoginFactory', function ($scope, LoginFactory) {

    $scope.model = new UserLoginModel();
    $scope.invalidLogin = false;

    $scope.login = function () {
        LoginFactory.update({ username: $scope.model.username, password: $scope.model.password }).$promise.then(function (result) {
            $scope.session.setUser(result);
            $scope.invalidLogin = !$scope.session.hasUser();
            if ($scope.invalidLogin) {
                $scope.session.clear();
            } else {
                $scope.go("/student");
            }
        });
    }

    if ($scope.session.hasUser()) {
        $scope.go("/student");
    }

}]);