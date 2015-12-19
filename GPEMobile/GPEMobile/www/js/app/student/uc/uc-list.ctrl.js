'use strict';

gpeAppStudentUc.controller('StudentUcListController', ['$scope', 'StudentUCsFactory', function ($scope, StudentUCsFactory) {

    $scope.ucs = StudentUCsFactory.query();

    $scope.hasUCs = function () {
        return $scope.ucs.length > 0;
    }

    $scope.viewUC = function (idUC) {
        $scope.go("/student/ucs/" + idUC);
    }
}]);