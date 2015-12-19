'use strict';

gpeAppStudentUser.controller('StudentUserDetailController', ['$scope', '$routeParams', 'StudentUserFactory', function ($scope, $routeParams, StudentUserFactory) {
    $scope.user = StudentUserFactory.show({ id: $routeParams.idUser })
}]);