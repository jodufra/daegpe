package pt.ipleiria.dae.gpe.web.managers;

import java.io.IOException;
import java.util.EnumMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import pt.ipleiria.dae.gpe.lib.beans.AttendanceBean;
import pt.ipleiria.dae.gpe.lib.beans.UCBean;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.core.EntityValidationError;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.web.app.AbstractManager;
import pt.ipleiria.dae.gpe.web.models.student.AttendanceIndexModel;
import pt.ipleiria.dae.gpe.web.models.student.UCIndexModel;
import pt.ipleiria.dae.gpe.web.models.student.UserDetailModel;

/**
 *
 * @author joeld
 */
@ManagedBean
@SessionScoped
public class StudentManager extends AbstractManager {

    @EJB
    private UCBean ucBean;
    @EJB
    private UserBean userBean;
    @EJB
    private AttendanceBean attendanceBean;

    private UCIndexModel ucIndexModel;
    private UserDetailModel userDetailModel;
    private AttendanceIndexModel attendanceIndexModel;

    private final EnumMap<EntityValidationError, String> errorMessages;

    public StudentManager() {
        errorMessages = new EnumMap<>(EntityValidationError.class);
        errorMessages.put(EntityValidationError.USER_NAME_REQUIRED, "Nome é obrigatório.");
        errorMessages.put(EntityValidationError.USER_EMAIL_REQUIRED, "Email é obrigatório.");
        errorMessages.put(EntityValidationError.USER_EMAIL_PATTERN, "Email inválido.");
    }

    @PostConstruct
    public void constructModels() {
        ucIndexModel = new UCIndexModel(ucBean);
        userDetailModel = new UserDetailModel();
        attendanceIndexModel = new AttendanceIndexModel(attendanceBean);
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
    
    public void updateAttendanceState(ActionEvent e)  throws IOException {
        AttendanceDTO attedance = (AttendanceDTO) ((UIParameter) e.getComponent().findComponent("attendance")).getValue();
        attedance.setPresent(true);
        try {
            attendanceBean.save(attedance);
            PresentSuccessMessage("eventattendancesform", "Participação registada com sucesso");
        } catch (EntityValidationException eve) {
            PresentErrorMessages("eventattendancesform", eve.getEntityValidationErrors(), errorMessages);
        } catch (EntityNotFoundException enf) {
            PresentErrorMessage("eventattendancesform", "Verifique se o evento ainda existe ou se os registos de presenças estão abertos.");
        }
    }

    public void updateAttendanceState(ActionEvent e)  throws IOException {
        AttendanceDTO attedance = (AttendanceDTO) ((UIParameter) e.getComponent().findComponent("attendance")).getValue();
        attedance.setPresent(true);
        try {
            attendanceBean.save(attedance);
            PresentSuccessMessage("eventattendancesform", "Participação registada com sucesso");
        } catch (EntityValidationException eve) {
            PresentErrorMessages("eventattendancesform", eve.getEntityValidationErrors(), errorMessages);
        } catch (EntityNotFoundException enf) {
            PresentErrorMessage("eventattendancesform", "Verifique se o evento ainda existe ou se os registos de presenças estão abertos.");
        }
    }

    ////////////////////////////////////////////
    ///////////////// Models ///////////////////
    public UCIndexModel getUcIndexModel() {
        return ucIndexModel;
    }

    public UserDetailModel getUserDetailModel() {
        return userDetailModel;
    }

    public AttendanceIndexModel getAttendanceIndexModel() {
        return attendanceIndexModel;
    }

}
