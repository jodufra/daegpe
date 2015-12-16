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
import javax.persistence.OneToMany;

/**
 *
 * @author joeld
 */
@Entity
public class Manager extends User {

    @OneToMany(mappedBy = "manager", cascade = CascadeType.REMOVE)
    private Collection<Event> events;

    public Manager() {
        super();
        this.userGroup = new UserGroup(GROUP.Manager, this);
        this.events = new ArrayList<>();
    }

    public Manager(String internalId, String name, String email, String password) {
        super(internalId, name, email, password, GROUP.Manager);
        this.events = new ArrayList<>();
    }

    public Manager(Integer idUser, String internalId, String name, String email, String password, String search) {
        super(idUser, internalId, name, email, password, GROUP.Manager, search);
        this.events = new ArrayList<>();
    }

    public Collection<Event> getEvents() {
        return events;
    }

    public void setEvents(Collection<Event> events) {
        this.events = events;
    }

}
