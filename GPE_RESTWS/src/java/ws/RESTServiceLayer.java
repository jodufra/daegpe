
package ws;


import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;

@Path("/users")
public class RESTServiceLayer {
    
    @EJB
    private UserBean userBean;
    
    @GET
    @Path("alla")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_XML})
    public String getAll(){
        return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
    }
    
    @GET
    @Path("a")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_XML})
    public String getA(){
        String a = "";
        for(UserDTO user: userBean.findAll()){
            a += user.getName() + "\n";
        }
        return "<?xml version=\"1.0\"?> <hello>" + a + "</hello>";
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML})
    @Path("all")
    public List<String> getAllS(){
        List<String> coisas = new LinkedList<>();
        coisas.add("pedro");
        return coisas;
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML})
    @Path("allp")
    public List<UserDTO> getAllp(){
        return userBean.findAll();
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("allaa")
    public Response getAllSS(){
        return Response.ok(userBean.findAll()).build();
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("al")
    public UserDTO getAl(){
        return userBean.findFirst();
    }
    
}
