package pt.ipleiria.dae.gpe.lib.beans;

import java.util.ArrayList;
import java.util.Collection;
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
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.entities.Attendance;
import pt.ipleiria.dae.gpe.lib.entities.Event;
import pt.ipleiria.dae.gpe.lib.entities.Manager;
import pt.ipleiria.dae.gpe.lib.entities.Student;
import pt.ipleiria.dae.gpe.lib.entities.UC;
import pt.ipleiria.dae.gpe.lib.entities.User;
import pt.ipleiria.dae.gpe.lib.entities.UserType;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.lib.utilities.AdminEventFindOptions;
import pt.ipleiria.dae.gpe.lib.utilities.EventOrderBy;
import pt.ipleiria.dae.gpe.lib.utilities.EventType;
import pt.ipleiria.dae.gpe.lib.utilities.ManagerEventFindOptions;
import static pt.ipleiria.dae.gpe.lib.utilities.Text.GenerateSlug;

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

    public static EventType[] getEventTypes() {
        return EventType.values();
    }

    @Override
    public void save(EventDTO dto) throws EntityValidationException, EntityNotFoundException {
        List<EntityValidationError> errors = new ArrayList<>();

        if (dto.getInternalId().isEmpty()) {
            errors.add(EntityValidationError.USER_INTERNALID_REQUIRED);
        }
        if (dto.getName().isEmpty()) {
            errors.add(EntityValidationError.USER_NAME_REQUIRED);
        }
        if (dto.getManager() == null) {
            errors.add(EntityValidationError.USER_IS_REQUIRED);
        } else if (dto.getManager().getType() != UserType.Manager) {
            errors.add(EntityValidationError.USER_IS_NOT_MANAGER);
        }
        if (dto.getManager() == null) {
            errors.add(EntityValidationError.UC_IS_REQUIRED);
        }
        if (errors.isEmpty()) {
            Event event;
            if (dto.isNew()) {
                String[] regex = dto.getStartWeek().split(";");
                if (regex.length > 0) {
                    for (String string : regex) {
                        errors.clear();
                        String[] calendarString = string.split(":");
                        if (calendarString.length == 3) {
                            try {

                                Integer startWeek = Integer.parseInt(calendarString[1]);
                                Integer finalWeek = Integer.parseInt(calendarString[2]);
                                if (startWeek > finalWeek) {
                                    errors.add(EntityValidationError.EVENT_WEEK_INVALID);
                                } else {
                                    for (int index = startWeek; index <= finalWeek; index++) {
                                        event = new Event();
                                        String yearWeek = calendarString[0] + ":" + (index);
                                        event.setInternalId(dto.getInternalId());
                                        event.setEventType(dto.getEventType());
                                        event.setName(dto.getName());
                                        event.setEventDayWeek(dto.getEventDayWeek());
                                        event.setRoom(dto.getRoom());
                                        event.setStartHour(dto.getStartHour());
                                        event.setEndHour(dto.getEndHour());
                                        event.setStartWeek(yearWeek);
                                        event.setEndWeek(finalWeek);
                                        event.setSemester(dto.getSemester());
                                        event.setSearch(GenerateSlug(dto.getInternalId() + " " + dto.getName() + " " + dto.getManager().getName() + " " + dto.getUc().getName(), true, true));
                                        event.setManager(em.find(Manager.class, dto.getManager().getRelationalId()));
                                        event.setUc(em.find(UC.class, dto.getUc().getRelationalId()));
                                        event.setAttendanceActive(dto.isAttendanceActive());
                                        event.setAttendanceActivated(dto.isAttendanceActivated());
                                        event.setAttendancePassword(dto.getAttendancePassword());
                                        super.create(event);
                                    }
                                }
                            } catch (NumberFormatException ex) {
                                errors.add(EntityValidationError.EVENT_WEEK_INVALID);
                            }
                        }
                    }
                }

            } else {
                event = getEntity(dto.getIdEvent());
                event.setInternalId(dto.getInternalId());
                event.setEventType(dto.getEventType());
                event.setName(dto.getName());
                event.setEventDayWeek(dto.getEventDayWeek());
                event.setRoom(dto.getRoom());
                event.setStartHour(dto.getStartHour());
                event.setEndHour(dto.getEndHour());
                event.setStartWeek(dto.getStartWeek());
                event.setEndWeek(dto.getEndWeek());
                event.setSemester(dto.getSemester());
                event.setSearch(GenerateSlug(dto.getInternalId() + " " + dto.getName() + " " + dto.getManager().getName() + " " + dto.getUc().getName(), true, true));
                event.setManager(em.find(Manager.class, dto.getManager().getRelationalId()));
                event.setUc(em.find(UC.class, dto.getUc().getRelationalId()));
                event.setAttendanceActive(dto.isAttendanceActive());
                event.setAttendanceActivated(dto.isAttendanceActivated());
                event.setAttendancePassword(dto.getAttendancePassword());
                super.edit(event);
            }
        } else {
            throw new EntityValidationException(errors);
        }
    }

    public void addAttendanceEvent(AttendanceDTO dto) throws EntityValidationException {
        List<EntityValidationError> errors = new ArrayList<>();

        if (dto.getStudent() == null || dto.getStudent().isNew()) {
            errors.add(EntityValidationError.ATTENDANCE_NULL_STUDENT);
        }
        if (dto.getEvent() == null || dto.getEvent().isNew()) {
            errors.add(EntityValidationError.ATTENDANCE_NULL_EVENT);
        }
        if (dto.isNew()) {
            errors.add(EntityValidationError.ATTENDANCE_IS_NEW);
        }

        if (errors.isEmpty()) {
            Attendance a = em.find(Attendance.class, dto.getIdAttendance());
            Event e = em.find(Event.class, dto.getEvent().getIdEvent());
            if (!e.getParticipants().contains(a)) {
                e.addParticipant(a);
                super.edit(e);
            }
        } else {
            throw new EntityValidationException(errors);
        }
    }
