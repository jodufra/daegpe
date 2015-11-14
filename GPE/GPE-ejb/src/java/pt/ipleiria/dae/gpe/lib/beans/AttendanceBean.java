/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.beans;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pt.ipleiria.dae.gpe.lib.core.AbstractBean;
import pt.ipleiria.dae.gpe.lib.core.EntityValidationError;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.entities.Attendance;
import pt.ipleiria.dae.gpe.lib.entities.Event;
import pt.ipleiria.dae.gpe.lib.entities.Student;
import pt.ipleiria.dae.gpe.lib.entities.User;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.lib.utilities.StudentAttendanceFindOptions;

/**
 *
 * @author joeld
 */
@Stateless
public class AttendanceBean extends AbstractBean<Attendance, AttendanceDTO> {

    @PersistenceContext(unitName = "GPE-ejbPU")
    private EntityManager em;

    public AttendanceBean() {
        super(Attendance.class, AttendanceDTO.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void save(AttendanceDTO dto) throws EntityValidationException, EntityNotFoundException {
        List<EntityValidationError> errors = new ArrayList<>();

        if (dto.getStudent() == null || dto.getStudent().isNew()) {
            errors.add(EntityValidationError.ATTENDANCE_INVALID_STUDENT);
        }

        if (dto.getEvent() == null || dto.getEvent().isNew()) {
            errors.add(EntityValidationError.ATTENDANCE_INVALID_EVENT);
        }

        if (errors.isEmpty()) {
            Attendance attendance;
            if (dto.isNew()) {
                attendance = new Attendance();
            } else {
                attendance = getEntity(dto.getIdAttendance());
            }

            attendance.setStudent(em.find(Student.class, dto.getStudent().getIdUser()));
            attendance.setEvent(em.find(Event.class, dto.getEvent().getIdEvent()));
            attendance.setPresent(dto.isPresent());
            if (dto.isNew()) {
                super.create(attendance);
            } else {
                super.edit(attendance);
            }
        } else {
            throw new EntityValidationException(errors);
        }

    }

    public List<AttendanceDTO> findFromStudent(StudentAttendanceFindOptions options) {
        Student student = em.find(Student.class, options.user.getIdUser());
        return generateDTOList((List<Attendance>) student.getAttendances());
    }
    
    
    public List<AttendanceDTO> findFromEvent(EventDTO eventDTO) {
        Event event = em.find(Event.class, eventDTO.getIdEvent());
        return generateDTOList((List<Attendance>) event.getParticipants());
    }
}
