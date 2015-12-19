gpeAppStudentAttendance.controller('StudentAttendanceDetailController', ['$scope', '$routeParams', 'StudentAttendanceFactory', function ($scope, $routeParams, StudentAttendanceFactory) {

    $scope.attendance = StudentAttendanceFactory.show({ idAttendance: $routeParams.idAttendance });

    $scope.password = "";
    $scope.buttonClass = "";
    $scope.presence = "";

    $scope.assignAttendance = function (password) {
        var hash = CryptoJS.SHA256(password);
        console.log(hash.toString());
    }

    $scope.$watch(function () {
        $scope.attendance.present;
        checkPresence();
    });


    $scope.changeIntention = function () {
        $scope.attendance.present = !$scope.attendance.present;
        StudentAttendanceFactory.update({ idAttendance: $routeParams.idAttendance, state: $scope.attendance.present });
        checkPresence();
    }


    /**
     * CONTROLLER FUNCTIONS
     */
    function checkPresence() {
        if ($scope.attendance.present) {
            $scope.buttonClass = "btn btn-warning btn-lg btn-block";
            $scope.presence = "Anular Presença";
        } else {
            $scope.buttonClass = "btn btn-primary btn-lg btn-block";
            $scope.presence = "Marcar Presença";
        }
    }

}]);



