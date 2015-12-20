/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import pt.ipleiria.dae.gpe.lib.beans.UCBean;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.entities.EventGroup;
import pt.ipleiria.dae.gpe.lib.entities.EventType;

/**
 *
 * @author joeld
 */
public class EventGroupDetailModel {

    private final UCBean ucBean;
    private final UserBean userBean;
    private Integer idEvent;
    private String internalId;
    private EventType eventType;
    private String name;
    private String room;
    private Calendar dateStart;
    private Calendar dateEnd;
    private Calendar timetableStart;
    private Calendar timetableDuration;
    private boolean mondayEvent;
    private boolean tuesdayEvent;
    private boolean wednesdayEvent;
    private boolean thursdayEvent;
    private boolean fridayEvent;
    private boolean saturdayEvent;
    private boolean sundayEvent;
    private List<Integer> ignoredWeeks;
    private UCDTO uc;
    private UserDTO manager;

    public EventGroupDetailModel(UCBean ucBean, UserBean userBean) {
        this.ucBean = ucBean;
        this.userBean = userBean;
    }

    public void setEventGroup(EventGroup eventGroup) {
        if (eventGroup != null) {
            this.idEvent = 0;
            this.internalId = eventGroup.getInternalId();
            this.eventType = eventGroup.getEventType();
            this.name = eventGroup.getName();
            this.room = eventGroup.getRoom();
            this.dateStart = eventGroup.getDateStart();
            this.dateEnd = eventGroup.getDateEnd();
            this.timetableStart = eventGroup.getTimetableStart();
            this.timetableDuration = eventGroup.getTimetableDuration();
            this.mondayEvent = eventGroup.isMondayEvent();
            this.tuesdayEvent = eventGroup.isTuesdayEvent();
            this.wednesdayEvent = eventGroup.isWednesdayEvent();
            this.thursdayEvent = eventGroup.isThursdayEvent();
            this.fridayEvent = eventGroup.isFridayEvent();
            this.saturdayEvent = eventGroup.isSaturdayEvent();
            this.sundayEvent = eventGroup.isSundayEvent();
            this.ignoredWeeks = eventGroup.getIgnoredWeeks();
            this.uc = eventGroup.getUc();
            this.manager = eventGroup.getManager();
        } else {
            this.idEvent = 0;
            this.internalId = "";
            this.eventType = EventType.AULATEORICA;
            this.name = "";
            this.room = "";
            this.dateStart = Calendar.getInstance();
            this.dateEnd = Calendar.getInstance();
            this.timetableStart = new GregorianCalendar(0, 0, 0, 9, 0);
            this.timetableDuration = new GregorianCalendar(0, 0, 0, 2, 0);
            this.mondayEvent = false;
            this.tuesdayEvent = false;
            this.wednesdayEvent = false;
            this.thursdayEvent = false;
            this.fridayEvent = false;
            this.saturdayEvent = false;
            this.sundayEvent = false;
            this.ignoredWeeks = new ArrayList<>();
            this.uc = ucBean.findFirst();
            this.manager = userBean.findFirstManager();
        }
    }

    public String title() {
        return "Adicionar um grupo de Eventos";
    }

