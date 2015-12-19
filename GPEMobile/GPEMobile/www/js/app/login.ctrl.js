'use strict';

var UserLoginModel = function (name, pass) {
    return { username: name, password: pass };
}

gpeApp.controller('LoginController', ['$scope', 'LoginFactory', function ($scope, LoginFactory) {

    $scope.model = new UserLoginModel("", "");
    $scope.invalidLogin = false;

    $scope.login = function () {
        var username = $scope.model.username;
        var password = CryptoJS.SHA256($scope.model.password).toString(CryptoJS.enc.Hex);

        LoginFactory.update(new UserLoginModel(username, password)).$promise.then(function (result) {
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