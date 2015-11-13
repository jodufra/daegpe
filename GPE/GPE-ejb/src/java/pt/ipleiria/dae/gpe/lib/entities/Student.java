/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.entities;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author joeld
 */
@Entity
public class Student extends User {

    @ManyToMany(mappedBy = "students")
    private Collection<UC> ucs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Collection<Attendance> attendances;

    public Student() {
        super();
        this.type = UserType.Student;
        this.ucs = new ArrayList<>();
        this.attendances = new ArrayList<>();
    }

    public Student(String internalId, String name, String email, String password) {
        super(internalId, name, email, password);
        this.type = UserType.Student;
        this.ucs = new ArrayList<>();
        this.attendances = new ArrayList<>();
    }

    public Student(Integer idUser, String internalId, String name, String email, String password, String search) {
        super(idUser, internalId, name, email, password, search);
        this.type = UserType.Student;
        this.ucs = new ArrayList<>();
        this.attendances = new ArrayList<>();
    }

    public Collection<UC> getUcs() {
        return ucs;
    }

    public void setUcs(Collection<UC> ucs) {
        this.ucs = ucs;
    }

    public Collection<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(Collection<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void addUc(UC uc) {
        ucs.add(uc);
    }

}
