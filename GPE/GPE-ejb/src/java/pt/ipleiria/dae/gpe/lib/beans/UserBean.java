/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.beans;

import pt.ipleiria.dae.gpe.lib.core.AbstractBean;
import pt.ipleiria.dae.gpe.lib.core.EntityValidationError;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.entities.Administrator;
import pt.ipleiria.dae.gpe.lib.entities.Attendance;
import pt.ipleiria.dae.gpe.lib.entities.Manager;
import pt.ipleiria.dae.gpe.lib.entities.Student;
import pt.ipleiria.dae.gpe.lib.entities.UC;
import pt.ipleiria.dae.gpe.lib.entities.GROUP;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import static pt.ipleiria.dae.gpe.lib.utilities.Text.GenerateSlug;
import pt.ipleiria.dae.gpe.lib.beans.query.options.AdminUserFindOptions;
import pt.ipleiria.dae.gpe.lib.beans.query.options.ManagerUserFindOptions;

/**
 *
 * @author joeld
 */
@Stateless
public class UserBean extends AbstractBean<User, UserDTO> {

    @PersistenceContext(unitName = "GPE-ejbPU")
    private EntityManager em;

    public UserBean() {
        super(User.class, UserDTO.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void save(UserDTO dto) throws EntityValidationException, EntityNotFoundException {
        List<EntityValidationError> errors = new ArrayList<>();

        UC uc = null;
        if (dto.getInternalId().isEmpty()) {
            errors.add(EntityValidationError.USER_INTERNALID_REQUIRED);
        } else {
            UserDTO userWithSameInternalId;
            try {
                userWithSameInternalId = find(dto.getInternalId());
                if (userWithSameInternalId != null && !Objects.equals(userWithSameInternalId.getIdUser(), dto.getIdUser())) {
                    errors.add(EntityValidationError.USER_INTERNALID_NOT_UNIQUE);
                }
            } catch (EntityNotFoundException ex) {
                // Do nothing. This exception means that there are no users with repeated InternalId.
            }
        }
        if (dto.getGroup() == null) {
            errors.add(EntityValidationError.USER_USERGROUP_INVALID);
        }
        if (dto.getName().isEmpty()) {
            errors.add(EntityValidationError.USER_NAME_REQUIRED);
        }
        if (dto.getEmail().isEmpty()) {
            errors.add(EntityValidationError.USER_EMAIL_REQUIRED);
        }

        if (errors.isEmpty()) {
            User user;
            if (dto.isNew()) {
                if (UserDTO.IsAdministrator(dto)) {
                    user = new Administrator();
                } else if (UserDTO.IsManager(dto)) {
                    user = new Manager();
                } else {
                    user = new Student();
                }
            } else {
                user = getEntityFromDTO(dto);
            }
            user.setInternalId(dto.getInternalId());
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            if (dto.hasNewPassword()) {
                user.setPassword(dto.getNewPassword());
            }
            user.setSearch(GenerateSlug(user.getInternalId() + " " + user.getName() + " " + user.getEmail() + " " + user.getUserGroup().getGroupName().toString(), true, true));
            if (dto.isNew()) {
                super.create(user);
                em.persist(user.getUserGroup());
            } else {
                super.edit(user);
            }
        } else {
            throw new EntityValidationException(errors);
        }
    }

    public List<UserDTO> find(AdminUserFindOptions options) {
        String query = "SELECT u FROM User u";

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
                query += "u.search LIKE '%" + pieces[i] + "%'";
            }
        }

        options.count = (long) em.createQuery(query.replace("SELECT u", "SELECT COUNT(u)")).getSingleResult();

        switch (options.orderBy) {
            case EmailAsc:
                query += " ORDER BY u.email";
                break;
            case EmailDesc:
                query += " ORDER BY u.email desc";
                break;
            case InternalIdAsc:
                query += " ORDER BY u.internalId";
                break;
            case InternalIdDesc:
                query += " ORDER BY u.internalId desc";
                break;
            case NameAsc:
                query += " ORDER BY u.name";
                break;
            case NameDesc:
                query += " ORDER BY u.name desc";
                break;
            case TypeAsc:
                query += " ORDER BY u.type";
                break;
            case TypeDesc:
                query += " ORDER BY u.type desc";
                break;
        }

        if (options.pageId > 0 && options.pageSize > 0) {
            int offset = (options.pageId - 1) * options.pageSize;
            return generateDTOList(em.createQuery(query).setFirstResult(offset).setMaxResults(options.pageSize).getResultList());
        }

        return generateDTOList(em.createQuery(query, User.class).getResultList());
    }

