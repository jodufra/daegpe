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
import pt.ipleiria.dae.gpe.lib.exceptions.EntityIsNullException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;

/**
 *
 * @author Joel
 */
@ManagedBean
@SessionScoped
public class SessionManager extends AbstractManager {

    private UserDTO user;
    private String username;
    private String password;
    @EJB
    private UserBean userBean;

    public SessionManager() {
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getExternalContext().getSessionMap().get("user") == null) {
            try {
                Redirect("/login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private String generateUrl() throws EntityIsNullException {
        if (user == null) {
            throw new EntityIsNullException();
        }
        String url = "";
        if (UserDTO.IsAdministrator(user)) {
            url = "/admin/dashboard.xhtml";
        } else if (UserDTO.IsManager(user)) {
            url = "/manager/dashboard.xhtml";
        } else if (UserDTO.IsStudent(user)) {
            url = "/student/dashboard.xhtml";
        }
        return url;
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
            user = userBean.find(username, password);
            password = "";
            externalContext.getSessionMap().put("user", user);
            if (user != null) {
                externalContext.redirect(GenerateAbsoluteURL(generateUrl()));
            }
        } catch (EntityNotFoundException | ServletException ex) {
            context.addMessage(null, new FacesMessage("Nome ou Palavra-chave incorrectos"));
        } catch (EntityIsNullException ex) {
            context.addMessage(null, new FacesMessage("Utilizador sem permissões para acesso."));
        }
    }

    public void logout() throws IOException {
        username = "";
        password = "";
        user = null;
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
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

    public UserDTO getUser() throws IOException {
        if (user == null) {
            logout();
        }
        return user;
    }

}
