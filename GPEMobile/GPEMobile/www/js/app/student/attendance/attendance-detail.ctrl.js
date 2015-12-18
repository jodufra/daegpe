gpeAppStudentAttendance.controller('StudentAttendanceDetailController', ['$scope', '$routeParams', 'AttendanceUnique', function($scope, $routeParams, AttendanceUnique){

    console.log("ATTT: " + $routeParams.attendanceID);

    $scope.attendance = AttendanceUnique.unique({ attendanceID: $routeParams.attendanceID});

    console.log("ATTTN: " + AttendanceUnique.unique({ attendanceID: $routeParams.attendanceID}));

    $scope.assignAttendance = function(){
        console.log("sds");
    }

}]);