    public List<UserDTO> find(ManagerUserFindOptions options) {
        String query = "SELECT u FROM User u";

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
                query += "u.search LIKE '%" + pieces[i] + "%'";
            }
        }

        switch (options.orderBy) {
            case EmailAsc:
                query += " ORDER BY u.email";
                break;
            case EmailDesc:
                query += " ORDER BY u.email desc";
                break;
            case InternalIdAsc:
                query += " ORDER BY u.internalId";
                break;
            case InternalIdDesc:
                query += " ORDER BY u.internalId desc";
                break;
            case NameAsc:
                query += " ORDER BY u.name";
                break;
            case NameDesc:
                query += " ORDER BY u.name desc";
                break;
            case TypeAsc:
                query += " ORDER BY u.type";
                break;
            case TypeDesc:
                query += " ORDER BY u.type desc";
                break;
        }

        if (options.pageId > 0 && options.pageSize > 0) {
            int offset = (options.pageId - 1) * options.pageSize;
            return generateDTOList(em.createQuery(query).setFirstResult(offset).setMaxResults(options.pageSize).getResultList());
        }

        return generateDTOList(em.createQuery(query, User.class).getResultList());
    }

    public void addUCStudent(UserDTO userDTO, UCDTO UCdto) throws EntityNotFoundException, EntityValidationException {
        List<EntityValidationError> errors = new ArrayList<>();
        if (userDTO.isNew()) {
            errors.add(EntityValidationError.UC_IS_NEW);
        }
        if (userDTO.getGroup() != GROUP.Student) {
            errors.add(EntityValidationError.USER_IS_NOT_STUDENT);
        }
        if (UCdto.isNew()) {
            errors.add(EntityValidationError.USER_IS_NEW);
        }

        if (errors.isEmpty()) {

        }

        if (errors.isEmpty()) {
            Student student = em.find(Student.class, userDTO.getIdUser());
            UC uc = em.find(UC.class, UCdto.getIdUC());
            if (!student.getUcs().contains(uc)) {
                student.addUc(uc);
                edit(student);
            }
        }
        if (!errors.isEmpty()) {
            throw new EntityValidationException(errors);
        }
    }

    public void addAttendanceStudent(AttendanceDTO dto) throws EntityValidationException {
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
            Attendance attendance = em.find(Attendance.class, dto.getIdAttendance());
            Student student = em.find(Student.class, dto.getStudent().getIdUser());
            if (!student.getAttendances().contains(attendance)) {
                student.addAttendance(attendance);
                super.edit(student);
            }
        } else {
            throw new EntityValidationException(errors);
        }
    }

    public UserDTO find(String internalId) throws EntityNotFoundException {
        List<User> users = em.createNamedQuery("User.findByInternalid").setParameter("internalId", internalId).getResultList();
        if (users.isEmpty() || users.size() > 1) {
            throw new EntityNotFoundException();
        }
        return generateDTO(users.get(0));
    }

    public UserDTO findByUsername(String username) throws EntityNotFoundException {
        List<User> users = em.createNamedQuery("User.findByUsername").setParameter("email", username).setParameter("internalId", username).getResultList();
        if (users.isEmpty() || users.size() > 1) {
            throw new EntityNotFoundException();
        }
        return generateDTO(users.get(0));
    }

    public UserDTO findFirstManager() {
        String q = "SELECT u.* FROM USERS u JOIN USERS_GROUPS g ON u.INTERNALID = g.USERNAME WHERE g.GROUPNAME = '" + GROUP.Manager + "'";
        List<User> list = em.createNativeQuery(q, User.class).setFirstResult(0).setMaxResults(1).getResultList();
        return list.isEmpty() ? null : generateDTO(list.get(0));
    }

    public List<UserDTO> findAllManagers() {
        String q = "SELECT u.* FROM USERS u JOIN USERS_GROUPS g ON u.INTERNALID = g.USERNAME WHERE g.GROUPNAME = '" + GROUP.Manager + "'";
        return generateDTOList(em.createNativeQuery(q, User.class).getResultList());
    }

    public List<UserDTO> findFromUC(AdminUserFindOptions options) {
        if (options.ucdto == null || options.ucdto.isNew()) {
            return new ArrayList<>();
        }
        UC uc = em.find(UC.class, options.ucdto.getIdUC());
        List<User> users = new ArrayList<>();
        for (Student s : uc.getStudents()) {
            users.add(s);
        }
        return generateDTOList(users);
    }

}
