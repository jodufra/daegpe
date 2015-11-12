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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import pt.ipleiria.dae.gpe.lib.beans.EventBean;
import pt.ipleiria.dae.gpe.lib.beans.UCBean;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.web.models.admin.EventDetailModel;
import pt.ipleiria.dae.gpe.web.models.admin.EventIndexModel;
import pt.ipleiria.dae.gpe.web.models.admin.UCDetailModel;
import pt.ipleiria.dae.gpe.web.models.admin.UCIndexModel;
import pt.ipleiria.dae.gpe.web.models.admin.UserDetailModel;
import pt.ipleiria.dae.gpe.web.models.admin.UserIndexModel;

/**
 *
 * @author joeld
 */
@ManagedBean
@SessionScoped
public class AdminManager extends AbstractManager {

    @EJB
    private UCBean ucBean;
    @EJB
    private UserBean userBean;
    @EJB
    private EventBean eventBean;

    private UCIndexModel ucIndexModel;
    private UCDetailModel ucDetailModel;
    private UserIndexModel userIndexModel;
    private UserDetailModel userDetailModel;
    private EventIndexModel eventIndexModel;
    private EventDetailModel eventDetailModel;
    private final EnumMap<EntityValidationError, String> errorMessages;

    public AdminManager() {
        errorMessages = new EnumMap<>(EntityValidationError.class);
        errorMessages.put(EntityValidationError.UC_INTERNALID_REQUIRED, "Id da Uc é Obrigatório.");
        errorMessages.put(EntityValidationError.UC_INTERNALID_NOT_UNIQUE, "Internal ID da Uc é Obrigatório.");
        errorMessages.put(EntityValidationError.UC_NAME_REQUIRED, "Nome é Obrigatório.");
        errorMessages.put(EntityValidationError.USER_INTERNALID_REQUIRED, "Id Utilizador é obrigatório.");
        errorMessages.put(EntityValidationError.USER_NAME_REQUIRED, "Nome é obrigatório.");
        errorMessages.put(EntityValidationError.USER_EMAIL_REQUIRED, "Email é obrigatório.");
        errorMessages.put(EntityValidationError.USER_EMAIL_PATTERN, "Email inválido.");
        errorMessages.put(EntityValidationError.USER_USERTYPE_INVALID, "Tipo de Utilizador inválido.");
    }

    @PostConstruct
    public void constructModels() {
        ucIndexModel = new UCIndexModel(ucBean);
        ucDetailModel = new UCDetailModel();
        userIndexModel = new UserIndexModel(userBean);
        userDetailModel = new UserDetailModel();
        eventIndexModel = new EventIndexModel(eventBean);
        eventDetailModel = new EventDetailModel();
    }

    ////////////////////////////////////////////
    ///////////////// UCs //////////////////////
    public void saveUc() {
        UCDTO uc = ucDetailModel.save();
        boolean wasNew = uc.isNew();

        try {
            ucBean.save(uc);
            PresentSuccessMessage("ucdetailform", wasNew ? "Adicionado com sucesso" : "Guardado com sucesso");
        } catch (EntityValidationException eve) {
            PresentErrorMessages("ucdetailform", eve.getEntityValidationErrors(), errorMessages);
        } catch (EntityNotFoundException enf) {
            PresentErrorMessage("ucdetailform", "UC a ser editada, não foi encontrada ou foi removida.");
        }
    }

    public void removeUc(ActionEvent event) throws IOException {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("ucId");
            Object id = param.getValue();
            ucBean.removeById(id);
            PresentSuccessMessage("ucindexform", "UC removida com sucesso.");
        } catch (EntityNotFoundException e) {
            PresentErrorMessage("ucindexform", "A UC que prentendia remover, não foi encontrado.");
        }
    }

    ////////////////////////////////////////////
    ///////////////// Users ///////////////////
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
                SessionManager sessionManager = new SessionManager();
                sessionManager.logout();
            } else {
                PresentSuccessMessage("userindexform", "Utilizador removido com sucesso.");
            }
        } catch (EntityNotFoundException e) {
            PresentErrorMessage("userindexform", "O Utilizador que prentendia remover, não foi encontrado.");
        }
    }

    ////////////////////////////////////////////
    ///////////////// Events ///////////////////
    public void saveEvent() {
        EventDTO eventDTO = this.eventDetailModel.provideEventDTO();
        boolean wasNew = eventDTO.isNew();
        try {
            eventBean.save(eventDTO);
            PresentSuccessMessage("eventdetailform", wasNew ? "Adicionado com sucesso" : "Guardado com sucesso");
        } catch (EntityValidationException eve) {
            PresentErrorMessages("eventdetailform", eve.getEntityValidationErrors(), errorMessages);
        } catch (EntityNotFoundException enf) {
            PresentErrorMessage("eventdetailform", "Utilizador a ser editado, não foi encontrado ou foi removido.");
        }
    }

    public void removeEvent(ActionEvent event) {
        UIParameter param = (UIParameter) event.getComponent().findComponent("deleteEventId");
        Integer id = (Integer) param.getValue();
        eventBean.remove(id);
    }

    ////////////////////////////////////////////
    ///////////////// Models ///////////////////
    public UCIndexModel getUcIndexModel() {
        return ucIndexModel;
    }

    public UCDetailModel getUcDetailModel() {
        return ucDetailModel;
    }

    public UserIndexModel getUserIndexModel() {
        return userIndexModel;
    }

    public UserDetailModel getUserDetailModel() {
        return userDetailModel;
    }

    public EventIndexModel getEventIndexModel() {
        return eventIndexModel;
    }

    public EventDetailModel getEventDetailModel() {
        return eventDetailModel;
    }

}
