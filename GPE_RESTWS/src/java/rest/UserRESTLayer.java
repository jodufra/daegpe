package rest;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.UserLoginModel;
import pt.ipleiria.dae.gpe.lib.beans.AttendanceBean;
import pt.ipleiria.dae.gpe.lib.beans.UCBean;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.beans.query.options.StudentAttendanceFindOptions;
import pt.ipleiria.dae.gpe.lib.beans.query.options.StudentUCFindOptions;
import pt.ipleiria.dae.gpe.lib.beans.query.order.AttendanceOrderBy;
import pt.ipleiria.dae.gpe.lib.beans.query.order.UCOrderBy;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.utilities.Security;

@Path("users")
public class UserRESTLayer {

    @EJB
    private UserBean userBean;

    @EJB
    private UCBean ucBean;

    @EJB
    private AttendanceBean attendanceBean;

    @PUT
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public UserDTO loginUser(UserLoginModel model) {
        try {
            UserDTO dto = userBean.findByUsername(model.getUsername());
            if (dto != null && dto.getPassword().equals(model.getHashedPassword())) {
                return dto;
            }
        } catch (EntityNotFoundException ex) {
            System.out.println("ERROR: " + ex);
        }
        return null;
    }

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

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id}/ucs")
    //URL: http://localhost:8080/GPE_RESTWS/gpeapi/users/student/ucs
    public List<UCDTO> getUCs(@PathParam("id") String idUser) throws EntityNotFoundException {
        StudentUCFindOptions options = new StudentUCFindOptions(1, 100, UCOrderBy.NameAsc, userBean.find(idUser), "");
        return ucBean.findFromStudent(options);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id}/participacoes")
    //URL: http://localhost:8080/GPE_RESTWS/gpeapi/users/student/participacoes
    public List<AttendanceDTO> getAttendances(@PathParam("id") String idUser) throws EntityNotFoundException {
        StudentAttendanceFindOptions options = new StudentAttendanceFindOptions(1, 100, AttendanceOrderBy.UCNameAsc, userBean.find(idUser), "");
        return attendanceBean.findStudentAttendances(options);
    }

}
