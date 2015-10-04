/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Beans.UserFacade;
import javax.inject.Inject;
import javax.ws.rs.GET;

/**
 *
 * @author joeld
 */
public class HomeController {

    @Inject
    UserFacade userFacade;

    @GET
    public String Index() {
        //models.put("model", new HomeIndexModel((new Date()).toString()));
        return "/Home/home.xhtml";
    }

}
