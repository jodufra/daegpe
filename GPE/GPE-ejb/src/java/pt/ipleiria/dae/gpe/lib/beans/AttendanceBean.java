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
import pt.ipleiria.dae.gpe.lib.entities.Attendance;
import pt.ipleiria.dae.gpe.lib.entities.Event;
import pt.ipleiria.dae.gpe.lib.entities.Student;
import pt.ipleiria.dae.gpe.lib.entities.User;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.lib.utilities.StudentAttendanceFindOptions;
import static pt.ipleiria.dae.gpe.lib.utilities.Text.GenerateSlug;

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
        String query = "SELECT a FROM Attendance a JOIN a.event e, a.student s, e.manager m, e.uc u"
                + " WHERE s.idUser = " + options.user.getIdUser();

        if (options.search != null && !options.search.isEmpty()) {
            for (String piece : options.search.split(" ")) {
                if (piece.equals(" ") || piece.isEmpty()) {
                    continue;
                }
                query += " AND e.search LIKE '%" + GenerateSlug(piece, true, true) + "%'";
            }
        }
        options.count = (long) em.createQuery(query.replace("SELECT a", "SELECT COUNT(a)")).getSingleResult();

        switch (options.orderBy) {
            case DateEndAsc:
                break;
            case DateEndDesc:
                break;
            case DateStartAsc:
                break;
            case DateStartDesc:
                break;
            case EventNameAsc:
                query += " ORDER BY e.name";
                break;
            case EventNameDesc:
                query += " ORDER BY e.name desc";
                break;
            case IsPresentAsc:
                query += " ORDER BY a.present";
                break;
            case IsPresentDesc:
                query += " ORDER BY a.present desc";
                break;
            case ManagerNameAsc:
                query += " ORDER BY m.name";
                break;
            case ManagerNameDesc:
                query += " ORDER BY m.name desc";
                break;
            case UCNameAsc:
                query += " ORDER BY u.name";
                break;
            case UCNameDesc:
                query += " ORDER BY u.name desc";
                break;
        }

        if (options.pageId > 0 && options.pageSize > 0) {
            int offset = (options.pageId - 1) * options.pageSize;
            return generateDTOList(em.createQuery(query).setFirstResult(offset).setMaxResults(options.pageSize).getResultList());
        }
        return generateDTOList(em.createQuery(query, Attendance.class).getResultList());
    }

}
