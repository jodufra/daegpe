/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.LinkedList;
import java.util.List;
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
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;

@Path("attendances")
public class AttendanceRESTLayer {
    
    @EJB
    private AttendanceBean attendanceBean;
    
    @EJB 
    private EventBean eventBean;
    
    @EJB
    private UserBean userBean;
    
    
    @GET
    @Path("/user/{idUser}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<AttendanceDTO> getStudentAttendances(){
        return attendanceBean.findAll();
    }
    
    @GET
    @Path("/{attendanceID}/{eventID}/{userID}")
    @Produces({MediaType.APPLICATION_JSON})
    public AttendanceDTO getAttendanceInfo(@PathParam("attendanceID") String attendanceID, @PathParam("eventID") Integer eventID, @PathParam("userID") String userInternalID) throws EntityNotFoundException{
        System.out.println("EVENTOSS: " + eventID);
        EventDTO event = eventBean.find(eventID);
        UserDTO user = userBean.find(userInternalID);
        System.out.println("USER: " + user.getName());
        System.out.println("EVENT: " + event.getName());
        AttendanceDTO attendance = attendanceBean.find(event, user);
        System.out.println("ATT: " + attendance);
        return attendanceBean.find(event, user);
    }
    
    
    @PUT
    @Path("/{attendanceID}/{eventID}/{userID}/{state}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public AttendanceDTO updateAttendancePresence(@PathParam("attendanceID") String attendanceID, @PathParam("eventID") Integer eventID, @PathParam("userID") String userInternalID, @PathParam("state") boolean newState) throws EntityNotFoundException, EntityValidationException{
        System.out.println("EVENTOSS: " + eventID);
        System.out.println("NewState: " + newState);
        EventDTO event = eventBean.find(eventID);
        UserDTO user = userBean.find(userInternalID);
        AttendanceDTO attendance = attendanceBean.find(event, user);
        attendance.setPresent(newState);
        attendanceBean.save(attendance);
        return attendanceBean.find(event, user);
    }
    
}
