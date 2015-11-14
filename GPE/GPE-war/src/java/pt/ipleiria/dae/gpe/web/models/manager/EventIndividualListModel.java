/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models.manager;

import pt.ipleiria.dae.gpe.web.models.admin.*;
import java.util.List;
import pt.ipleiria.dae.gpe.lib.beans.EventBean;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.utilities.EventOrderBy;

/**
 *
 * @author pedroextendssilva
 */
public class EventIndividualListModel {
    
    private EventBean eventBean;
    
    private EventDTO eventDTO;
    
    public int pageId;    
    public final int pageSize = 20;
    public EventOrderBy orderBy;
    public List<EventDTO> users;
    
    public EventIndividualListModel(EventBean eventBean){
        this.eventBean = eventBean;
        this.pageId = 1;
        this.orderBy = EventOrderBy.NameAsc;
    }

    public EventDTO getEventDTO() {
        return eventDTO;
    }

    public void setEventDTO(EventDTO eventDTO) {
        this.eventDTO = eventDTO;
    }
    
    public List<EventDTO> getEvents() {
        return eventBean.findEventsByInternalId(pageId, pageSize, orderBy, this.eventDTO.getInternalId());
    }
    
    
}
