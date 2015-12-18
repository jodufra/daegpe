gpeAppStudentAttendance.controller('StudentAttendanceDetailController', ['$scope', '$routeParams', 'AttendanceUnique', 'AttendanceUpdate', function($scope, $routeParams, AttendanceUnique, AttendanceUpdate){

    $scope.attendance = AttendanceUnique.unique({ attendanceID: $routeParams.attendanceID, eventID: $routeParams.eventID, userID: $routeParams.userID});

    $scope.password = "";
    $scope.buttonClass = "";
    $scope.presence = "";





    $scope.assignAttendance = function(password){
        var hash = CryptoJS.SHA256(password);
        console.log(hash.toString());
    }

   $scope.$watch(function(){
       $scope.attendance.present;
       checkPresence();
   });


    $scope.changeIntention = function(){
        if($scope.attendance.present){
            $scope.attendance.present = false;
            AttendanceUpdate.update({ attendanceID: $routeParams.attendanceID, eventID: $routeParams.eventID, userID: $routeParams.userID, newState: $scope.attendance.present});
            checkPresence();
        }else{
            $scope.attendance.present = true;
            AttendanceUpdate.update({ attendanceID: $routeParams.attendanceID, eventID: $routeParams.eventID, userID: $routeParams.userID, newState: $scope.attendance.present});
            checkPresence();
        }

    }


    /**
     * CONTROLLER FUNCTIONS
     */
    function checkPresence(){
        if($scope.attendance.present){
            $scope.buttonClass = "btn btn-warning btn-lg btn-block";
            $scope.presence = "Anular Presença";
        }else{
            $scope.buttonClass = "btn btn-primary btn-lg btn-block";
            $scope.presence = "Marcar Presença";
        }
    }

}]);



