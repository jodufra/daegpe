/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.managers;

import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import pt.ipleiria.dae.gpe.web.app.AbstractManager;
import pt.ipleiria.dae.gpe.lib.beans.EventBean;
import pt.ipleiria.dae.gpe.lib.core.EntityValidationError;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.web.models.EventDetailModel;
import pt.ipleiria.dae.gpe.web.models.EventIndexModel;

@ManagedBean
@SessionScoped
public class EventsManager extends AbstractManager{
    
    private final EnumMap<EntityValidationError, String> errorMessages = new EnumMap<>(EntityValidationError.class);
    
    @EJB
    private EventBean eventBean;
    
    private EventIndexModel eventIndexModel;
    private EventDetailModel eventDetailModel;
    List<EntityValidationError> errors;
   
    
    @PostConstruct
    private void initUsersManager(){
        eventIndexModel = new EventIndexModel(eventBean);
        eventDetailModel = new EventDetailModel();
        errors = new ArrayList<>();
        errorMessages.put(EntityValidationError.USER_INTERNALID_REQUIRED, "Id de Utilizador é Obrigatório.");  
        errorMessages.put(EntityValidationError.USER_NAME_REQUIRED, "Nome é Obrigatório.");
    }
    
    public EventsManager() {
    }

    public void saveEvent()
    {
        errors.clear();
        EventDTO eventDTO = this.eventDetailModel.save();
        boolean wasNew = eventDTO.isNew();
        try {
            eventBean.save(eventDTO);
            PresentSuccessMessage("userdetailform", wasNew ? "Adicionado com sucesso" : "Guardado com sucesso");
        } catch (EntityValidationException eve) {
            PresentErrorMessages("userdetailform", eve.getEntityValidationErrors(), errorMessages);
        } catch (EntityNotFoundException enf) {
            PresentErrorMessage("userdetailform", "Utilizador a ser editado, não foi encontrado ou foi removido.");
        }
    }
    
    public EventIndexModel getEventIndexModel() {
        return this.eventIndexModel;
    }

    public void setEventIndexModel(EventIndexModel eventIndexModel) {
        this.eventIndexModel = eventIndexModel;
    }
    
    public EventBean getEventBean() {
        return eventBean;
    }

    public void setEventBean(EventBean eventBean) {
        this.eventBean = eventBean;
    }

    public EventDetailModel getEventDetailModel() {
        return eventDetailModel;
    }

    public void setEventDetailModel(EventDetailModel eventDetailModel) {
        this.eventDetailModel = eventDetailModel;
    }

    public List<EntityValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<EntityValidationError> errors) {
        this.errors = errors;
    }


    
    
    public String removeEvent()
    {
        this.errors.clear();
        return GenerateRelativeURL("/events/index");
    }
    
    
    public void remove(ActionEvent event)
    {
         UIParameter param = (UIParameter) event.getComponent().findComponent("deleteEventId");
        System.out.println("VALOR: " + param.getValue());
       
        Integer id = (Integer) param.getValue();
        
        eventBean.remove(id);
    }
    
}