    public EventGroup provideEventGroup() {
        return new EventGroup(internalId, eventType, name, room, dateStart, dateEnd, timetableStart, timetableDuration, mondayEvent, tuesdayEvent, wednesdayEvent, thursdayEvent, fridayEvent, saturdayEvent, sundayEvent, ignoredWeeks, uc, manager);
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
    
    public EventType[] getEventTypes(){
        return EventType.values();
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

    public String getDateStart() {
        if(dateStart == null) return "";
        int day = dateStart.get(Calendar.DAY_OF_MONTH);
        int month = dateStart.get(Calendar.MONTH) + 1;
        int year = dateStart.get(Calendar.YEAR);
        return day + "/" + month + "/" + year;
    }

    public void setDateStart(String dateStart) {
        String[] vals = dateStart.split("/");
        int day = Integer.valueOf(vals[0]);
        int month = Integer.valueOf(vals[1]) - 1;
        int year = Integer.valueOf(vals[2]);
        this.dateStart = new GregorianCalendar(year, month, day);
    }

    public String getDateEnd() {
        if(dateEnd == null) return "";
        int day = dateEnd.get(Calendar.DAY_OF_MONTH);
        int month = dateEnd.get(Calendar.MONTH) + 1;
        int year = dateEnd.get(Calendar.YEAR);
        return day + "/" + month + "/" + year;
    }

    public void setDateEnd(String dateEnd) {
        String[] vals = dateEnd.split("/");
        int day = Integer.valueOf(vals[0]);
        int month = Integer.valueOf(vals[1]) - 1;
        int year = Integer.valueOf(vals[2]);
        this.dateEnd = new GregorianCalendar(year, month, day);
    }

    public String getTimetableStart() {
        if(timetableStart == null) return "";
        int hour = timetableStart.get(Calendar.HOUR_OF_DAY);
        int minute = timetableStart.get(Calendar.MINUTE);
        return hour + ":" + minute;
    }

    public void setTimetableStart(String timetableStart) {
        String[] vals = timetableStart.split(":");
        int hour = Integer.valueOf(vals[0]);
        int minute = Integer.valueOf(vals[1]);
        this.timetableStart = new GregorianCalendar(0, 0, 0, hour, minute);
    }

    public String getTimetableDuration() {
        if(timetableDuration == null) return "";
        int hour = timetableDuration.get(Calendar.HOUR_OF_DAY);
        int minute = timetableDuration.get(Calendar.MINUTE);
        return hour + ":" + minute;
    }

    public void setTimetableDuration(String timetableDuration) {
        String[] vals = timetableDuration.split(":");
        int hour = Integer.valueOf(vals[0]);
        int minute = Integer.valueOf(vals[1]);
        this.timetableDuration = new GregorianCalendar(0, 0, 0, hour, minute);
    }

    public boolean isMondayEvent() {
        return mondayEvent;
    }

    public void setMondayEvent(boolean mondayEvent) {
        this.mondayEvent = mondayEvent;
    }

    public boolean isTuesdayEvent() {
        return tuesdayEvent;
    }

    public void setTuesdayEvent(boolean tuesdayEvent) {
        this.tuesdayEvent = tuesdayEvent;
    }

    public boolean isWednesdayEvent() {
        return wednesdayEvent;
    }

    public void setWednesdayEvent(boolean wednesdayEvent) {
        this.wednesdayEvent = wednesdayEvent;
    }

    public boolean isThursdayEvent() {
        return thursdayEvent;
    }

    public void setThursdayEvent(boolean thursdayEvent) {
        this.thursdayEvent = thursdayEvent;
    }

    public boolean isFridayEvent() {
        return fridayEvent;
    }

    public void setFridayEvent(boolean fridayEvent) {
        this.fridayEvent = fridayEvent;
    }

    public boolean isSaturdayEvent() {
        return saturdayEvent;
    }

    public void setSaturdayEvent(boolean saturdayEvent) {
        this.saturdayEvent = saturdayEvent;
    }

    public boolean isSundayEvent() {
        return sundayEvent;
    }

    public void setSundayEvent(boolean sundayEvent) {
        this.sundayEvent = sundayEvent;
    }

    public String getIgnoredWeeks() {
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> iterator = ignoredWeeks.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public void setIgnoredWeeks(String ignoredWeeks) {
        this.ignoredWeeks.clear();
        for (String week : ignoredWeeks.split(",")) {
            this.ignoredWeeks.add(Integer.parseInt(week));
        }
    }

    public UCDTO getUc() {
        if(this.uc == null) return new UCDTO("", "");
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

    public List<UCDTO> getAllUCs() {
        return ucBean.findAll();
    }

    public List<UserDTO> getAllManagers() {
        return userBean.findAllManagers();
    }

}
