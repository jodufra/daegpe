'use strict';

gpeAppStudentUc.controller('StudentUcDetailController', ['$scope', '$routeParams', 'UCFactory', function ($scope, $routeParams, UCFactory) {
    $scope.uc = UCFactory.show({ id: $routeParams.idUC })
}]);