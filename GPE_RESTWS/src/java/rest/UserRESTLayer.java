package rest;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;

@Path("users")
public class UserRESTLayer {
    
    @EJB
    private UserBean userBean;
    
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public UserDTO getUserDTO(@PathParam("id") int id) {
        try {
            return userBean.find(id);
        } catch (EntityNotFoundException ex) {
            System.out.println("ERROR: " + ex);
        }
        return null;
    }

//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    @Path("/{id}/ucs")
//    @Consumes({MediaType.APPLICATION_JSON})
//    //URL: http://localhost:8080/GPE_RESTWS/gpeapi/users/student/ucs
//    public List<UCDTO> getAll(@PathParam("id") String idUser) throws EntityNotFoundException{
//        StudentUCFindOptions options = new StudentUCFindOptions(1, 100, UCOrderBy.NameAsc, userBean.find(idUser), "");
//        return ucBean.findFromStudent(options);
//    }
}
