/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;

/**
 *
 * @author joeld
 */
public class EventGroup {

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

    public EventGroup() {
        ignoredWeeks = new ArrayList<>();
    }

    public EventGroup(String internalId, EventType eventType, String name, String room, 
            Calendar dateStart, Calendar dateEnd, Calendar timetableStart, Calendar timetableDuration, 
            boolean mondayEvent, boolean tuesdayEvent, boolean wednesdayEvent, boolean thursdayEvent, 
            boolean fridayEvent, boolean saturdayEvent, boolean sundayEvent, List<Integer> ignoredWeeks, 
            UCDTO uc, UserDTO manager) {
        this.internalId = internalId;
        this.eventType = eventType;
        this.name = name;
        this.room = room;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.timetableStart = timetableStart;
        this.timetableDuration = timetableDuration;
        this.mondayEvent = mondayEvent;
        this.tuesdayEvent = tuesdayEvent;
        this.wednesdayEvent = wednesdayEvent;
        this.thursdayEvent = thursdayEvent;
        this.fridayEvent = fridayEvent;
        this.saturdayEvent = saturdayEvent;
        this.sundayEvent = sundayEvent;
        this.ignoredWeeks = ignoredWeeks;
        this.uc = uc;
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

    public Calendar getDateStart() {
        return dateStart;
    }

    public void setDateStart(Calendar dateStart) {
        this.dateStart = dateStart;
    }

    public Calendar getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Calendar getTimetableStart() {
        return timetableStart;
    }

    public void setTimetableStart(Calendar timetableStart) {
        this.timetableStart = timetableStart;
    }

    public Calendar getTimetableDuration() {
        return timetableDuration;
    }

    public void setTimetableDuration(Calendar timetableDuration) {
        this.timetableDuration = timetableDuration;
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

    public void setTuesdayEvent(boolean thuesdayEvent) {
        this.tuesdayEvent = thuesdayEvent;
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

    public List<Integer> getIgnoredWeeks() {
        return ignoredWeeks;
    }

    public void setIgnoredWeeks(List<Integer> ignoredWeeks) {
        this.ignoredWeeks = ignoredWeeks;
    }

    public void addIgnoredWeek(int ignoredWeek) {
        this.ignoredWeeks.add(ignoredWeek);
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
    
    public boolean isAnyDayOfWeek(){
        return mondayEvent || tuesdayEvent || wednesdayEvent || thursdayEvent ||
                fridayEvent || saturdayEvent || sundayEvent;
    }
    
    public boolean isDayOfWeekEvent(GregorianCalendar calendar){
        return isDayOfWeekEvent(calendar.get(Calendar.DAY_OF_WEEK));
    }

    public boolean isDayOfWeekEvent(int dayOfWeek) {
        switch(dayOfWeek){
            case Calendar.MONDAY: return mondayEvent;
            case Calendar.TUESDAY: return tuesdayEvent;
            case Calendar.WEDNESDAY: return wednesdayEvent;
            case Calendar.THURSDAY: return thursdayEvent;
            case Calendar.FRIDAY: return fridayEvent;
            case Calendar.SATURDAY: return saturdayEvent;
            case Calendar.SUNDAY: return sundayEvent;
        }
        return false;
    }

}
