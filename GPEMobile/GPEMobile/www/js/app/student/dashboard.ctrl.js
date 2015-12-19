'use strict';

gpeAppStudent.controller('StudentDashboardController', ['$scope', function ($scope) {

    $scope.logout = function () {
        $scope.session.clear();
        $scope.go('/');
    }

}]);