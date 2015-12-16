
package rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pt.ipleiria.dae.gpe.lib.beans.UCBean;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;

@Path("ucs")
public class UcRESTLayer {
   
    private static final String FAILURE_RESULT = "<result>failure</result>";
    
    @EJB
    private UCBean ucBean;
    
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("all")
    public List<UCDTO> getAll(){
        return ucBean.findAll();
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public UCDTO getUCDTO(@PathParam("id") String internalID){
        try{
            UCDTO ucDTO = ucBean.find(internalID);
            if(ucDTO != null){
                return ucDTO;
            }
        }catch(EntityNotFoundException ex){
            System.out.println("ERROR: " + ex);
        }
        return null;
    }
    
   @POST
   @Path("/new")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   public String updateUser(@FormParam("id") int id){
       System.out.println("ID: " + id);
      return FAILURE_RESULT;
   }
   
   @POST
   @Path("/newJson")
   @Consumes(MediaType.APPLICATION_JSON)
   public String updateUserJson(UCDTO ucDTO){
        System.out.println("ID: " + ucDTO.getName() + " idUC: " + ucDTO.getIdUC() );
        return FAILURE_RESULT;
   }
    

}
