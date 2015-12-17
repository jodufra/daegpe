package rest;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pt.ipleiria.dae.gpe.lib.beans.EventBean;
import pt.ipleiria.dae.gpe.lib.beans.query.options.AdminEventFindOptions;
import pt.ipleiria.dae.gpe.lib.beans.query.order.EventOrderBy;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;


@Path("events")
public class EventRESTLayer {
  
    @EJB
    private EventBean eventBean;
    
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<EventDTO> getAllGroups(){
        AdminEventFindOptions options = new AdminEventFindOptions(0, 0, EventOrderBy.NameAsc, "");
        return eventBean.findGroupedByInternalId(options);
    }
    
    @GET
    @Path("/{internalID}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<EventDTO> getAllFromGroup(@PathParam("internalID") String internalID){
        // PRECISA DE SER ALTERADO PARA StudentEventFindOptions
        AdminEventFindOptions options = new AdminEventFindOptions(0, 0, EventOrderBy.NameAsc, ""); 
        return eventBean.findEventsByInternalId(0, 0, EventOrderBy.NameAsc, internalID);
    }
    
    @GET
    @Path("/detail/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public EventDTO getEvent(@PathParam("id") Integer id){
        try {
            return eventBean.find(id);
        } catch (EntityNotFoundException ex) {
            System.out.println("ERROR: " + ex);
        }
        return null;
    }
}
