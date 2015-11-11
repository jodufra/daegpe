/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.managers;

import java.util.EnumMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import pt.ipleiria.dae.gpe.lib.beans.UCBean;
import pt.ipleiria.dae.gpe.lib.core.EntityValidationError;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.web.app.AbstractManager;
import pt.ipleiria.dae.gpe.web.models.UCDetailModel;
import pt.ipleiria.dae.gpe.web.models.UCIndexModel;

/**
 *
 * @author LuoSKraD
 */
@ManagedBean
@SessionScoped
public class UcsManager extends AbstractManager {

    private final EnumMap<EntityValidationError, String> errorMessages = new EnumMap<>(EntityValidationError.class);

    @EJB
    private UCBean ucBean;
    private UCIndexModel ucIndexModel;
    private UCDetailModel ucDetailModel;

    @PostConstruct
    private void initUcsManager() {
        ucIndexModel = new UCIndexModel(ucBean);
        ucDetailModel = new UCDetailModel();
        errorMessages.put(EntityValidationError.UC_INTERNAL_REQUIRED, "Id da Uc é Obrigatório.");
        errorMessages.put(EntityValidationError.UC_INTERNALID_NOT_UNIQUE, "Internal ID da Uc é Obrigatório.");
        errorMessages.put(EntityValidationError.UC_NAME_REQUIRED, "Nome é Obrigatório.");
    }

       public String saveUc() {
        UCDTO uc = ucDetailModel.save();
        boolean wasNew = uc.isNew();
        List<EntityValidationError> errors = ucBean.save(uc);

        
        FacesContext currentInstance = FacesContext.getCurrentInstance();

        if (errors.isEmpty()) {
            currentInstance.addMessage("ucdetailform", new FacesMessage(FacesMessage.SEVERITY_INFO, wasNew ? "Adicionada com sucesso" : "Guardada com sucesso", ""));
        } else {
            for (EntityValidationError error : errors) {
                currentInstance.addMessage("ucdetailform", new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessages.get(error), ""));
            }
        }

        return "";
    }
    
    
    public String removeUc() {
        return GenerateRelativeURL("/ucs/index");
    }

    public UCIndexModel getUcIndexModel() {
        return ucIndexModel;
    }

    public void setUcIndexModel(UCIndexModel ucIndexModel) {
        this.ucIndexModel = ucIndexModel;
    }

    public UCDetailModel getUcDetailModel() {
        return ucDetailModel;
    }

    public void setUcDetailModel(UCDetailModel ucDetailModel) {
        this.ucDetailModel = ucDetailModel;
    }

}
