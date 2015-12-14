/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.entities;

import pt.ipleiria.dae.gpe.lib.core.AbstractEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author joeld
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByIduser", query = "SELECT u FROM User u WHERE u.idUser = :iduser"),
    @NamedQuery(name = "User.findByInternalid", query = "SELECT u FROM User u WHERE u.internalId = :internalId"),
    @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.internalId = :internalId OR u.email = :email"),
    @NamedQuery(name = "User.findBySearch", query = "SELECT u FROM User u WHERE u.search = :search"),
})
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = {"INTERNALID"}))
public class User extends AbstractEntity implements Serializable {

    public static boolean IsAdministrator(User user) {
        return (user instanceof Administrator || user.userGroup.getGroupName() == GROUP.Administrator);
    }

    public static boolean IsManager(User user) {
        return (user instanceof Manager || user.userGroup.getGroupName() == GROUP.Manager);
    }

    public static boolean IsStudent(User user) {
        return (user instanceof Student || user.userGroup.getGroupName() == GROUP.Student);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    protected Integer idUser;

    @Basic(optional = false)
    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    protected UserGroup userGroup;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    protected String internalId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    protected String name;

    //@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    protected String email;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    protected String password;

    @Size(max = 255)
    protected String search;

    protected User() {
    }

    protected User(String internalId, String name, String email, String password, GROUP group) {
        this.internalId = internalId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userGroup = new UserGroup(group, this);
    }

    protected User(Integer idUser, String internalId, String name, String email, String password, GROUP group, String search) {
        this.idUser = idUser;
        this.internalId = internalId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userGroup = new UserGroup(group, this);
        this.search = search;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup group) {
        this.userGroup = group;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public boolean isNew() {
        return idUser == 0;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        return !((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser)));
    }

    @Override
    public String toString() {
        return "User{" + "idUser=" + idUser + ", internalId=" + internalId + ", name=" + name + ", email=" + email + ", password=" + password + ", search=" + search + '}';
    }

}
