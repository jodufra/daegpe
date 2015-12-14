/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.app;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author joeld
 */
@ManagedBean
@RequestScoped
public class RedirectManager extends AbstractManager {

    public RedirectManager() {
    }

    @Override
    public String GenerateRelativeURL(String url) {
        return super.GenerateRelativeURL(url);
    }

    @Override
    public String GenerateAbsoluteURL(String url) {
        return super.GenerateAbsoluteURL(url);
    }
}
