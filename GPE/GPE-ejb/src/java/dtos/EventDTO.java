/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.Date;

/**
 *
 * @author joeld
 */
public class EventDTO extends AbstractDTO{

    private Integer idEvent;
    private String name;
    private Date dateStart;
    private short minutes;
    private String search;
    private UCDTO uc;
    private ManagerDTO manager;
    
    public EventDTO() {
    }

    public EventDTO(String name, Date dateStart, short minutes, UCDTO uc, ManagerDTO manager) {
        this.name = name;
        this.dateStart = dateStart;
        this.minutes = minutes;
        this.uc = uc;
        this.manager = manager;
    }

    public EventDTO(Integer idEvent, String name, Date dateStart, short minutes, String search, UCDTO uc, ManagerDTO manager) {
        this.idEvent = idEvent;
        this.name = name;
        this.dateStart = dateStart;
        this.minutes = minutes;
        this.search = search;
        this.uc = uc;
        this.manager = manager;
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public short getMinutes() {
        return minutes;
    }

    public void setMinutes(short minutes) {
        this.minutes = minutes;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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
    
    @Override
    public boolean isNew() {
        return idEvent == 0;
    }
    
}
