/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import beans.UserBean;
import dtos.UserDTO;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import models.UserDetailModel;
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
    private UserDetailModel userDetailModel;

    @PostConstruct
    private void createModels() {
        userIndexModel = new UserIndexModel(userBean);
        userDetailModel = new UserDetailModel();
    }

    public String saveUser() {
        UserDTO user = userDetailModel.save();
        boolean wasNew = user.isNew();
        List<String> errors = userBean.save(user);
        if (errors.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("userdetail:success", new FacesMessage(wasNew ? "Adicionado com sucesso" : "Guardado com sucesso"));
        } else {
            for (String error : errors) {
                FacesContext.getCurrentInstance().addMessage("userdetail:error", new FacesMessage(error));
            }
        }
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
