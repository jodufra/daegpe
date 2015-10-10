/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

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
@Table(name = "ENROLLMENTS")
public class Enrollment extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idEnrollment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDUSER")
    @NotNull
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDUC")
    @NotNull
    private UC uc;

    public Enrollment() {
    }

    public Enrollment(User user, UC uc) {
        this.user = user;
        this.uc = uc;
    }

    public Enrollment(Integer idEnrollment, User user, UC uc) {
        this.idEnrollment = idEnrollment;
        this.user = user;
        this.uc = uc;
    }

    public Integer getIdEnrollment() {
        return idEnrollment;
    }

    public void setIdEnrollment(Integer idEnrollment) {
        this.idEnrollment = idEnrollment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UC getUc() {
        return uc;
    }

    public void setUc(UC uc) {
        this.uc = uc;
    }

    @Override
    public boolean isNew() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idEnrollment);
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
        final Enrollment other = (Enrollment) obj;
        if (!Objects.equals(this.idEnrollment, other.idEnrollment)) {
            return false;
        }
        return true;
    }

}
