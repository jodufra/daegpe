/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import beans.UserBean;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import models.UserDetailModel;
import models.UserIndexModel;

/**
 *
 * @author joeld
 */
@ManagedBean
@ViewScoped
public class UsersManager extends AbstractManager {

    @EJB
    private UserBean userBean;
    
    private UserIndexModel userIndexModel;
    private UserDetailModel userDetailModel;

    @PostConstruct
    private void createModels(){
        userIndexModel = new UserIndexModel(userBean);
        userDetailModel = new UserDetailModel();
    }
    
    public void newUser() {
        userDetailModel.newUser();
        try {
            Redirect("users/detail");
        } catch (IOException ex) {
            Logger.getLogger(UsersManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String saveUser() {
        return GenerateRelativeURL("/users/detail"); 
    }
    
    public String removeUser() {
        return GenerateRelativeURL("/users/index"); 
    }

    public UserIndexModel getUserIndexModel() {
        return userIndexModel;
    }

    public void setUserIndexModel(UserIndexModel userIndexModel) {
        this.userIndexModel = userIndexModel;
    }

    public UserDetailModel getUserDetailModel() {
        return userDetailModel;
    }

    public void setUserDetailModel(UserDetailModel userDetailModel) {
        this.userDetailModel = userDetailModel;
    }
}
