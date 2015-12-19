gpeAppStudentAttendance.controller('StudentAttendanceListController', ['$scope', '$routeParams', 'AttendanceFactory', 'SessionFactory', function($scope, $routeParams, AttendanceFactory, SessionFactory){

    console.log(SessionFactory.getUser());

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


