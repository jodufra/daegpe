/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.managers;

import java.io.IOException;
import pt.ipleiria.dae.gpe.web.app.AbstractManager;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.core.EntityValidationError;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.web.models.UserDetailModel;
import pt.ipleiria.dae.gpe.web.models.UserIndexModel;

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
    private final EnumMap<EntityValidationError, String> errorMessages;

    public UsersManager() {
        errorMessages = new EnumMap<>(EntityValidationError.class);
        errorMessages.put(EntityValidationError.USER_INTERNALID_REQUIRED, "Id Utilizador é obrigatório.");
        errorMessages.put(EntityValidationError.USER_NAME_REQUIRED, "Nome é obrigatório.");
        errorMessages.put(EntityValidationError.USER_EMAIL_REQUIRED, "Email é obrigatório.");
        errorMessages.put(EntityValidationError.USER_EMAIL_PATTERN, "Email inválido.");
        errorMessages.put(EntityValidationError.USER_USERTYPE_INVALID, "Tipo de Utilizador inválido.");
    }

    @PostConstruct
    public void constructModels() {
        userIndexModel = new UserIndexModel(userBean);
        userDetailModel = new UserDetailModel();
    }

    public void saveUser() {
        UserDTO user = userDetailModel.provideUserDTO();
        boolean wasNew = user.isNew();
        try {
            userBean.save(user);
            PresentSuccessMessage("userdetailform", wasNew ? "Adicionado com sucesso" : "Guardado com sucesso");
        } catch (EntityValidationException eve) {
            PresentErrorMessages("userdetailform", eve.getEntityValidationErrors(), errorMessages);
        } catch (EntityNotFoundException enf) {
            PresentErrorMessage("userdetailform", "Utilizador a ser editado, não foi encontrado ou foi removido.");
        }
    }

    public void removeUser(ActionEvent event) throws IOException {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("userId");
            Object id = param.getValue();
            userBean.removeById(id);

            UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
            if (Objects.equals(user.getIdUser(), id)) {
                AuthManager authManager = new AuthManager();
                authManager.logout();
            } else {
                PresentSuccessMessage("userindexform", "Utilizador removido com sucesso.");
            }
        } catch (EntityNotFoundException e) {
            PresentErrorMessage("userindexform", "O Utilizador que prentendia remover, não foi encontrado.");
        }
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