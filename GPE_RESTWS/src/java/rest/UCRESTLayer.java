package rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pt.ipleiria.dae.gpe.lib.beans.UCBean;
import pt.ipleiria.dae.gpe.lib.beans.query.options.StudentUCFindOptions;
import pt.ipleiria.dae.gpe.lib.beans.query.order.UCOrderBy;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import ws.Session;

@Path("student/ucs")
public class UCRESTLayer {

    @EJB
    private UCBean ucBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<UCDTO> getAll() {
        return ucBean.findFromStudent(new StudentUCFindOptions(0, 0, UCOrderBy.NameAsc, Session.Current.getUser(), null));
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public UCDTO getUCDTO(@PathParam("id") int id) {
        try {
            return ucBean.find(id);
        } catch (EntityNotFoundException ex) {
            System.out.println("ERROR: " + ex);
        }
        return null;
    }

}
