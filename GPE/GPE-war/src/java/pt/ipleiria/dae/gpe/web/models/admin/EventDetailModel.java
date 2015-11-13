
package pt.ipleiria.dae.gpe.web.models.admin;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javafx.print.Collation;
import pt.ipleiria.dae.gpe.lib.beans.EventBean;
import pt.ipleiria.dae.gpe.lib.beans.UCBean;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.dtos.ManagerDTO;
import pt.ipleiria.dae.gpe.lib.dtos.StudentDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.entities.Attendance;
import pt.ipleiria.dae.gpe.lib.entities.Event;
import pt.ipleiria.dae.gpe.lib.entities.User;
import pt.ipleiria.dae.gpe.lib.entities.UserType;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.utilities.EventDayWeek;
import pt.ipleiria.dae.gpe.lib.utilities.EventType;
import pt.ipleiria.dae.gpe.lib.utilities.Room;
import pt.ipleiria.dae.gpe.lib.utilities.Text;


public class EventDetailModel {
    
    private EventBean eventBean;
    private UCBean ucBean;
    private UserBean userBean;
    
    private Integer idEvent;
    private String internalId;
    private EventType eventType;
    private String name;
    private EventDayWeek eventDayWeek;
    private Room room;
    private Integer startHour;
    private Integer endHour;
    private Integer startWeek;
    private Integer endWeek;
    private String semester;
    private UCDTO uc;
    private ManagerDTO manager;
    private boolean isNew;
    private String stringIdImport;
    private Collection<Attendance> studentsAttendance;
    
    
    public EventDetailModel(EventBean eventBean, UCBean ucBean, UserBean userBean)
    {
        this.eventBean = eventBean;
        this.ucBean = ucBean;
        this.userBean = userBean;
        this.studentsAttendance = new LinkedList<>();
    }
    
    public void setEvent(EventDTO eventDTO)
    {
        if(eventDTO != null && !eventDTO.isNew()){
            this.idEvent = eventDTO.getIdEvent();
            this.internalId = eventDTO.getInternalId();
            this.eventType = eventDTO.getEventType();
            this.name = eventDTO.getName();
            this.eventDayWeek = eventDTO.getEventDayWeek();
            this.room = eventDTO.getRoom();
            this.startHour = eventDTO.getStartHour();
            this.endHour = eventDTO.getEndHour();
            this.startWeek = eventDTO.getStartWeek();
            this.endWeek = eventDTO.getEndWeek();
            this.semester = eventDTO.getSemester();
            this.uc = eventDTO.getUc();
            this.manager = eventDTO.getManager();
            this.isNew = eventDTO.isNew();
        }else{
            this.idEvent = 0;
            this.internalId = "";
            this.eventType = EventType.AULA;
            this.name = "";
            this.eventDayWeek = EventDayWeek.SEGUNDA;
            this.room = Room.A;
            this.startHour = 9;
            this.endHour = 10;
            this.startWeek = 10;
            this.endWeek = 20;
            this.semester = "1";
            this.uc = new UCDTO("a", "a");
            this.manager = new ManagerDTO("a", "a", "a@a.com", "a");
            this.isNew = true;
        }
    }
    
    public EventDTO provideEventDTO()
    {
        System.out.println("EE");
        System.out.println("EE: " + manager.getName());
        EventDTO eventDTO = new EventDTO(idEvent, internalId, eventType, name, eventDayWeek, room, startHour, endHour, startWeek, endWeek, semester, uc, manager);
        return eventDTO;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String getStringIdImport() {
        return stringIdImport;
    }

    public void setStringIdImport(String stringIdImport) {
        this.stringIdImport = stringIdImport;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public UCDTO getUc() {
        return uc;
    }

    public void setUc(UCDTO uc) {
        System.out.println("UC: " + uc.getName());
        this.uc = uc;
    }

    public ManagerDTO getManager() {
        return manager;
    }

    public void setManager(ManagerDTO manager) {
        this.manager = manager;
    }
    
    public Collection<Attendance> getStudentsAttendance()
    {
        EventDTO eventDTO = this.provideEventDTO();
        return eventBean.getStudentsAttendance(eventDTO);
    }

    public EventBean getEventBean() {
        return eventBean;
    }

    public void setEventBean(EventBean eventBean) {
        this.eventBean = eventBean;
    }
    
    public EventType[] getEventTypes()
    {
        return EventType.values();
    }
   
    public EventDayWeek[] getEventDayWeekTypes()
    {
        return EventDayWeek.values();
    }
    
    public Room[] getRoomTypes()
    {
        return Room.values();
    }
  
    public List<UCDTO> getAllUCs()
    {
        List<UCDTO> ucsDTO = ucBean.findAll();
        return ucsDTO;
    }
 
    public List<ManagerDTO> getAllManagers()
    {
        List<ManagerDTO> managersDTO = userBean.getAllManagers();
        return managersDTO;
    }
    
    public void getImportStudentsFromUC()
    {
        EventDTO eventDTO = this.provideEventDTO();
        
    }
}
    