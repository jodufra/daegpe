'use strict';

gpeAppStudentUser.controller('StudentUserDetailController', ['$scope', '$routeParams', 'UserFactory', function ($scope, $routeParams, UserFactory) {
    $scope.user = UserFactory.show({ id: $routeParams.idUser })
}]);