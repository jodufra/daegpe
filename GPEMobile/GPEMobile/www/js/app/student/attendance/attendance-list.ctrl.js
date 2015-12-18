gpeAppStudentAttendance.controller('StudentAttendanceListController', ['$scope', '$routeParams', 'AttendanceFactory', function($scope, $routeParams, AttendanceFactory){

    $scope.attendances = getAttendances();

    /**
     * verify if the user has attendances associated
     */
    $scope.hasAttendances = function(){
        return $scope.attendances.length > 0;
    }



    /**
     * go to dedicated page of attendance
     */
    $scope.load = function(attendance){
        //TODO - obter o login
        $scope.go('student/attendances/' + attendance.idAttendance + "/" + attendance.event.idEvent + "/" + "student");
    }



    /**
     * //TODO - Define Student
     * get all student attendances
     */
    function getAttendances(){
        return $scope.attendances = AttendanceFactory.query({userID: "student"});
    }


}]);


