/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

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
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;

@Path("attendances")
public class AttendanceRESTLayer {
    
    @EJB
    private AttendanceBean attendanceBean;
    
    
    @GET
    @Path("/{idUser}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<AttendanceDTO> getStudentAttendances(){
        return attendanceBean.findAll();
    }
    
}
