/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import pt.ipleiria.dae.gpe.lib.beans.AttendanceBean;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.entities.EventType;
import pt.ipleiria.dae.gpe.lib.beans.query.options.ManagerUserFindOptions;
import pt.ipleiria.dae.gpe.lib.beans.query.order.UserOrderBy;

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
    private String attendancePassword;

    public EventDetailModel(UserBean userBean, AttendanceBean attendanceBean) {
        this.userBean = userBean;
        this.attendanceBean = attendanceBean;
        this.tab = "details";
        this.userSearch = "";
        this.attendancePassword = "";
    }

    public String getTitle() {
        return event.getName();
    }
    
    public EventDTO getEvent(){
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
        this.tab = "details";
        this.userSearch = "";
        this.attendancePassword = "";
    }

    public Integer getIdEvent() {
        return event.getIdEvent();
    }

    public String getInternalId() {
        return event.getInternalId();
    }

    public EventType getEventType() {
        return event.getEventType();
    }

    public String getName() {
        return event.getName();
    }

    public String getRoom() {
        return event.getRoom();
    }

    public Calendar getEventDate() {
        return event.getEventDate();
    }

    public Calendar getEventDuration() {
        return event.getEventDuration();
    }

    public boolean isAttendanceActive() {
        return event.isAttendanceActive();
    }

    public boolean isAttendanceActivated() {
        return event.isAttendanceActivated();
    }

    public String getAttendancePassword() {
        return event.getAttendancePassword();
    }

    public UCDTO getUc() {
        return event.getUc();
    }

    public UserDTO getManager() {
        return event.getManager();
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

    public void setAttendancePassword(String attendancePassword) {
        this.attendancePassword = attendancePassword;
    }

    public List<UserDTO> getSearchedUsers() {
        if (userSearch.isEmpty()) {
            return new ArrayList<>();
        }
        ManagerUserFindOptions options = new ManagerUserFindOptions(1, 5, UserOrderBy.InternalIdAsc, userSearch);
        return userBean.find(options);
    }

    public List<AttendanceDTO> getAttendances() {
        return attendanceBean.findEventAttendances(event);
    }
}
