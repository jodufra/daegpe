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
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.entities.Attendance;
import pt.ipleiria.dae.gpe.lib.entities.Event;
import pt.ipleiria.dae.gpe.lib.entities.Student;
import pt.ipleiria.dae.gpe.lib.entities.UC;
import pt.ipleiria.dae.gpe.lib.entities.GROUP;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.lib.beans.query.options.StudentAttendanceFindOptions;
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

        if (dto.getStudent() == null) {
            errors.add(EntityValidationError.ATTENDANCE_NULL_STUDENT);
        } else {
            if (dto.getStudent().isNew()) {
                errors.add(EntityValidationError.ATTENDANCE_STUDENT_IS_NEW);
            }
            if (dto.getStudent().getGroup() != GROUP.Student) {
                errors.add(EntityValidationError.ATTENDANCE_USER_NOT_STUDENT);
            }
        }

        if (dto.getEvent() == null) {
            errors.add(EntityValidationError.ATTENDANCE_NULL_EVENT);
        } else if (dto.getEvent().isNew()) {
            errors.add(EntityValidationError.ATTENDANCE_EVENT_IS_NEW);
        }
        if (dto.isNew() && find(dto.getEvent(), dto.getStudent()) != null) {
            errors.add(EntityValidationError.ATTENDANCE_CANT_BE_REPEATED);
        }

        if (errors.isEmpty()) {
            Attendance attendance;
            if (dto.isNew()) {
                attendance = new Attendance();
            } else {
                attendance = getEntity(dto.getIdAttendance());
            }
            System.out.println("TA");
            System.out.println("USER: " + em.find(Student.class, dto.getStudent().getIdUser()));
            System.out.println("TA2");
            attendance.setStudent(em.find(Student.class, dto.getStudent().getIdUser()));
            attendance.setEvent(em.find(Event.class, dto.getEvent().getIdEvent()));
            System.out.println("DTO PRESENT: " + dto.isPresent());
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

    public void addStudentsToEvents(UCDTO ucDTO, EventDTO eventDTO) throws EntityValidationException {
        List<EntityValidationError> errors = new ArrayList<>();
        if (ucDTO == null) {
            errors.add(EntityValidationError.UC_IS_REQUIRED);
            throw new EntityValidationException(errors);
        }
        if (eventDTO == null) {
            errors.add(EntityValidationError.EVENT_IS_REQUIRED);
            throw new EntityValidationException(errors);
        }
        UC uc = em.find(UC.class, ucDTO.getIdUC());
        if (uc == null) {
            errors.add(EntityValidationError.UC_IS_REQUIRED);
            throw new EntityValidationException(errors);
        }
        String query = "SELECT e FROM Event e WHERE e.internalId = '" + eventDTO.getInternalId() + "'";
        List<Event> events = em.createQuery(query, Event.class).getResultList();
        query = "SELECT s FROM UC u JOIN u.students s WHERE u.idUC = " + ucDTO.getIdUC();
        List<Student> students = em.createQuery(query, Student.class).getResultList();
        for (Event event : events) {
            for (Student student : students) {
                if (student.getUserGroup().getGroupName() != GROUP.Student) {
                    continue;
                }
                query = "SELECT COUNT(a) FROM Attendance a JOIN a.event e, a.student s WHERE e.idEvent = " + event.getIdEvent() + " AND s.idUser = " + student.getIdUser();
                long count = (long) em.createQuery(query).getSingleResult();
                System.out.println("Count: " + count);
                if (count == 0) {
                    Attendance attendance = new Attendance(student, event, false);
                    super.create(attendance);
                    System.out.println("after create");
                }
            }
        }
        if (!errors.isEmpty()) {
            throw new EntityValidationException(errors);
        }
    }
    

    public AttendanceDTO find(EventDTO event, UserDTO student) {
        List<EntityValidationError> errors = new ArrayList<>();
        if (student == null || student.isNew() || event == null || event.isNew()) {
            return null;
        }

        String query = "SELECT a FROM Attendance a JOIN a.event e, a.student s "
                + "WHERE e.idEvent = " + event.getIdEvent() + " AND s.idUser = " + student.getIdUser();
        try {
            AttendanceDTO att = generateDTO(em.createQuery(query, Attendance.class).getSingleResult());
            return generateDTO(em.createQuery(query, Attendance.class).getSingleResult());
        } catch (Exception e) {
            return null;
        }

    }

    public List<AttendanceDTO> findEventAttendances(EventDTO event) {
        String query = "SELECT a FROM Attendance a JOIN a.event e WHERE e.idEvent = " + event.getIdEvent();
        return generateDTOList(em.createQuery(query, Attendance.class).getResultList());
    }

    public List<AttendanceDTO> findFromEvent(EventDTO eventDTO) {
        Event event = em.find(Event.class, eventDTO.getIdEvent());
        return generateDTOList((List<Attendance>) event.getParticipants());
    }

    public List<AttendanceDTO> findStudentActiveAttendances(StudentAttendanceFindOptions options) {
        String query = "SELECT a FROM Attendance a JOIN a.event e, a.student s, e.manager m, e.uc u"
                + " WHERE e.attendanceActive = 1 AND s.idUser = " + options.user.getIdUser();

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
    
    
    public List<AttendanceDTO> findStudentAttendances(StudentAttendanceFindOptions options) {
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
