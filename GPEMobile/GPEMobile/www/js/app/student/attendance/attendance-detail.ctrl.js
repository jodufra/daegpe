gpeAppStudentAttendance.controller('StudentAttendanceDetailController', ['$scope', '$routeParams', 'AttendanceUnique', function($scope, $routeParams, AttendanceUnique){

    $scope.attendance = AttendanceUnique.unique({ attendanceID: $routeParams.attendanceID });

    $scope.assignAttendance = function(){
        console.log("sds");
    }

}]);



