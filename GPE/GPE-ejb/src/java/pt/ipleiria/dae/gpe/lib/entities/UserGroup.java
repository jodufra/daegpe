/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author joeld
 */
@Entity
@Table(name = "USERS_GROUPS")
public class UserGroup implements Serializable {

    @Id
    @Enumerated(EnumType.STRING)
    private GROUP groupName;

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USERNAME",  referencedColumnName = "INTERNALID")
    private User user;

    public UserGroup() {
    }

    public UserGroup(GROUP group, User user) {
        this.groupName = group;
        this.user = user;
    }

    public GROUP getGroupName() {
        return groupName;
    }

    public void setGroupName(GROUP group) {
        this.groupName = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.groupName);
        hash = 59 * hash + Objects.hashCode(this.user);
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
        final UserGroup other = (UserGroup) obj;
        if (this.groupName != other.groupName) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }

}
