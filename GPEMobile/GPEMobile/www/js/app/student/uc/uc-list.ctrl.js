'use strict';

gpeAppStudentUc.controller('StudentUcListController', ['$scope', 'UCsFactory', function ($scope, UCsFactory) {

    $scope.viewUC = function (idUC) {
        $scope.go("/student/ucs/" + idUC);
    }
    $scope.ucs = UCsFactory.query();
}]);