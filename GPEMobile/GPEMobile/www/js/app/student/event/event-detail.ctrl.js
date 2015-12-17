'use strict';

gpeAppStudentEvent.controller('StudentEventDetailController', ['$scope', '$routeParams', 'EventFactory', function ($scope, $routeParams, EventFactory) {
    $scope.event = EventFactory.show({ id: $routeParams.idEvent })
}]);