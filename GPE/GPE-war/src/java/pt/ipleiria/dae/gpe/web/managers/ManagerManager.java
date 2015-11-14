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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import pt.ipleiria.dae.gpe.lib.beans.AttendanceBean;
import pt.ipleiria.dae.gpe.lib.beans.EventBean;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.web.models.manager.EventIndividualListModel;
import pt.ipleiria.dae.gpe.web.models.manager.EventDetailModel;
import pt.ipleiria.dae.gpe.web.models.manager.EventIndexModel;
import pt.ipleiria.dae.gpe.web.models.manager.UserDetailModel;

/**
 *
 * @author joeld
 */
@ManagedBean
@SessionScoped
public class ManagerManager extends AbstractManager {

    @EJB
    private AttendanceBean attendanceBean;
    @EJB
    private EventBean eventBean;
    @EJB
    private UserBean userBean;

    private EventIndexModel eventIndexModel;
    private EventDetailModel eventDetailModel;
    private EventIndividualListModel eventIndividualListModel;
    private UserDetailModel userDetailModel;
    private final EnumMap<EntityValidationError, String> errorMessages;

    public ManagerManager() {
        errorMessages = new EnumMap<>(EntityValidationError.class);
        errorMessages.put(EntityValidationError.USER_NAME_REQUIRED, "Nome é obrigatório.");
        errorMessages.put(EntityValidationError.USER_EMAIL_REQUIRED, "Email é obrigatório.");
        errorMessages.put(EntityValidationError.USER_EMAIL_PATTERN, "Email inválido.");
        errorMessages.put(EntityValidationError.ATTENDANCE_NULL_STUDENT, "Estudante Inválido.");
        errorMessages.put(EntityValidationError.ATTENDANCE_STUDENT_IS_NEW, "Estudante ainda não registado.");
        errorMessages.put(EntityValidationError.ATTENDANCE_USER_NOT_STUDENT, "Utilizador não é Estudante.");
        errorMessages.put(EntityValidationError.ATTENDANCE_NULL_EVENT, "Evento Inválido.");
        errorMessages.put(EntityValidationError.ATTENDANCE_EVENT_IS_NEW, "Evento ainda não registado.");
        errorMessages.put(EntityValidationError.ATTENDANCE_CANT_BE_REPEATED, "Utilizador já está registado numa Presença no Evento.");
    }

    @PostConstruct
    public void constructModels() {
        eventIndexModel = new EventIndexModel(eventBean);
        eventDetailModel = new EventDetailModel(userBean, attendanceBean);
        eventIndividualListModel = new EventIndividualListModel(eventBean);
        userDetailModel = new UserDetailModel();
    }

    ////////////////////////////////////////////
    ///////////////// Users ///////////////////
    public void saveUser() {
        UserDTO user = userDetailModel.provideUserDTO();
        try {
            userBean.save(user);
            PresentSuccessMessage("userdetailform", "Guardado com sucesso");
        } catch (EntityValidationException eve) {
            PresentErrorMessages("userdetailform", eve.getEntityValidationErrors(), errorMessages);
        } catch (EntityNotFoundException enf) {
            PresentErrorMessage("userdetailform", "Utilizador a ser editado, não foi encontrado ou foi removido.");
        }
    }

    ////////////////////////////////////////////
    ///////////////// Attendance ///////////////
    public void addAttendance(ActionEvent e) throws IOException {
        UserDTO user = (UserDTO) ((UIParameter) e.getComponent().findComponent("user")).getValue();
        EventDTO event = eventDetailModel.getEvent();
        AttendanceDTO attedance = new AttendanceDTO(user, event);
        try {
            attendanceBean.save(attedance);
            PresentSuccessMessage("eventattendancesform", "Adicionado com sucesso");
        } catch (EntityValidationException eve) {
            PresentErrorMessages("eventattendancesform", eve.getEntityValidationErrors(), errorMessages);
        } catch (EntityNotFoundException enf) {
            PresentErrorMessage("eventattendancesform", "Verifique que o Estudante e o Evento ainda existem.");
        }
    }

    public void updateEventAttendanceState() {
        EventDTO event = eventDetailModel.getEvent();
        if (!(!event.isAttendanceActive() && event.isAttendanceActivated())) {
            if (!event.isAttendanceActivated()) {
                event.setAttendanceActive(true);
                event.setAttendanceActivated(true);
                event.setAttendancePassword(eventDetailModel.getAttendancePassword());
            } else if (event.isAttendanceActive() && event.isAttendanceActivated()) {
                event.setAttendanceActive(false);
            }
            try {
                eventBean.save(event);
            } catch (EntityValidationException ex) {
                PresentErrorMessages("eventattendancesform", ex.getEntityValidationErrors(), errorMessages);
            } catch (EntityNotFoundException ex) {
                PresentErrorMessage("eventattendancesform", "Verifique que o Evento ainda existe.");
            }
        }
    }

    public void updateAttendanceState(ActionEvent e) throws IOException {
       
    }

    ////////////////////////////////////////////
    ///////////////// Models ///////////////////
    public EventIndexModel getEventIndexModel() {
        return eventIndexModel;
    }

    public EventDetailModel getEventDetailModel() {
        return eventDetailModel;
    }

    public EventIndividualListModel getEventIndividualListModel() {
        return eventIndividualListModel;
    }

    public UserDetailModel getUserDetailModel() {
        return userDetailModel;
    }

}
