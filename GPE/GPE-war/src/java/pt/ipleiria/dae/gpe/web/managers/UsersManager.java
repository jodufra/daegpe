/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.managers;

import pt.ipleiria.dae.gpe.web.app.AbstractManager;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.core.EntityValidationError;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import java.util.EnumMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import pt.ipleiria.dae.gpe.web.models.UserDetailModel;
import pt.ipleiria.dae.gpe.web.models.UserIndexModel;

/**
 *
 * @author joeld
 */
@ManagedBean
@SessionScoped
public class UsersManager extends AbstractManager {
    
    private final EnumMap<EntityValidationError, String> errorMessages = new EnumMap<>(EntityValidationError.class);

    @EJB
    private UserBean userBean;

    private UserIndexModel userIndexModel;
    private UserDetailModel userDetailModel;
        
    @PostConstruct
    private void initUsersManager(){
        userIndexModel = new UserIndexModel(userBean);
        userDetailModel = new UserDetailModel();
        errorMessages.put(EntityValidationError.USER_INTERNALID_REQUIRED, "Id Utilizador é obrigatório.");        
        errorMessages.put(EntityValidationError.USER_NAME_REQUIRED, "Nome é obrigatório.");
        errorMessages.put(EntityValidationError.USER_EMAIL_REQUIRED, "Email é obrigatório.");
        errorMessages.put(EntityValidationError.USER_EMAIL_PATTERN, "Email inválido.");
        errorMessages.put(EntityValidationError.USER_USERTYPE_INVALID, "Tipo de Utilizador inválido.");
    }

    public String saveUser() {
        UserDTO user = userDetailModel.provideUserDTO();
        boolean wasNew = user.isNew();
        List<EntityValidationError> errors = userBean.save(user);

        FacesContext currentInstance = FacesContext.getCurrentInstance();

        if (errors.isEmpty()) {
            currentInstance.addMessage("userdetailform", new FacesMessage(FacesMessage.SEVERITY_INFO, wasNew ? "Adicionado com sucesso" : "Guardado com sucesso", ""));
        } else {
            for (EntityValidationError error : errors) {
                currentInstance.addMessage("userdetailform", new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessages.get(error), ""));
            }
        }

        return "";
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