/*
    public void addStudentsToEvents(UCDTO ucDTO, EventDTO eventDTO) throws EntityValidationException {
        List<EntityValidationError> errors = new ArrayList<>();
        if (ucDTO == null) {
            errors.add(EntityValidationError.UC_IS_REQUIRED);
            return;
        }
        System.out.println("1");
        List<EventDTO> evsDTO = findEventsOfEvent(0, 0, EventOrderBy.NameAsc, eventDTO.getInternalId());
        for (EventDTO evDTO : evsDTO) {
            System.out.println("2");
            Event event = em.find(Event.class, evDTO.getIdEvent());
            UC uc = em.find(UC.class, ucDTO.getIdUC());
            if (event != null && uc != null) {
                if (uc.getStudents().isEmpty()) {
                    errors.add(EntityValidationError.UC_WITHOUT_STUDENTS);
                }
                System.out.println("3");
                for (Student user : uc.getStudents()) {
                    System.out.println("4");
                    if (user.getType() == UserType.Student) {
                        String query = "SELECT COUNT(a) FROM Attendance a JOIN a.event e, a.student s WHERE e.idEvent = " + event.getIdEvent() + " AND s.idUser = " + user.getIdUser();
                        long count = (long) em.createQuery(query).getSingleResult();
                        if (count == 0) {
                            System.out.println("5");
                            Attendance attendeAttendance = new Attendance(user, event, false);
                            event.addParticipant(attendeAttendance);
                        } else {
                            System.out.println("6");
                            errors.add(EntityValidationError.ATTENDANCE_CANT_BE_REPEATED);
                        }
                    }
                }
                if (!errors.isEmpty()) {
                    throw new EntityValidationException(errors);
                }
            }
        }
    }*/


    public void addStudentsToEvent(UCDTO ucDTO, EventDTO eventDTO) throws EntityValidationException {
        List<EntityValidationError> errors = new ArrayList<>();
        Event event = em.find(Event.class, eventDTO.getIdEvent());

        UC uc = em.find(UC.class, ucDTO.getIdUC());
        if (event != null && uc != null) {
            for (User user : uc.getStudents()) {
                if (user.getType() == UserType.Student) {
                    Attendance attendeAttendance = new Attendance((Student) user, event, true);
                    if (!(event.getParticipants().contains(attendeAttendance))) {
                        event.addParticipant(attendeAttendance);
                    } else {
                        errors.add(EntityValidationError.ATTENDANCE_CANT_BE_REPEATED);
                    }
                }
            }

            if (!errors.isEmpty()) {
                throw new EntityValidationException(errors);
            }
        }

    }

    public void addStudentsToEvent(List<UserDTO> students, EventDTO eventDTO) throws EntityNotFoundException, EntityValidationException {
        List<EntityValidationError> errors = new ArrayList<>();
        Event event = em.find(Event.class, eventDTO.getIdEvent());
        if (event != null) {
            Collection<Attendance> attendances = event.getParticipants();
            for (UserDTO stu : students) {
                Student student = em.find(Student.class, stu.getIdUser());
                if (student != null) {
                    if (attendances.isEmpty()) {

                        Attendance attendeAttendance = new Attendance((Student) student, event, false);
                        event.addParticipant(attendeAttendance);

                    } else {
                        boolean find = false;
                        for (Attendance att : attendances) {
                            if (att.getStudent().getIdUser().equals(stu.getIdUser())) {
                                find = true;
                                errors.add(EntityValidationError.EVENT_ALREADY_HAVE_STUDENT);
                            }
                        }

                        if (!find) {
                            Attendance attendeAttendance = new Attendance((Student) student, event, false);
                            event.addParticipant(attendeAttendance);
                        }

                    }
                }

            }
        } else {
            errors.add(EntityValidationError.EVENT_NOT_FOUND);
        }

        if (!errors.isEmpty()) {
            throw new EntityValidationException(errors);
        }
    }

    public void addStudentsToEvent(Collection<AttendanceDTO> attendances, UCDTO ucDTO, EventDTO eventDTO) throws EntityValidationException {
        List<EntityValidationError> errors = new ArrayList<>();
        Event event = em.find(Event.class, eventDTO.getIdEvent());

        UC uc = em.find(UC.class, ucDTO.getIdUC());
        if (event != null && uc != null) {
            for (User user : uc.getStudents()) {
                if (user.getType() == UserType.Student) {
                    boolean find = false;
                    for (AttendanceDTO attendace : attendances) {
                        if (attendace.getEvent().getIdEvent().equals(event.getIdEvent()) && attendace.getStudent().getIdUser().equals(user.getIdUser())) {
                            find = true;
                        }
                    }
                    if (find == true) {
                        errors.add(EntityValidationError.ATTENDANCE_CANT_BE_REPEATED);
                    } else {
                        Attendance attendeAttendance = new Attendance((Student) user, event, true);
                        event.addParticipant(attendeAttendance);
                    }
                }
            }

            if (!errors.isEmpty()) {
                throw new EntityValidationException(errors);
            }
        }
    }

    public EventDTO find(String internalId) {
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
        return find(new AdminEventFindOptions(pageId, pageSize, orderBy, null));
    }

    public List<EventDTO> find(AdminEventFindOptions options) {
        String query = "SELECT e FROM Event e JOIN e.uc u, e.manager m";

        if (options.search != null && !options.search.isEmpty()) {
            String[] pieces = options.search.split(" ");
            boolean first = true;
            for (int i = 0; i < pieces.length; i++) {
                if (pieces[i].equals(" ") || pieces[i].isEmpty()) {
                    continue;
                }
                pieces[i] = GenerateSlug(pieces[i], true, true);
                if (first) {
                    query += " WHERE ";
                    first = false;
                } else {
                    query += " AND ";
                }
                query += "e.search LIKE '%" + pieces[i] + "%'";
            }
        }

        options.count = (long) em.createQuery(query.replace("SELECT e", "SELECT COUNT(e)")).getSingleResult();

        switch (options.orderBy) {
            case InternalIdAsc:
                query += " ORDER BY e.internalId";
                break;
            case InternalIdDesc:
                query += " ORDER BY e.internalId desc";
                break;
            case TypeAsc:
                query += " ORDER BY e.eventType";
                break;
            case TypeDesc:
                query += " ORDER BY e.eventType desc";
                break;
            case NameAsc:
                query += " ORDER BY e.name";
                break;
            case NameDesc:
                query += " ORDER BY e.name desc";
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

        if (options.pageId != 0 && options.pageSize != 0) {
            int offset = (options.pageId - 1) * options.pageSize;
            return generateDTOList(em.createQuery(query).setFirstResult(offset).setMaxResults(options.pageSize).getResultList());
        }
        return generateDTOList(em.createQuery(query, Event.class).getResultList());
    }

    public List<EventDTO> findUnique(AdminEventFindOptions options) {
        List<EventDTO> allEvents = find(options);
        ArrayList<EventDTO> uniqueEvents = new ArrayList<>();
        boolean find;
        for (EventDTO eventDTO : allEvents) {
            if (!uniqueEvents.isEmpty()) {
                find = false;
                for (EventDTO ev : uniqueEvents) {
                    if (Objects.equals(ev.getInternalId(), eventDTO.getInternalId())) {
                        find = true;
                    }
                }
                if (find == false) {
                    uniqueEvents.add(eventDTO);
                }
            } else {
                uniqueEvents.add(eventDTO);
            }
        }

        return uniqueEvents;
    }

    public List<EventDTO> findEventsOfEvent(int pageId, int pageSize, EventOrderBy orderBy, String eventInternalId) {
        String query = "SELECT e FROM Event e Where e.internalId= " + "'" + eventInternalId + "'";

        switch (orderBy) {
            case InternalIdAsc:
                query += " ORDER BY e.internalId";
                break;
            case InternalIdDesc:
                query += " ORDER BY e.internalId desc";
                break;
            case NameAsc:
                query += " ORDER BY e.name";
                break;
            case NameDesc:
                query += " ORDER BY e.name desc";
                break;
        }

        if (pageId != 0 && pageSize != 0) {
            int offset = (pageId - 1) * pageSize;
            return generateDTOList(em.createQuery(query).setFirstResult(offset).setMaxResults(pageSize).getResultList());
        }
        return generateDTOList(em.createQuery(query, Event.class).getResultList());
    }

    public List<EventDTO> findEventsByInternalId(int pageId, int pageSize, EventOrderBy orderBy, String eventInternalId) {
        String query = "SELECT e FROM Event e WHERE e.internalId = '" + eventInternalId + "'";

        switch (orderBy) {
            case InternalIdAsc:
                query += " ORDER BY e.internalId";
                break;
            case InternalIdDesc:
                query += " ORDER BY e.internalId desc";
                break;
            case TypeAsc:
                query += " ORDER BY e.eventType";
                break;
            case TypeDesc:
                query += " ORDER BY e.eventType desc";
                break;
            case NameAsc:
                query += " ORDER BY e.name";
                break;
            case NameDesc:
                query += " ORDER BY e.name desc";
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

        if (pageId != 0 && pageSize != 0) {
            int offset = (pageId - 1) * pageSize;
            return generateDTOList(em.createQuery(query).setFirstResult(offset).setMaxResults(pageSize).getResultList());
        }
        return generateDTOList(em.createQuery(query, Event.class).getResultList());
    }

    public List<EventDTO> findEventsManager(ManagerEventFindOptions options) {
        String query = "SELECT e FROM Event e JOIN e.manager m, e.uc u WHERE m.idUser = " + options.user.getIdUser();

        if (options.search != null && !options.search.isEmpty()) {
            for (String piece : options.search.split(" ")) {
                if (piece.equals(" ") || piece.isEmpty()) {
                    continue;
                }
                query += " AND e.search LIKE '%" + GenerateSlug(piece, true, true) + "%'";
            }
        }
        options.count = (long) em.createQuery(query.replace("SELECT e", "SELECT COUNT(e)")).getSingleResult();

        switch (options.orderBy) {
            case InternalIdAsc:
                query += " ORDER BY e.internalId";
                break;
            case InternalIdDesc:
                query += " ORDER BY e.internalId desc";
                break;
            case TypeAsc:
                query += " ORDER BY e.eventType";
                break;
            case TypeDesc:
                query += " ORDER BY e.eventType desc";
                break;
            case NameAsc:
                query += " ORDER BY e.name";
                break;
            case NameDesc:
                query += " ORDER BY e.name desc";
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

        List<EventDTO> allEvents;
        if (options.pageId != 0 && options.pageSize != 0) {
            int offset = (options.pageId - 1) * options.pageSize;
            allEvents = generateDTOList(em.createQuery(query).setFirstResult(offset).setMaxResults(options.pageSize).getResultList());
        } else {
            allEvents = generateDTOList(em.createQuery(query, Event.class).getResultList());
        }

        ArrayList<EventDTO> uniqueEvents = new ArrayList<>();
        boolean find;
        for (EventDTO eventDTO : allEvents) {
            if (!uniqueEvents.isEmpty()) {
                find = false;
                for (EventDTO ev : uniqueEvents) {
                    if (Objects.equals(ev.getInternalId(), eventDTO.getInternalId())) {
                        find = true;
                    }
                }
                if (find == false) {
                    uniqueEvents.add(eventDTO);
                }
            } else {
                uniqueEvents.add(eventDTO);
            }
        }

        return uniqueEvents;
    }

    public Collection<Attendance> findStudentsAttendance(EventDTO eventDTO) {
        Event event = em.find(Event.class, eventDTO.getIdEvent());
        if (event != null) {
            return event.getParticipants();
        }
        return null;
    }

    public void remove(Integer idEvent) {
        Event event = em.find(Event.class, idEvent);
        if (event == null) {
            return;
        }
        em.remove(event);
    }
}
