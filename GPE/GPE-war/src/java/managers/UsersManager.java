/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import beans.UserBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import models.UserIndexModel;

/**
 *
 * @author joeld
 */
@ManagedBean
@SessionScoped
public class UsersManager extends AbstractManager {

    @EJB
    private UserBean userBean;
    
    private UserIndexModel userIndexModel;

    public UsersManager() {
    }

    public String listUsers() {
        userIndexModel = new UserIndexModel();
        userIndexModel.users = userBean.find(userIndexModel.pageId, userIndexModel.pageSize, userIndexModel.orderBy);
        return Redirect("/users/index");
    }

    public String removeUser() {
        return Redirect("/users/index");
    }

    public String newUser() {
        return Redirect("/users/detail");
    }

    public String editUser() {
        return Redirect("/users/detail");
    }

    public UserIndexModel getUserIndexModel() {
        return userIndexModel;
    }

    public void setUserIndexModel(UserIndexModel userIndexModel) {
        this.userIndexModel = userIndexModel;
    }

}
