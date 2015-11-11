/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.entities;

import pt.ipleiria.dae.gpe.lib.core.AbstractEntity;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author joeld
 */
@Entity
@NamedQueries({})
@Table(name = "ATTENDANCES")
public class Attendance extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idAttendance;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDSTUDENT")
    @NotNull
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDEVENT")
    @NotNull
    private Event event;

    @Basic(optional = false)
    private boolean present;

    public Attendance() {
        this.present = false;
    }

    public Attendance(Student student, Event event, boolean present) {
        this.student = student;
        this.event = event;
        this.present = present;
    }

    public Attendance(Integer idAttendance, Student student, Event event, boolean present) {
        this.idAttendance = idAttendance;
        this.student = student;
        //this.event = event;
        this.present = present;
    }

    public Integer getIdAttendance() {
        return idAttendance;
    }

    public void setIdAttendance(Integer idAttendance) {
        this.idAttendance = idAttendance;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
    
    @Override
    public boolean isNew() {
        return idAttendance == 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idAttendance);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Attendance other = (Attendance) obj;
        if (!Objects.equals(this.idAttendance, other.idAttendance)) {
            return false;
        }
        return true;
    }

}
