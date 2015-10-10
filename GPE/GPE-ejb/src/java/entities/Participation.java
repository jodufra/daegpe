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
@Table(name = "PARTICIPATIONS")
public class Participation extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idParticipation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDUSER")
    @NotNull
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDEVENT")
    @NotNull
    private Event event;

    @Basic(optional = false)
    private boolean hasPresence;

    public Participation() {
    }

    public Participation(User user, Event event, boolean hasPresence) {
        this.user = user;
        this.event = event;
        this.hasPresence = hasPresence;
    }

    public Participation(Integer idParticipation, User user, Event event, boolean hasPresence) {
        this.idParticipation = idParticipation;
        this.user = user;
        this.event = event;
        this.hasPresence = hasPresence;
    }

    public Integer getIdParticipation() {
        return idParticipation;
    }

    public void setIdParticipation(Integer idParticipation) {
        this.idParticipation = idParticipation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isHasPresence() {
        return hasPresence;
    }

    public void setHasPresence(boolean hasPresence) {
        this.hasPresence = hasPresence;
    }

    @Override
    public boolean isNew() {
        return idParticipation == 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idParticipation);
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
        final Participation other = (Participation) obj;
        if (!Objects.equals(this.idParticipation, other.idParticipation)) {
            return false;
        }
        return true;
    }

}
