/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import dtos.UserDTO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author joeld
 */
@ManagedBean
public class SessionManager extends AbstractManager {

    private UserDTO user;

    @PostConstruct
    private void setUser() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            user = (UserDTO) externalContext.getSessionMap().get("user");

            if (user == null) {
                externalContext.invalidateSession();
                throw new Exception("User not logged in");
            }
        } catch (Exception e) {
            Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, e);
            try {
                Redirect("index");
            } catch (IOException ex) {
                Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public UserDTO getUser() {
        return user;
    }

}
