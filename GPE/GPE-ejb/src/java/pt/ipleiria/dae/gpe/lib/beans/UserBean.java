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
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.StudentDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.entities.Administrator;
import pt.ipleiria.dae.gpe.lib.entities.Attendance;
import pt.ipleiria.dae.gpe.lib.entities.Manager;
import pt.ipleiria.dae.gpe.lib.entities.Student;
import pt.ipleiria.dae.gpe.lib.entities.UC;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.utilities.Security;
import static pt.ipleiria.dae.gpe.lib.utilities.Text.GenerateSlug;
import pt.ipleiria.dae.gpe.lib.utilities.AdminUserFindOptions;

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
        if (dto.getType() == null) {
            errors.add(EntityValidationError.USER_USERTYPE_INVALID);
        }
        if (dto.getName().isEmpty()) {
            errors.add(EntityValidationError.USER_NAME_REQUIRED);
        }
        if (dto.getEmail().isEmpty()) {
            errors.add(EntityValidationError.USER_EMAIL_REQUIRED);
        }

        if (errors.isEmpty()) {
            User user;
            String userType = "";
            if (dto.isNew()) {
                if (UserDTO.IsAdministrator(dto)) {
                    user = new Administrator();
                    userType = "Administrator";
                } else if (UserDTO.IsManager(dto)) {
                    user = new Manager();
                    userType = "Manager";
                } else if (UserDTO.IsStudent(dto)) {
                    user = new Student();
                    userType = "Student";
                } else {
                    user = new User();
                }
            } else {
                user = getEntityFromDTO(dto);
            }
            user.setType(dto.getType());
            user.setInternalId(dto.getInternalId());
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            if (dto.hasNewPassword()) {
                user.setPassword(dto.getNewPassword());
            }
            user.setSearch(GenerateSlug(" " + user.getInternalId() + " " + user.getName() + " " + user.getEmail() + " " + userType + " ", true, true));

            if (dto.isNew()) {
                super.create(user);
            } else {
                super.edit(user);
            }
        } else {
            throw new EntityValidationException(errors);
        }
    }
    
    
     public void addUcStudent(UserDTO userDTO, UCDTO UCdto) throws EntityNotFoundException, EntityValidationException {
        
           List<EntityValidationError> errors = new ArrayList<>();
        if (userDTO.getInternalId() == null) {
            errors.add(EntityValidationError.UC_INTERNALID_REQUIRED);
        }
        if (UCdto.getInternalId() == null ) {
            errors.add(EntityValidationError.USER_INTERNALID_REQUIRED);
        }


        if (errors.isEmpty()) {
         Student student = (Student) getEntityFromDTO(userDTO);
        UC uc = em.find(UC.class, UCdto.getIdUC());
        student.addUc(uc);
        edit(student);
        }else {
            throw new EntityValidationException(errors);
        }
    }
    
    
    

    public UserDTO find(String internalId) throws EntityNotFoundException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u FROM User u WHERE u.internalId = \"").append(internalId).append("\"");
        TypedQuery<User> query = em.createQuery(sb.toString(), User.class);
        List<User> users = query.getResultList();
        if (users.isEmpty()) {
            throw new EntityNotFoundException();
        }
        User user = users.get(0);
        if (User.IsAdministrator(user)) {
            return generateDTO((Administrator) user);
        }
        if (User.IsManager(user)) {
            return generateDTO((Manager) user);
        }
        if (User.IsStudent(user)) {
            return generateDTO((Student) user);
        }
        return generateDTO(user);
    }

    public UserDTO find(String username, String password) throws EntityNotFoundException {
        password = Security.GenerateMD5Hash(password);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u FROM User u WHERE (u.internalId = \"").append(username).append("\" OR u.email = \"").append(username).append("\") ");
        sb.append("AND u.password = \"").append(password).append("\"");
        TypedQuery<User> query = em.createQuery(sb.toString(), User.class);
        List<User> users = query.getResultList();
        if (users.isEmpty()) {
            throw new EntityNotFoundException();
        }
        User user = users.get(0);
        if (User.IsAdministrator(user)) {
            return generateDTO((Administrator) user);
        }
        if (User.IsManager(user)) {
            return generateDTO((Manager) user);
        }
        if (User.IsStudent(user)) {
            return generateDTO((Student) user);
        }
        return generateDTO(user);
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

    public void addStudentAttendance(AttendanceDTO dto) throws EntityValidationException {
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
            Student s = em.find(Student.class, dto.getStudent().getIdUser());
            s.addAttendance(a);
            super.edit(s);
        } else {
            throw new EntityValidationException(errors);
        }
    }

}
