/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.dtos;

import pt.ipleiria.dae.gpe.lib.core.AbstractDTO;
import pt.ipleiria.dae.gpe.lib.entities.Attendance;

/**
 *
 * @author joeld
 */
public class AttendanceDTO extends AbstractDTO {

    private Integer idAttendance;
    private StudentDTO student;
    private EventDTO event;
    private boolean present;

    public AttendanceDTO(StudentDTO student, EventDTO event, boolean present) {
        super(null);
        this.student = student;
        this.event = event;
        this.present = present;
    }

    public AttendanceDTO(Integer idAttendance, StudentDTO student, EventDTO event, boolean present) {
        super(null);
        this.idAttendance = idAttendance;
        this.student = student;
        this.event = event;
        this.present = present;
    }

    public AttendanceDTO(Attendance entity) {
        super(entity);
        this.idAttendance = entity.getIdAttendance();
        this.event = new EventDTO(entity.getEvent());
        this.student = new StudentDTO(entity.getStudent());
    }

    public Integer getIdAttendance() {
        return idAttendance;
    }

    public void setIdAttendance(Integer idAttendance) {
        this.idAttendance = idAttendance;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    @Override
    public Object getRelationalId() {
        return getIdAttendance();
    }
}
