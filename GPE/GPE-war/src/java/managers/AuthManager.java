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
public class AuthManager extends AbstractManager {

    public AuthManager() {
    }

    public String attemptAuth() {
        return Redirect("dashboard");
    }

    public String logout() {
        return Redirect("index");
    }

}
