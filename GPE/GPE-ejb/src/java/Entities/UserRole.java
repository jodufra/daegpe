/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author joeld
 */
@Entity
@Table(name = "user_role")
@NamedQueries({
    @NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u"),
    @NamedQuery(name = "UserRole.findByIduserrole", query = "SELECT u FROM UserRole u WHERE u.iduserrole = :iduserrole"),
    @NamedQuery(name = "UserRole.findByName", query = "SELECT u FROM UserRole u WHERE u.name = :name"),
    @NamedQuery(name = "UserRole.findByDatecreated", query = "SELECT u FROM UserRole u WHERE u.datecreated = :datecreated"),
    @NamedQuery(name = "UserRole.findByDateupdated", query = "SELECT u FROM UserRole u WHERE u.dateupdated = :dateupdated")
})
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer iduserrole;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date datecreated;
    @Temporal(TemporalType.DATE)
    private Date dateupdated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iduserrole")
    private Collection<User> userCollection;

    public UserRole() {
    }

    public UserRole(Integer iduserrole) {
        this.iduserrole = iduserrole;
    }

    public UserRole(Integer iduserrole, String name, Date datecreated) {
        this.iduserrole = iduserrole;
        this.name = name;
        this.datecreated = datecreated;
    }

    public Integer getIduserrole() {
        return iduserrole;
    }

    public void setIduserrole(Integer iduserrole) {
        this.iduserrole = iduserrole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    public boolean isNew() {
        return iduserrole == 0;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iduserrole != null ? iduserrole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRole)) {
            return false;
        }
        UserRole other = (UserRole) object;
        if ((this.iduserrole == null && other.iduserrole != null) || (this.iduserrole != null && !this.iduserrole.equals(other.iduserrole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.UserRole[ iduserrole=" + iduserrole + " ]";
    }
    
}
