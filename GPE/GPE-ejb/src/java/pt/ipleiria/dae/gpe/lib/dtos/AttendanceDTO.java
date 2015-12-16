/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import pt.ipleiria.dae.gpe.lib.core.AbstractDTO;
import pt.ipleiria.dae.gpe.lib.entities.Attendance;

@XmlRootElement(name = "attendance")
@XmlAccessorType(XmlAccessType.FIELD)
public class AttendanceDTO extends AbstractDTO {

    private Integer idAttendance;
    private UserDTO student;
    private EventDTO event;
    private boolean present;

    public AttendanceDTO(){
        super();
    }
    
    public AttendanceDTO(UserDTO student, EventDTO event) {
        super(null);
        this.idAttendance = 0;
        this.student = student;
        this.event = event;
        this.present = false;
    }

    public AttendanceDTO(Integer idAttendance, UserDTO student, EventDTO event, boolean present) {
        super(null);
        this.idAttendance = idAttendance;
        this.student = student;
        this.event = event;
        this.present = present;
        this.New = idAttendance == 0;
    }

    public AttendanceDTO(Attendance entity) {
        super(entity);
        this.idAttendance = entity.getIdAttendance();
        this.event = new EventDTO(entity.getEvent());
        this.student = new UserDTO(entity.getStudent());
        this.present = entity.isPresent();
    }

    public Integer getIdAttendance() {
        return idAttendance;
    }

    public void setIdAttendance(Integer idAttendance) {
        this.idAttendance = idAttendance;
    }

    public UserDTO getStudent() {
        return student;
    }

    public void setStudent(UserDTO student) {
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
