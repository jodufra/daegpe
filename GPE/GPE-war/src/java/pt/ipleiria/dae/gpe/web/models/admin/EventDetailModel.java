package pt.ipleiria.dae.gpe.web.models.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import pt.ipleiria.dae.gpe.lib.beans.EventBean;
import pt.ipleiria.dae.gpe.lib.beans.UCBean;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.entities.Attendance;
import pt.ipleiria.dae.gpe.lib.entities.EventType;

public class EventDetailModel {

    private final EventBean eventBean;
    private final UCBean ucBean;
    private final UserBean userBean;

    private Integer idEvent;
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
    private boolean isNew;

    private Collection<AttendanceDTO> attendants;

    private String tab;

    public EventDetailModel(EventBean eventBean, UCBean ucBean, UserBean userBean) {
        this.eventBean = eventBean;
        this.ucBean = ucBean;
        this.userBean = userBean;
        this.attendants = new ArrayList<>();
        this.tab = "details";
    }

    public void setEvent(EventDTO event) {
        if (event != null && !event.isNew()) {
            this.idEvent = event.getIdEvent();
            this.internalId = event.getInternalId();
            this.eventType = event.getEventType();
            this.name = event.getName();
            this.room = event.getRoom();
            this.eventDate = event.getEventDate();
            this.eventDuration = event.getEventDuration();
            this.attendanceActive = event.isAttendanceActive();
            this.attendanceActivated = event.isAttendanceActivated();
            this.attendancePassword = event.getAttendancePassword();
            this.uc = event.getUc();
            this.manager = event.getManager();
            this.isNew = event.isNew();
        } else {
            this.idEvent = 0;
            this.internalId = "";
            this.eventType = EventType.AULATEORICA;
            this.name = "";
            this.room = "";
            this.eventDate = Calendar.getInstance();
            this.eventDuration = new GregorianCalendar(0, 0, 0, 2, 0);
            this.attendanceActive = false;
            this.attendanceActivated = false;
            this.attendancePassword = "";
            this.uc = ucBean.findFirst();
            this.manager = userBean.findFirstManager();
            this.isNew = true;
        }
    }

    public String title() {
        return isNew ? "Adicionar Evento" : name;
    }

    public EventDTO provideEventDTO() {
        return new EventDTO(internalId, eventType, name, room, eventDate, eventDuration, attendanceActive, attendanceActivated, attendancePassword, uc, manager);
    }

    public Collection<Attendance> getStudentsAttendance() {
        EventDTO eventDTO = this.provideEventDTO();
        return eventBean.findStudentsAttendance(eventDTO);
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

    public Calendar getEventDuration() {
        return eventDuration;
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

    public boolean isIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public Collection<AttendanceDTO> getAttendants() {
        return attendants;
    }

    public void setAttendants(Collection<AttendanceDTO> attendants) {
        this.attendants = attendants;
    }

    public List<UCDTO> getAllUCs() {
        return ucBean.findAll();
    }

    public List<UserDTO> getAllManagers() {
        return userBean.findAllManagers();
    }

    public String getTab() {
        return tab;
    }
    
    public void setTab(String tab) {
        this.tab = tab;
    }
    public EventType[] getEventTypes()
    {
        return EventType.values();
    }
    
    
}
