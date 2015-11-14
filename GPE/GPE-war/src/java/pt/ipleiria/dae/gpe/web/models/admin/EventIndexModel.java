/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models.admin;

import java.util.List;
import pt.ipleiria.dae.gpe.lib.beans.EventBean;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.utilities.EventOrderBy;

/**
 *
 * @author pedroextendssilva
 */
public class EventIndexModel {
    
    private final EventBean eventBean;
    
    
    public int pageId;    
    public final int pageSize = 20;
    public EventOrderBy orderBy;
    public List<EventDTO> users;
    
    public EventIndexModel(EventBean eventBean)
    {
        this.eventBean = eventBean;
        this.pageId = 1;
        this.orderBy = EventOrderBy.NameAsc;
    }
    
    public List<EventDTO> getEvents() {
        return eventBean.findUnique(pageId, pageSize, orderBy);
    }
    
    
}
