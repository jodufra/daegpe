/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.app;

import java.io.IOException;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import pt.ipleiria.dae.gpe.lib.core.EntityValidationError;

/**
 *
 * @author Joel
 */
public abstract class AbstractManager implements Serializable {

    protected String GenerateURL(String url) {
        url += (url.contains("?") ? "&" : "?") + "faces-redirect=true";
        return url;
    }

    protected String GenerateRelativeURL(String url) {
        url = GenerateURL(url);
        if (url.charAt(0) != '/') {
            url = "/" + url;
        }
        return url;
    }

    protected String GenerateAbsoluteURL(String url) {
        if (url.charAt(0) != '/') {
            url = '/' + url;
        }
        if (!url.contains(".xhtml")) {
            url += ".xhtml";
        }
        url = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath() + url;
        return url;
    }

    protected void Redirect(String url) throws IOException {
        url = GenerateAbsoluteURL(url);
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }

    protected void PresentMessage(String clientId, FacesMessage.Severity severity, String summary, String details) {
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        currentInstance.addMessage(clientId, new FacesMessage(severity, summary, details));
    }

    protected void PresentSuccessMessage(String clientId, String summary) {
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        currentInstance.addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, ""));
    }

    protected void PresentErrorMessage(String clientId, String summary) {
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        currentInstance.addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, ""));
    }

    protected void PresentErrorMessages(String clientId, List<EntityValidationError> exceptions, EnumMap<EntityValidationError, String> exceptionMessages) {
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        for (EntityValidationError exception : exceptions) {
            currentInstance.addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_ERROR, exceptionMessages.get(exception), ""));
        }
    }

}
