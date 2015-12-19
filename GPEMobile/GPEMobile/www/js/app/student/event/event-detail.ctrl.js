'use strict';

gpeAppStudentEvent.controller('StudentEventDetailController', ['$scope', '$routeParams', 'EventFactory', 'EventSubscribe', function ($scope, $routeParams, EventFactory, EventSubscribe) {
    $scope.event = EventFactory.show({ id: $routeParams.idEvent });

    $scope.subscribeUC = function(){
        //preciso do evento e do estudante
        console.log("eventID: " + $scope.event.idEvent);
        console.log("studentID: " + "34");
        $scope.teste = EventSubscribe.update({ userID: "2120680", ucID: $scope.event.idEvent});
        console.log("T: " + $scope.teste);
        console.log("sds");
    }

}]);