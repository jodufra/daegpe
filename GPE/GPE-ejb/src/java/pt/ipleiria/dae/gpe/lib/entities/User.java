/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.entities;

import pt.ipleiria.dae.gpe.lib.core.AbstractEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
    @NamedQuery(name = "User.findByInternalid", query = "SELECT u FROM User u WHERE u.internalId = :internalid"),
    @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findBySearch", query = "SELECT u FROM User u WHERE u.search = :search")
})
@Table(name = "USERS")
public class User extends AbstractEntity implements Serializable {
    
    public static boolean IsAdministrator(User user){
        return (user instanceof Administrator);
    }
    public static boolean IsManager(User user){
        return (user instanceof Manager);
    }
    public static boolean IsStudent(User user){
        return (user instanceof Student);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    protected Integer idUser;

    @Basic(optional = false)
    @NotNull
    protected UserType type;

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

    public User() {
    }

    public User(String internalId, String name, String email, String password) {
        this.internalId = internalId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(Integer idUser, String internalId, String name, String email, String password, String search) {
        this.idUser = idUser;
        this.internalId = internalId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.search = search;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
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