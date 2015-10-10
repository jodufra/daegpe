/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author joeld
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u"),
    @NamedQuery(name = "UserRole.findByName", query = "SELECT u FROM UserRole u WHERE u.name = :name")
})
@Table(name = "USERROLES")
public class UserRole extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUserRole;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userRole")
    private Collection<User> users;

    public UserRole() {
        this.idUserRole = 0;
        this.name = "";
        this.users = new ArrayList<>();
    }

    public UserRole(String name) {
        this.idUserRole = 0;
        this.name = name;
        this.users = new ArrayList<>();
    }

    public UserRole(Integer idUserRole, String name) {
        this.idUserRole = idUserRole;
        this.name = name;
        this.users = new ArrayList<>();
    }

    public Integer getIdUserRole() {
        return idUserRole;
    }

    public void setIdUserRole(Integer idUserRole) {
        this.idUserRole = idUserRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @Override
    public boolean isNew() {
        return idUserRole == 0;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUserRole != null ? idUserRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRole)) {
            return false;
        }
        UserRole other = (UserRole) object;
        if ((this.idUserRole == null && other.idUserRole != null) || (this.idUserRole != null && !this.idUserRole.equals(other.idUserRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.UserRole[ iduserrole=" + idUserRole + " ]";
    }

}
