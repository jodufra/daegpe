/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.core;

/**
 *
 * @author joeld
 */
public enum EntityValidationError {

    // USER
    USER_INTERNALID_REQUIRED, USER_INTERNALID_NOT_UNIQUE, USER_USERTYPE_INVALID,
    USER_NAME_REQUIRED, USER_EMAIL_REQUIRED, USER_EMAIL_PATTERN, USER_IS_NOT_ADMIN,
    USER_IS_NOT_MANAGER, USER_IS_NOT_STUDENT, USER_IS_NEW, USER_IS_REQUIRED,
    // UC
    UC_INTERNALID_REQUIRED, UC_INTERNALID_NOT_UNIQUE, UC_NAME_REQUIRED, UC_IS_NEW, UC_IS_REQUIRED,
    // ATTENDANCE
    ATTENDANCE_NULL_STUDENT, ATTENDANCE_STUDENT_IS_NEW, ATTENDANCE_USER_NOT_STUDENT, 
    ATTENDANCE_NULL_EVENT, ATTENDANCE_EVENT_IS_NEW, ATTENDANCE_IS_NEW, ATTENDANCE_CANT_BE_REPEATED,
    //EVENT
    EVENT_WEEK_INVALID

}
