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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pt.ipleiria.dae.gpe.lib.entities.Administrator;
import pt.ipleiria.dae.gpe.lib.entities.Manager;
import pt.ipleiria.dae.gpe.lib.entities.Student;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.utilities.Security;
import static pt.ipleiria.dae.gpe.lib.utilities.Text.GenerateSlug;

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
                // Do nothing. This execption means that there are no users with repeated InternalId.
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
            if (dto.isNew()) {
                if (UserDTO.IsAdministrator(dto)) {
                    user = new Administrator();
                } else if (UserDTO.IsManager(dto)) {
                    user = new Manager();
                } else if (UserDTO.IsStudent(dto)) {
                    user = new Student();
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
            user.setSearch(GenerateSlug(" " + user.getInternalId() + " " + user.getName() + " " + user.getEmail() + " ", true, true));

            if (dto.isNew()) {
                super.create(user);
            } else {
                super.edit(user);
            }
        } else {
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
        return generateDTO(users.get(0));
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
        return generateDTO(users.get(0));
    }

    public enum UserOrderBy {

        InternalIdAsc, InternalIdDesc, NameAsc, NameDesc, EmailAsc, EmailDesc
    }

    public List<UserDTO> find(int pageId, int pageSize, UserOrderBy orderBy) {
        String query = "SELECT u FROM User u";

        switch (orderBy) {
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
        }

        if (pageId > 0 && pageSize > 0) {
            int offset = (pageId - 1) * pageSize;
            return generateDTOList(em.createQuery(query).setFirstResult(offset).setMaxResults(pageSize).getResultList());
        }

        return generateDTOList(em.createQuery(query, User.class).getResultList());
    }

}
