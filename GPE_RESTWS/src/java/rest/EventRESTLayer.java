package rest;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pt.ipleiria.dae.gpe.lib.beans.AttendanceBean;
import pt.ipleiria.dae.gpe.lib.beans.EventBean;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.beans.query.options.AdminEventFindOptions;
import pt.ipleiria.dae.gpe.lib.beans.query.order.EventOrderBy;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;


@Path("events")
public class EventRESTLayer {
  
    @EJB
    private EventBean eventBean;
    
    @EJB
    private AttendanceBean attendanceBean;
    
    @EJB
    private UserBean userBean;
    
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("all")
    public List<EventDTO> getAll(){
        return eventBean.findAll();
    }
    
    
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
    
    
    //addStudentsToEvent()
    @PUT
    @Path("/{userID}/{eventID}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String subscibeStudentEvent(@PathParam("userID") String userID, @PathParam("eventID") String eventID) throws EntityNotFoundException, EntityValidationException{
        UserDTO userDTO = userBean.find("student");
        EventDTO eventDTO = eventBean.find("DAE (T)");
        System.out.println("email: " + userDTO.getEmail());
        System.out.println("event: " + eventDTO.getName());
        
        AttendanceDTO attendance = new AttendanceDTO(1, userDTO, eventDTO, false);
        AttendanceDTO attendance2 = new AttendanceDTO(2,userDTO, eventDTO, false);
        attendanceBean.save(attendance);
        attendanceBean.save(attendance2);
        
        System.out.println("user: " + userID + " - " + eventID);
        return "";
    }
    
    
}
