'use strict';

gpeAppStudentAttendance.controller('StudentAttendanceListController', ['$scope', '$routeParams', 'StudentAttendancesFactory', function ($scope, $routeParams, StudentAttendancesFactory) {

    $scope.attendances = StudentAttendancesFactory.query();

    /**
     * verify if the user has attendances associated
     */
    $scope.hasAttendances = function () {
        return $scope.attendances.length > 0;
    }

    /**
     * go to detail page of attendance
     */
    $scope.load = function (attendance) {
        $scope.go('student/attendances/' + attendance.idAttendance);
    }

}]);


