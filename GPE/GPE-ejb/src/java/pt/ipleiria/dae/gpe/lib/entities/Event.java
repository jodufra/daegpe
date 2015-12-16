package pt.ipleiria.dae.gpe.lib.entities;

import pt.ipleiria.dae.gpe.lib.core.AbstractEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({})
@Table(name = "EVENTS")
public class Event extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idEvent;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String internalId;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private EventType eventType;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Basic(optional = false)
    @NotNull
    private String room;

    @Basic(optional = false)
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar eventDate;

    @Basic(optional = false)
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIME)
    private Calendar eventDuration;

    @Basic(optional = false)
    @NotNull
    private boolean attendanceActive;

    @Basic(optional = false)
    @NotNull
    private boolean attendanceActivated;

    @Basic(optional = false)
    @NotNull
    private String attendancePassword;

    @ManyToOne(optional = true)
    @JoinColumn(name = "IDUC")
    @NotNull
    private UC uc;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDUSER")
    @NotNull
    private Manager manager;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private Collection<Attendance> participants;

    @Size(max = 255)
    private String search;

    public Event() {
        this.attendanceActive = false;
        this.attendanceActivated = false;
        this.attendancePassword = "";
        this.participants = new ArrayList<>();
    }

    public Event(String internalId, EventType eventType, String name, String room, Calendar eventDate, Calendar eventDuration, String search, UC uc, Manager manager) {
        this.internalId = internalId;
        this.eventType = eventType;
        this.name = name;
        this.room = room;
        this.eventDate = eventDate;
        this.eventDuration = eventDuration;
        this.attendanceActive = false;
        this.attendanceActivated = false;
        this.attendancePassword = "";
        this.uc = uc;
        this.manager = manager;
        this.participants = new ArrayList<>();
        this.search = search;
    }

    public Event(Integer idEvent, String internalId, EventType eventType, String name, String room, Calendar eventDate, Calendar eventDuration, String search, UC uc, Manager manager) {
        this.idEvent = idEvent;
        this.internalId = internalId;
        this.eventType = eventType;
        this.name = name;
        this.room = room;
        this.eventDate = eventDate;
        this.eventDuration = eventDuration;
        this.attendanceActive = false;
        this.attendanceActivated = false;
        this.attendancePassword = "";
        this.uc = uc;
        this.manager = manager;
        this.participants = new ArrayList<>();
        this.search = search;
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Calendar getEventDate() {
        return eventDate;
    }

    public void setEventDate(Calendar eventDate) {
        this.eventDate = eventDate;
    }

    public Calendar getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(Calendar eventDuration) {
        this.eventDuration = eventDuration;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public UC getUc() {
        return uc;
    }

    public void setUc(UC uc) {
        this.uc = uc;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public boolean isAttendanceActive() {
        return attendanceActive;
    }

    public void setAttendanceActive(boolean attendanceActive) {
        this.attendanceActive = attendanceActive;
    }

    public boolean isAttendanceActivated() {
        return attendanceActivated;
    }

    public void setAttendanceActivated(boolean attendanceActivated) {
        this.attendanceActivated = attendanceActivated;
    }

    public String getAttendancePassword() {
        return attendancePassword;
    }

    public void setAttendancePassword(String attendancePassword) {
        this.attendancePassword = attendancePassword;
    }

    public Collection<Attendance> getParticipants() {
        return participants;
    }

    public void setParticipants(Collection<Attendance> participants) {
        this.participants = participants;
    }

    public void addParticipant(Attendance participant) {
        this.participants.add(participant);
    }

    @Override
    public boolean isNew() {
        return idEvent == 0;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.idEvent);
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
        final Event other = (Event) obj;
        if (!Objects.equals(this.idEvent, other.idEvent)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
