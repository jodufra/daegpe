/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.managers;

import pt.ipleiria.dae.gpe.web.app.AbstractManager;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import java.io.IOException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import static pt.ipleiria.dae.gpe.lib.utilities.Security.GenerateSHA256Hash;

/**
 *
 * @author Joel
 */
@ManagedBean
@SessionScoped
public class UserManager extends AbstractManager {

    private String username;
    private String password;
    private boolean loginFlag = true;
    @EJB
    private UserBean userBean;

    public UserManager() {
    }

    @PostConstruct
    public void init() {
        if (!IsUserAuthenticated()) {
            try {
                Redirect("/login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal != null) {
                request.logout();
            }
            UserDTO user = userBean.findByUsername(username);
            if (user != null) {
                request.login(user.getInternalId(), password);
                externalContext.redirect(GenerateAbsoluteURL(generateUrl()));
            }
        } catch (EntityNotFoundException | ServletException e) {
            context.addMessage(null, new FacesMessage("Nome ou Palavra-chave incorrectos"));
        }
    }

    private String generateUrl() {
        String url = "";
        if (IsUserAdministrator()) {
            url = "/admin/dashboard.xhtml";
        } else if (IsUserManager()) {
            url = "/manager/dashboard.xhtml";
        } else if (IsUserStudent()) {
            url = "/student/dashboard.xhtml";
        }
        return url;
    }

    public void logout() throws IOException, ServletException {
        username = "";
        password = "";
        
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        
        for (String bean : context.getExternalContext().getSessionMap().keySet()) {
            context.getExternalContext().getSessionMap().remove(bean);
        }
        session.invalidate();
        externalContext.redirect(externalContext.getApplicationContextPath() + "/login.xhtml");
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDTO getUser() throws IOException, ServletException, EntityNotFoundException {
        if (!IsUserAuthenticated()) {
            logout();
        }
        return userBean.findByUsername(GetAuthenticatedPrincipal().getName());
    }

}
