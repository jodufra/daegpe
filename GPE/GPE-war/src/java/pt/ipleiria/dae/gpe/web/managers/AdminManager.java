/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.managers;

import java.io.IOException;
import java.util.Collection;
import pt.ipleiria.dae.gpe.web.app.AbstractManager;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.core.EntityValidationError;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import java.util.EnumMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletException;
import pt.ipleiria.dae.gpe.lib.beans.AttendanceBean;
import pt.ipleiria.dae.gpe.lib.beans.EventBean;
import pt.ipleiria.dae.gpe.lib.beans.UCBean;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.entities.EventGroup;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.web.models.admin.EventDetailModel;
import pt.ipleiria.dae.gpe.web.models.admin.EventGroupDetailModel;
import pt.ipleiria.dae.gpe.web.models.admin.EventGroupIndexModel;
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

    @EJB
    private AttendanceBean attendanceBean;

    private EventIndexModel eventIndexModel;
    private EventDetailModel eventDetailModel;
    private EventGroupIndexModel eventGroupIndexModel;
    private EventGroupDetailModel eventGroupDetailModel;
    private UCIndexModel ucIndexModel;
    private UCDetailModel ucDetailModel;
    private UserIndexModel userIndexModel;
    private UserDetailModel userDetailModel;
    private final EnumMap<EntityValidationError, String> errorMessages;

    public AdminManager() {
        errorMessages = new EnumMap<>(EntityValidationError.class);
        errorMessages.put(EntityValidationError.UC_INTERNALID_REQUIRED, "Id da Uc é Obrigatório.");
        errorMessages.put(EntityValidationError.UC_INTERNALID_NOT_UNIQUE, "Internal ID da Uc é Obrigatório.");
        errorMessages.put(EntityValidationError.UC_NAME_REQUIRED, "Nome é Obrigatório.");
        errorMessages.put(EntityValidationError.UC_IS_NEW, "A UC ainda não existe.");
        errorMessages.put(EntityValidationError.USER_INTERNALID_REQUIRED, "Id Utilizador é obrigatório.");
        errorMessages.put(EntityValidationError.USER_USERGROUP_INVALID, "Tipo de Utilizador inválido.");
        errorMessages.put(EntityValidationError.USER_NAME_REQUIRED, "Nome é obrigatório.");
        errorMessages.put(EntityValidationError.USER_EMAIL_REQUIRED, "Email é obrigatório.");
        errorMessages.put(EntityValidationError.USER_EMAIL_PATTERN, "Email inválido.");
        errorMessages.put(EntityValidationError.USER_IS_NOT_ADMIN, "O Utilizador não é Administrador.");
        errorMessages.put(EntityValidationError.USER_IS_NOT_MANAGER, "O Utilizador não é Gestor.");
        errorMessages.put(EntityValidationError.USER_IS_NOT_STUDENT, "O Utilizador não é Estudante.");
        errorMessages.put(EntityValidationError.USER_IS_NEW, "O Utilizador ainda não existe.");
        errorMessages.put(EntityValidationError.USER_IS_REQUIRED, "O Utilizador é obrigatório.");
        errorMessages.put(EntityValidationError.USER_INVALID, "O ID inserido não é valido");
        errorMessages.put(EntityValidationError.EVENT_WEEK_INVALID, "As semanas do Evento estão incorrectas");
        errorMessages.put(EntityValidationError.EVENT_NOT_FOUND, "Evento por criar");
        errorMessages.put(EntityValidationError.EVENT_ALREADY_HAVE_STUDENT, "O Estudante já se encontra associado ao Evento");
        errorMessages.put(EntityValidationError.ATTENDANCE_NULL_STUDENT, "Estudante Inválido.");
        errorMessages.put(EntityValidationError.ATTENDANCE_STUDENT_IS_NEW, "Estudante ainda não registado.");
        errorMessages.put(EntityValidationError.ATTENDANCE_USER_NOT_STUDENT, "Utilizador não é Estudante.");
        errorMessages.put(EntityValidationError.ATTENDANCE_NULL_EVENT, "Evento Inválido.");
        errorMessages.put(EntityValidationError.ATTENDANCE_EVENT_IS_NEW, "Evento ainda não registado.");
        errorMessages.put(EntityValidationError.ATTENDANCE_CANT_BE_REPEATED, "Utilizador já está registado numa Presença no Evento.");
        errorMessages.put(EntityValidationError.EVENTGROUP_DATESTART_REQUIRED, "A Data de Inicio é obrigatória.");
        errorMessages.put(EntityValidationError.EVENTGROUP_DATEEND_REQUIRED, "A Data de Fim é obrigatória.");
        errorMessages.put(EntityValidationError.EVENTGROUP_DATESTART_HIGHER_DATEEND, "A Data de Inicio não pode ser superior à Data de Fim.");
        errorMessages.put(EntityValidationError.EVENTGROUP_DAYOFWEEK_REQUIRED, "Seleccione pelo menos um dia da semana.");
    }

    @PostConstruct
    public void constructModels() {
        ucIndexModel = new UCIndexModel(ucBean);
        ucDetailModel = new UCDetailModel(ucBean, userBean);
        userIndexModel = new UserIndexModel(userBean);
        userDetailModel = new UserDetailModel();
        eventIndexModel = new EventIndexModel(eventBean);
        eventDetailModel = new EventDetailModel(eventBean, ucBean, userBean);
        eventGroupIndexModel = new EventGroupIndexModel(eventBean);
        eventGroupDetailModel = new EventGroupDetailModel(ucBean, userBean);
    }

    ////////////////////////////////////////////
    ///////////////// UCs //////////////////////
    public void saveUC() {
        UCDTO uc = ucDetailModel.provideUCDTO();
        boolean wasNew = uc.isNew();

        try {
            ucBean.save(uc);
            ucDetailModel.setUc(ucBean.find(uc.getInternalId()));
            PresentSuccessMessage("ucdetailform", wasNew ? "Adicionado com sucesso" : "Guardado com sucesso");
        } catch (EntityValidationException eve) {
            PresentErrorMessages("ucdetailform", eve.getEntityValidationErrors(), errorMessages);
        } catch (EntityNotFoundException enf) {
            PresentErrorMessage("ucdetailform", "UC a ser editada, não foi encontrada ou foi removida.");
        }
    }

    public void removeUC(ActionEvent event) throws IOException {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("ucId");
            Object id = param.getValue();
            ucBean.removeById(id);
            PresentSuccessMessage("ucindexform", "UC removida com sucesso.");
        } catch (EntityNotFoundException e) {
            PresentErrorMessage("ucindexform", "A UC que prentendia remover, não foi encontrado.");
        }
    }

    public void addStudentUC(ActionEvent event) throws IOException {
        UserDTO user = (UserDTO) ((UIParameter) event.getComponent().findComponent("user")).getValue();
        UCDTO uc = ucDetailModel.provideUCDTO();

        try {
            ucBean.addStudentUC(uc, user);
            userBean.addUCStudent(user, uc);
            PresentSuccessMessage("ucstudentsform", "Adicionado com sucesso");
        } catch (EntityValidationException eve) {
            PresentErrorMessages("ucstudentsform", eve.getEntityValidationErrors(), errorMessages);
        } catch (EntityNotFoundException enf) {
            PresentErrorMessage("ucstudentsform", "Verifique que o Estudante e a UC ainda existem.");
        }
    }

    public void removeStudentUC(ActionEvent event) throws IOException {
        UserDTO user = (UserDTO) ((UIParameter) event.getComponent().findComponent("user")).getValue();
        UCDTO uc = ucDetailModel.provideUCDTO();
        try {
            ucBean.removeStudentUC(uc, user);
            ucDetailModel.setUc(ucBean.find(uc.getIdUC()));
            PresentSuccessMessage("ucstudentsform", "Removido com sucesso");
        } catch (EntityValidationException eve) {
            PresentErrorMessages("ucstudentsform", eve.getEntityValidationErrors(), errorMessages);
        } catch (EntityNotFoundException ex) {
            PresentErrorMessage("ucstudentsform", "Verifique que o Estudante e a UC ainda existem.");
        }
    }

    ////////////////////////////////////////////
    ///////////////// Users ///////////////////
    public void saveUser() {
        UserDTO user = userDetailModel.provideUserDTO();
        boolean wasNew = user.isNew();
        try {
            userBean.save(user);
            userDetailModel.setUser(userBean.find(user.getInternalId()));
            PresentSuccessMessage("userdetailform", wasNew ? "Adicionado com sucesso" : "Guardado com sucesso");
        } catch (EntityValidationException eve) {
            PresentErrorMessages("userdetailform", eve.getEntityValidationErrors(), errorMessages);
        } catch (EntityNotFoundException enf) {
            PresentErrorMessage("userdetailform", "Utilizador a ser editado, não foi encontrado ou foi removido.");
        }
    }

    public void removeUser(ActionEvent event) throws IOException, ServletException {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("userId");
            Object id = param.getValue();
            userBean.removeById(id);

            UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
            if (Objects.equals(user.getIdUser(), id)) {
                UserManager sessionManager = new UserManager();
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
    public void saveEventGroup() {
        EventGroup eventGroup = this.eventGroupDetailModel.provideEventGroup();
        try {
            eventBean.save(eventGroup);
            eventIndexModel.setInternalId(eventGroup.getInternalId());
            Redirect("admin/events-index");
        } catch (EntityValidationException eve) {
            PresentErrorMessages("eventgroupdetailform", eve.getEntityValidationErrors(), errorMessages);
        } catch (EntityNotFoundException enf) {
            PresentErrorMessage("eventgroupdetailform", "Verifique se o Gestor do Evento e a Unidade Curricular ainda existem.");
        } catch (IOException ex) {
            PresentErrorMessage("eventgroupdetailform", ex.getMessage());
        }
    }

    public void saveEvent() {
        EventDTO eventDTO = this.eventDetailModel.provideEventDTO();
        boolean wasNew = eventDTO.isNew();
        try {
            eventBean.save(eventDTO);
            PresentSuccessMessage("eventdetailform", wasNew ? "Adicionado com sucesso" : "Guardado com sucesso");
        } catch (EntityValidationException eve) {
            PresentErrorMessages("eventdetailform", eve.getEntityValidationErrors(), errorMessages);
        } catch (EntityNotFoundException enf) {
            PresentErrorMessage("eventdetailform", "Evento a ser editado não foi encontrado ou foi removido.");
        }
    }

    public void removeEvent(ActionEvent event) {
        UIParameter param = (UIParameter) event.getComponent().findComponent("deleteEventId");
        Integer id = (Integer) param.getValue();
        eventBean.remove(id);
    }

    /*
    public void importStudentsFromText() {
        List<EntityValidationError> errors = new LinkedList<>();
        EventDTO eventDTO = eventDetailModel.provideEventDTO();
        List<String> studentsId = new LinkedList<>();
        List<UserDTO> students = new LinkedList<>();
        if (!eventDetailModel.getStringIdImport().isEmpty()) {
            String[] studentsIDString = eventDetailModel.getStringIdImport().split(";");
            for (String studentString : studentsIDString) {
                if (!(studentString.isEmpty()) && !(studentString.equals(""))) {
                    try {
                        studentString = studentString.replaceFirst("[^0-9]", "");
                        studentsId.add(studentString);
                    } catch (NumberFormatException ex) {
                        errors.add(EntityValidationError.USER_INVALID);
                        PresentErrorMessages("eventstudentsform", errors, errorMessages);
                    }
                }
            }
            if (!studentsId.isEmpty()) {
                try {
                    for (String str : studentsId) {
                        UserDTO userDTO = userBean.find(str);
                        if (userDTO != null) {
                            if (userDTO.getGroup() == GROUP.Student) {
                                students.add(userDTO);
                            }
                        } else {
                            errors.add(EntityValidationError.USER_INVALID);
                        }
                    }
                } catch (EntityNotFoundException ex) {
                    errors.add(EntityValidationError.USER_INVALID);
                }
                if (!students.isEmpty()) {
                    if (eventDTO != null) {
                        try {
                            eventBean.addStudentsToEvent(students, eventDTO);
                        } catch (EntityValidationException ex) {
                            errors.add(EntityValidationError.EVENT_ALREADY_HAVE_STUDENT);
                            PresentErrorMessages("eventstudentsform", ex.getEntityValidationErrors(), errorMessages);
                        } catch (EntityNotFoundException ex) {
                            errors.add(EntityValidationError.USER_INVALID);
                        }
                    }
                }
                if (errors.isEmpty()) {
                    PresentSuccessMessage("eventstudentsform", "Estudantes adicionados com sucesso");
                } else {
                    PresentErrorMessage("eventstudentsform", "");
                }
            }
        } else {
            PresentErrorMessage("eventstudentsform", "Tem de introduzir o id de estudantes");
        }
    }

    public void importStudentsFromUC() {
        EventDTO eventDTO = eventDetailModel.provideEventDTO();
        Collection<AttendanceDTO> attendances;
        if (eventDetailModel.getStudentsUCDTO() != 0 && eventDetailModel.getStudentsUCDTO() != null) {
            try {
                UCDTO ucDTO = ucBean.find(eventDetailModel.getStudentsUCDTO());
                try {
                    attendances = attendanceBean.findFromEvent(eventDTO);
                    eventBean.addStudentsToEvent(attendances, ucDTO, eventDTO);
                } catch (EntityValidationException ex) {
                    PresentErrorMessage("eventstudentsform", "O Aluno já se encontra na lista de presenças do Evento");
                }
            } catch (EntityNotFoundException ex) {
                PresentErrorMessage("eventstudentsform", "UC não disponivel");
            }
        }
    }

    public void addStudentsToEvents() {
        EventDTO eventDTO = this.eventIndividualListModel.getEventDTO();
        try {
            attendanceBean.addStudentsToEvents(eventDTO.getUc(), eventDTO);
            PresentSuccessMessage("importstudentsform", "Estudantes adicionados com Sucesso");
        } catch (EntityValidationException ex) {
            PresentErrorMessage("importstudentsform", "A UC Seleccionada não têm Alunos");
        }
    }*/
    public void importStudentsFromUC() {
        System.out.println("AQUI");    
        try {
                
                EventDTO eventDTO = eventDetailModel.provideEventDTO();
                Collection<AttendanceDTO> attendances;
                UCDTO ucDTO = ucBean.find(eventDetailModel.getUc());
                try {
                    attendances = attendanceBean.findFromEvent(eventDTO);
                    eventBean.addStudentsToEvent(attendances, ucDTO, eventDTO);
                    System.out.println("PASSEI AQUI");
                } catch (EntityValidationException ex) {
                    PresentErrorMessage("eventstudentsform", "O Aluno já se encontra na lista de presenças do Evento");
                }
                
                
            } catch (EntityNotFoundException ex) {
                Logger.getLogger(AdminManager.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        
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

    public EventGroupIndexModel getEventGroupIndexModel() {
        return eventGroupIndexModel;
    }

    public EventGroupDetailModel getEventGroupDetailModel() {
        return eventGroupDetailModel;
    }

    
}
