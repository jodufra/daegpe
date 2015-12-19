'use strict';

gpeAppStudentUc.controller('StudentUcDetailController', ['$scope', '$routeParams', 'StudentUCFactory', function ($scope, $routeParams, StudentUCFactory) {
    $scope.uc = StudentUCFactory.show({ id: $routeParams.idUC })
}]);