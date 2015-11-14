package pt.ipleiria.dae.gpe.lib.dtos;

import pt.ipleiria.dae.gpe.lib.core.AbstractDTO;
import pt.ipleiria.dae.gpe.lib.entities.Event;
import pt.ipleiria.dae.gpe.lib.utilities.EventDayWeek;
import pt.ipleiria.dae.gpe.lib.utilities.EventType;
import pt.ipleiria.dae.gpe.lib.utilities.Room;
import pt.ipleiria.dae.gpe.lib.utilities.Security;

/**
 *
 * @author joeld
 */
public class EventDTO extends AbstractDTO {

    private Integer idEvent;
    private String internalId;
    private EventType eventType;
    private String name;
    private EventDayWeek eventDayWeek;
    private Room room;
    private Integer startHour;
    private Integer endHour;
    private String startWeek;
    private Integer endWeek;
    private String semester;
    private UCDTO uc;
    private ManagerDTO manager;
    private boolean attendanceActive;
    private boolean attendanceActivated;
    private String attendancePassword;

    public EventDTO(Integer idEvent, String internalId, EventType eventType, String name, EventDayWeek eventDayWeek, Room room, Integer startHour, Integer endHour,
            String startWeek, Integer endWeek, String semester, UCDTO uc, ManagerDTO manager) {
        super(null);
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
        this.semester = semester;
        this.uc = uc;
        this.manager = manager;
        this.attendanceActive = false;        
        this.attendanceActivated = false;
        this.attendancePassword = "";
        this.New = idEvent == 0;
    }

    public EventDTO(String internalId, EventType eventType, String name, EventDayWeek eventDayWeek, Room room, Integer startHour, Integer endHour,
            String startWeek, Integer endWeek, String semester, UCDTO uc, ManagerDTO manager) {
        super(null);
        this.idEvent = 0;
        this.internalId = internalId;
        this.eventType = eventType;
        this.name = name;
        this.eventDayWeek = eventDayWeek;
        this.room = room;
        this.startHour = startHour;
        this.endHour = endHour;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.semester = semester;
        this.uc = uc;
        this.manager = manager;
        this.attendanceActive = false;        
        this.attendanceActivated = false;
        this.attendancePassword = "";
    }

    public EventDTO(Event event) {
        super(event);
        this.idEvent = event.getIdEvent();
        this.internalId = event.getInternalId();
        this.eventType = event.getEventType();
        this.name = event.getName();
        this.eventDayWeek = event.getEventDayWeek();
        this.room = event.getRoom();
        this.startHour = event.getStartHour();
        this.endHour = event.getEndHour();
        this.startWeek = event.getStartWeek();
        this.endWeek = event.getEndWeek();
        this.semester = event.getSemester();
        this.uc = new UCDTO(event.getUc());
        this.manager = new ManagerDTO(event.getManager());
        this.attendanceActive = event.isAttendanceActive();        
        this.attendanceActivated = event.isAttendanceActivated(); ;
        this.attendancePassword = event.getAttendancePassword();
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

    public UCDTO getUc() {
        return uc;
    }

    public void setUc(UCDTO uc) {
        this.uc = uc;
    }

    public ManagerDTO getManager() {
        return manager;
    }

    public void setManager(ManagerDTO manager) {
        this.manager = manager;
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

    public String getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(String startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
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
        this.attendancePassword = Security.GenerateMD5Hash(attendancePassword);
    }

    @Override
    public Object getRelationalId() {
        return getIdEvent();
    }

}
