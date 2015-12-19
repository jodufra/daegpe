package rest;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pt.ipleiria.dae.gpe.lib.beans.AttendanceBean;
import pt.ipleiria.dae.gpe.lib.beans.UCBean;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;

@Path("student/users")
public class UserRESTLayer {

    @EJB
    private UserBean userBean;

    @EJB
    private UCBean ucBean;

    @EJB
    private AttendanceBean attendanceBean;

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public UserDTO getUser(@PathParam("id") int id) {
        try {
            return userBean.find(id);
        } catch (EntityNotFoundException ex) {
            System.out.println("ERROR: " + ex);
        }
        return null;
    }

}
