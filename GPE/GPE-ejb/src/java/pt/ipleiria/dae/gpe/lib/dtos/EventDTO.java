package pt.ipleiria.dae.gpe.lib.dtos;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import pt.ipleiria.dae.gpe.lib.core.AbstractDTO;
import pt.ipleiria.dae.gpe.lib.entities.Event;
import pt.ipleiria.dae.gpe.lib.entities.EventType;

@XmlRootElement(name = "evento")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventDTO extends AbstractDTO {

    private final Integer idEvent;
    private String internalId;
    private EventType eventType;
    private String name;
    private String room;
    private Calendar eventDate;
    private Calendar eventDuration;
    private boolean attendanceActive;
    private boolean attendanceActivated;
    private String attendancePassword;
    private UCDTO uc;
    private UserDTO manager;

    
    public EventDTO() {
        super(null);
        idEvent = 0;
        internalId = "";
        eventType = null;
        name = "";
        room = "";
        eventDate = null;
        eventDuration = null;
        attendanceActive = false;
        attendanceActivated = false;
        attendancePassword = "";
        uc = null;
        manager = null;
    }

    public EventDTO(Event event) {
        super(event);
        idEvent = event.getIdEvent();
        internalId = event.getInternalId();
        eventType = event.getEventType();
        name = event.getName();
        room = event.getRoom();
        eventDate = event.getEventDate();
        eventDuration = event.getEventDuration();
        attendanceActive = event.isAttendanceActive();
        attendanceActivated = event.isAttendanceActivated();
        attendancePassword = event.getAttendancePassword();
        uc = new UCDTO(event.getUc());
        manager = new ManagerDTO(event.getManager());
    }

    //TODO - By Pedro
    public EventDTO(Integer idEvent, String internalId, EventType eventType, String name, String room,
            Calendar eventDate, Calendar eventDuration, boolean attendanceActive,
            boolean attendanceActivated, String attendancePassword, UCDTO uc, UserDTO manager) {
        super(null);
        this.idEvent = idEvent;
        this.internalId = internalId;
        this.eventType = eventType;
        this.name = name;
        this.room = room;
        this.eventDate = eventDate;
        this.eventDuration = eventDuration;
        this.attendanceActive = attendanceActive;
        this.attendanceActivated = attendanceActivated;
        this.attendancePassword = attendancePassword;
        this.uc = uc;
        this.manager = manager;
    }
    
    public EventDTO(String internalId, EventType eventType, String name, String room,
            Calendar eventDate, Calendar eventDuration, boolean attendanceActive,
            boolean attendanceActivated, String attendancePassword, UCDTO uc, UserDTO manager) {
        super(null);
        this.idEvent = 0;
        this.internalId = internalId;
        this.eventType = eventType;
        this.name = name;
        this.room = room;
        this.eventDate = eventDate;
        this.eventDuration = eventDuration;
        this.attendanceActive = attendanceActive;
        this.attendanceActivated = attendanceActivated;
        this.attendancePassword = attendancePassword;
        this.uc = uc;
        this.manager = manager;
    }

    public Integer getIdEvent() {
        return idEvent;
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

    public void setEventDuration(Calendar timetableDuration) {
        this.eventDuration = timetableDuration;
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

    public UCDTO getUc() {
        return uc;
    }

    public void setUc(UCDTO uc) {
        this.uc = uc;
    }

    public UserDTO getManager() {
        return manager;
    }

    public void setManager(UserDTO manager) {
        this.manager = manager;
    }

    @Override
    public Object getRelationalId() {
        return getIdEvent();
    }

    @Override
    public String toString() {
        return "EventDTO{" + "idEvent=" + idEvent + ", internalId=" + internalId + ", eventType=" + eventType + ", name=" + name + ", room=" + room + ", eventDate=" + eventDate + ", eventDuration=" + eventDuration + ", attendanceActive=" + attendanceActive + ", attendanceActivated=" + attendanceActivated + ", attendancePassword=" + attendancePassword + ", uc=" + uc + ", manager=" + manager + '}';
    }

    
}
