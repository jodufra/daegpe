/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pt.ipleiria.dae.gpe.lib.beans.AttendanceBean;
import pt.ipleiria.dae.gpe.lib.beans.query.options.StudentAttendanceFindOptions;
import pt.ipleiria.dae.gpe.lib.beans.query.order.AttendanceOrderBy;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import ws.Session;

@Path("student/attendances")
public class AttendanceRESTLayer {

    @EJB
    private AttendanceBean attendanceBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<AttendanceDTO> getAttendances() {
        return attendanceBean.findStudentAttendances(new StudentAttendanceFindOptions(0, 0, AttendanceOrderBy.UCNameAsc, Session.Current.getUser(), null));
    }

    @GET
    @Path("/{idAttendance}")
    @Produces({MediaType.APPLICATION_JSON})
    public AttendanceDTO getAttendanceDetail(@PathParam("idAttendance") Integer idAttendance) throws EntityNotFoundException {
        return attendanceBean.find(idAttendance);
    }

    @PUT
    @Path("/{idAttendance}/{state}")
    @Produces({MediaType.APPLICATION_JSON})
    public AttendanceDTO updateAttendanceState(@PathParam("idAttendance") Integer idAttendance, @PathParam("state") boolean state) throws EntityNotFoundException, EntityValidationException {
        AttendanceDTO att = attendanceBean.find(idAttendance);
        att.setPresent(state);
        attendanceBean.save(att);
        return attendanceBean.find(idAttendance);
    }
    
}
