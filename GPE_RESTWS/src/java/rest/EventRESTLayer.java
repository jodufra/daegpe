package rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pt.ipleiria.dae.gpe.lib.beans.EventBean;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;


@Path("events")
public class EventRESTLayer {
  
    @EJB
    private EventBean eventBean;
    
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("all")
    public List<EventDTO> getAll(){
        return eventBean.findAll();
    }
}
