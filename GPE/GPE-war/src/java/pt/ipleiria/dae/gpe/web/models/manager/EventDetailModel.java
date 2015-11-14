/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models.manager;

import java.util.ArrayList;
import java.util.List;
import pt.ipleiria.dae.gpe.lib.beans.AttendanceBean;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.dtos.ManagerDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.utilities.EventDayWeek;
import pt.ipleiria.dae.gpe.lib.utilities.EventType;
import pt.ipleiria.dae.gpe.lib.utilities.ManagerUserFindOptions;
import pt.ipleiria.dae.gpe.lib.utilities.Room;
import pt.ipleiria.dae.gpe.lib.utilities.UserOrderBy;

/**
 *
 * @author joeld
 */
public class EventDetailModel {

    private final UserBean userBean;
    private final AttendanceBean attendanceBean;
    
    private EventDTO event;

    private String tab;
    private String userSearch;

    public EventDetailModel(UserBean userBean ,AttendanceBean attendanceBean) {
        this.userBean = userBean;
        this.attendanceBean = attendanceBean;
        this.tab = "details";
        this.userSearch = "";
    }

    public String getTitle() {
        return event.getName();
    }

    public void setEvent(EventDTO event) {
        this.event = event;
        this.tab = "details";
        this.userSearch = "";
    }

    public EventDTO getEvent() {
        return event;
    }
    
    public Integer getIdEvent() {
        return event.getIdEvent();
    }

    public String getName() {
        return event.getName();
    }

    public UCDTO getUc() {
        return event.getUc();
    }

    public ManagerDTO getManager() {
        return event.getManager();
    }

    public String getInternalId() {
        return event.getInternalId();
    }

    public EventType getEventType() {
        return event.getEventType();
    }

    public EventDayWeek getEventDayWeek() {
        return event.getEventDayWeek();
    }

    public Room getRoom() {
        return event.getRoom();
    }

    public Integer getStartHour() {
        return event.getStartHour();
    }

    public Integer getEndHour() {
        return event.getEndHour();
    }

    public Integer getStartWeek() {
        return event.getStartWeek();
    }

    public Integer getEndWeek() {
        return event.getEndWeek();
    }

    public String getSemester() {
        return event.getSemester();
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getUserSearch() {
        return userSearch;
    }

    public void setUserSearch(String userSearch) {
        this.userSearch = userSearch;
    }

    public List<UserDTO> getSearchedUsers() {
        if (userSearch.isEmpty()) {
            return new ArrayList<>();
        }
        ManagerUserFindOptions options = new ManagerUserFindOptions(1, 5, UserOrderBy.InternalIdAsc, userSearch);
        return userBean.find(options);
    }

    public List<AttendanceDTO> getAttendances(){
        return attendanceBean.findEventAttendances(event);
    }
}
