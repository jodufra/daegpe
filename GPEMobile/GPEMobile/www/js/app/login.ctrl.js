'use strict';

gpeApp.controller('LoginController', ['$scope', 'LoginFactory', function ($scope, LoginFactory) {
    $scope.internalId = '';
    $scope.newPassword = '';

    $scope.login = function () {
        $scope.isLogged = LoginFactory.update({ internalId: $scope.internalId, newPassword: $scope.newPassword, name: "" });
        $scope.isLogged.$promise.then(function (result) {
            $scope.data = result;
            if(!($scope.data.email == null)){
                $scope.go("/student");
            }
        });


    }
}]);