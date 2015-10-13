/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import beans.UserBean;
import dtos.UserDTO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Joel
 */
@ManagedBean
@ViewScoped
public class AuthManager extends AbstractManager {

    private String username;
    private String password;
    private String originalURL;

    public AuthManager() {
    }

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        originalURL = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

        if (originalURL == null) {
            originalURL = externalContext.getRequestContextPath() + "/dashboard.xhtml";
        } else {
            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);
            if (originalQuery != null) {
                originalURL += "?" + originalQuery;
            }
        }

        if (externalContext.getSessionMap().get("user") != null) {
            try { 
                Redirect("/dashboard.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AuthManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @EJB
    private UserBean userBean;

    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        UserDTO user = userBean.find(username, password);
        password = "";
        if (user != null) {
            externalContext.getSessionMap().put("user", user);
            externalContext.redirect(originalURL);
        } else {
            context.addMessage(null, new FacesMessage("Nome de Utilizador ou Palavra-chave incorrectas"));
        }
    }

    public void logout() throws IOException {
        username = "";
        password = "";
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();

        externalContext.redirect(externalContext.getApplicationContextPath() + "/index.xhtml");
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

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

}
