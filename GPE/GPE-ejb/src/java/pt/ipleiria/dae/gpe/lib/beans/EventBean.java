package pt.ipleiria.dae.gpe.lib.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pt.ipleiria.dae.gpe.lib.core.AbstractBean;
import pt.ipleiria.dae.gpe.lib.core.EntityValidationError;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.entities.Attendance;
import pt.ipleiria.dae.gpe.lib.entities.Event;
import pt.ipleiria.dae.gpe.lib.entities.Manager;
import pt.ipleiria.dae.gpe.lib.entities.Student;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.lib.utilities.EventOrderBy;
import pt.ipleiria.dae.gpe.lib.utilities.ManagerEventFindOptions;

@Stateless
public class EventBean extends AbstractBean<Event, EventDTO> {

    @PersistenceContext(unitName = "GPE-ejbPU")
    private EntityManager em;

    public EventBean() {
        super(Event.class, EventDTO.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void save(EventDTO dto) throws EntityValidationException, EntityNotFoundException {
        List<EntityValidationError> errors = new ArrayList<>();

        if (dto.getInternalId().isEmpty()) {
            errors.add(EntityValidationError.USER_INTERNALID_REQUIRED);
        } else {

            EventDTO eventWithSameId = find(dto.getInternalId());
            if (eventWithSameId != null && !Objects.equals(eventWithSameId.getIdEvent(), dto.getIdEvent())) {
                errors.add(EntityValidationError.USER_INTERNALID_NOT_UNIQUE);
            }

        }
        if (dto.getName().isEmpty()) {
            errors.add(EntityValidationError.USER_NAME_REQUIRED);
        }

        if (errors.isEmpty()) {
            Event event;
            if (dto.isNew()) {
                event = new Event();
            } else {
                event = getEntity(dto.getIdEvent());
            }
            event.setInternalId(dto.getInternalId());
            event.setName(dto.getName());
            event.setDateStart(dto.getDateStart());
            event.setManager(null);
            event.setMinutes(dto.getMinutes());
            event.setName(dto.getName());
            event.setUc(null);
            if (dto.isNew()) {
                super.create(event);
            } else {
                super.edit(event);
            }
        } else {
            throw new EntityValidationException(errors);
        }

    }

    public EventDTO find(String internalId) {
        System.out.println("INTERNAL: " + internalId);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u FROM Event u WHERE u.internalId = \"").append(internalId).append("\"");
        TypedQuery<Event> query = em.createQuery(sb.toString(), Event.class);
        List<Event> events = query.getResultList();
        if (events.isEmpty()) {
            return null;
        }
        return generateDTO(events.get(0));
    }

    public List<EventDTO> find(int pageId, int pageSize, EventOrderBy orderBy) {
        String query = "SELECT e FROM Event e ORDER BY e.name";

        switch (orderBy) {
            case InternalIdAsc:
                //query += " ORDER BY u.internalId";
                break;
            case InternalIdDesc:
                //query += " ORDER BY u.internalId desc";
                break;
            case NameAsc:
                //query += " ORDER BY u.name";
                break;
            case NameDesc:
                //query += " ORDER BY u.name desc";
                break;
        }

        if (pageId != 0 && pageSize != 0) {
            int offset = (pageId - 1) * pageSize;
            return generateDTOList(em.createQuery(query).setFirstResult(offset).setMaxResults(pageSize).getResultList());
        }
        return generateDTOList(em.createQuery(query, Event.class).getResultList());
    }

    public List<EventDTO> findFromManager(ManagerEventFindOptions options) {
        Manager manager = em.find(Manager.class, options.user.getRelationalId());
        return generateDTOList((List<Event>) manager.getEvents());
    }

    public void remove(Integer idEvent) {
        Event event = em.find(Event.class, idEvent);
        if (event == null) {
            return;
        }
        em.remove(event);
    }

    public void addEventAttendance(AttendanceDTO dto) throws EntityValidationException {
        List<EntityValidationError> errors = new ArrayList<>();

        if (dto.getStudent() == null || dto.getStudent().isNew()) {
            errors.add(EntityValidationError.ATTENDANCE_INVALID_STUDENT);
        }
        if (dto.getEvent() == null || dto.getEvent().isNew()) {
            errors.add(EntityValidationError.ATTENDANCE_INVALID_EVENT);
        }
        if (dto.isNew()) {
            errors.add(EntityValidationError.ATTENDANCE_IS_NEW);
        }

        if (errors.isEmpty()) {
            Attendance a = em.find(Attendance.class, dto.getIdAttendance());
            Event e = em.find(Event.class, dto.getEvent().getIdEvent());
            e.addParticipant(a);
            super.edit(e);
        } else {
            throw new EntityValidationException(errors);
        }
    }
}
