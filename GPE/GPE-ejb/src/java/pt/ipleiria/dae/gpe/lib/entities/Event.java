package pt.ipleiria.dae.gpe.lib.entities;

import pt.ipleiria.dae.gpe.lib.core.AbstractEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import pt.ipleiria.dae.gpe.lib.utilities.EventDayWeek;
import pt.ipleiria.dae.gpe.lib.utilities.EventType;
import pt.ipleiria.dae.gpe.lib.utilities.Room;


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
    protected String internalId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EventType eventType;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EventDayWeek eventDayWeek;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private Room room;
    
    @Basic(optional = false)
    @NotNull
    private Integer startHour;
    
    @Basic(optional = false)
    @NotNull
    private Integer endHour;
    
    @Basic(optional = false)
    @NotNull
    private Integer startWeek;
    
    
    @Basic(optional = false)
    @NotNull
    private Integer endWeek;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    private String Semester;
  

    @Size(max = 255)
    private String search;

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

    public Event() {
        this.participants = new ArrayList<>();
    }

    
    public Event(String internalId, EventType eventType,  String name, EventDayWeek eventDayWeek, Room room, Integer startHour, Integer endHour, Integer startWeek, Integer endWeek, String semester, UC uc, Manager manager) {
        this.internalId = internalId;
        this.eventType = eventType;
        this.name = name;
        this.eventDayWeek = eventDayWeek;
        this.room = room;
        this.startHour = startHour;
        this.endHour = endHour;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.Semester = semester;
        this.uc = uc;
        this.manager = null;
        this.participants = new ArrayList<>();
    }

    public Event(Integer idEvent, String internalId, EventType eventType,  String name, EventDayWeek eventDayWeek, Room room, Integer startHour, Integer endHour, Integer startWeek, Integer endWeek, String semester, UC uc, Manager manager) {
        this.idEvent = idEvent;
        this.internalId = internalId;
        this.eventType = eventType;
        this.name = name;
        this.eventDayWeek = eventDayWeek;
        this.room = room;
        this.startHour = startHour;
        this.endHour = endHour;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.Semester = semester;
        this.uc = uc;
        this.manager = null;
        this.participants = new ArrayList<>();
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    
    public Collection<Attendance> getParticipants() {
        return participants;
    }

    public void setParticipants(Collection<Attendance> participants) {
        this.participants = participants;
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


    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }

    public EventDayWeek getEventDayWeek() {
        return eventDayWeek;
    }

    public void setEventDayWeek(EventDayWeek eventDayWeek) {
        this.eventDayWeek = eventDayWeek;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String Semester) {
        this.Semester = Semester;
    }

    public void addParticipant(Attendance attendance)
    {
        this.participants.add(attendance);
    }
    
    @Override
    public String toString()
    {
        return this.name;
    }
    
}
