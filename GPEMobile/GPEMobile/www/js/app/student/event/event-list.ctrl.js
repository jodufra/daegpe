'use strict';

gpeAppStudentEvent.controller('StudentEventListController', ['$scope', 'EventsFactory', function ($scope, EventsFactory) {

    $scope.load = function (event) {
        if ($scope.internalId != '') {
            $scope.go('/student/events/' + event.idEvent);
        } else {
            $scope.internalId = event.internalId;
            getEvents();
        }
    }

    $scope.clear = function () {
        $scope.internalId = '';
        getEvents();
    }

    $scope.hasInternalId = function () {
        return $scope.internalId != '';
    }

    function getEvents() {
        $scope.events = EventsFactory.query({ id: $scope.internalId });
    }

    $scope.internalId = '';
    $scope.events;
    getEvents();
}]);