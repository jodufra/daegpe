/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Joel
 */
@ManagedBean
@RequestScoped
public class RedirectManager extends AbstractManager {

    public RedirectManager() {
    }

    @Override
    public String Redirect(String url) {
        return super.Redirect(url);
    }
}
