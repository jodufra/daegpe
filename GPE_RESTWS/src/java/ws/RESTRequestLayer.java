package ws;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;

@Path("users")
public class RESTRequestLayer {
    
    @EJB
    private UserBean userBean;
    
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("all")
    public List<UserDTO> getAll(){
        return userBean.findAll();
    }
}
