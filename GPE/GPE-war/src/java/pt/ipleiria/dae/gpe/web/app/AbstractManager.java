/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.app;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

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

}
