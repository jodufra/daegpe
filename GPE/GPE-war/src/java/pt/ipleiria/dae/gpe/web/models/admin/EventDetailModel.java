
package pt.ipleiria.dae.gpe.web.models.admin;

import java.util.Date;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;


public class EventDetailModel {
    
    
    private Integer idEvent;
    private String internalId;
    private String name;
    private Date dateStart;
    private Short minutes;
    private boolean isNew;
    
    public EventDetailModel()
    {
    }
    
    public void setEvent(EventDTO eventDTO)
    {
        if(eventDTO != null && !eventDTO.isNew()){
            this.idEvent = eventDTO.getIdEvent();
            this.internalId = eventDTO.getInternalId();
            this.name = eventDTO.getName();
            this.dateStart = eventDTO.getDateStart();
            this.minutes = eventDTO.getMinutes();
            this.isNew = eventDTO.isNew();
        }else{
            this.idEvent = 0;
            this.internalId = "";
            this.name = "";
            this.dateStart = new Date();
            this.minutes = 0;
            this.isNew = true;
        }
    }
    
    public EventDTO provideEventDTO()
    {
        EventDTO eventDTO = new EventDTO(this.idEvent, this.name, this.internalId, this.dateStart, this.minutes, null, null);
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Short getMinutes() {
        return minutes;
    }

    public void setMinutes(Short minutes) {
        this.minutes = minutes;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }
    
    
    
}
    