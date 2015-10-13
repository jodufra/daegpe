/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dtos.UserDTO;
import dtos.UserRoleDTO;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import utilities.Security;
import static utilities.Text.GenerateSlug;

/**
 *
 * @author joeld
 */
@Stateless
public class UserBean extends AbstractBean<User, UserDTO> {

    @PersistenceContext(unitName = "GPE-ejbPU")
    private EntityManager em;

    @EJB
    private UserRoleBean userRoleBean;

    public UserBean() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public User getEntityFromDTO(UserDTO dto) {
        return getEntity(dto.getIdUser());
    }

    @Override
    protected UserDTO generateDTO(User user) {
        UserRoleDTO role = userRoleBean.find(user.getUserRole().getIdUserRole());
        return new UserDTO(user.getIdUser(), user.getInternalId(), user.getName(),
                user.getEmail(), user.getPassword(), user.getPhoto(), role);
    }

    @Override
    public List<String> save(UserDTO dto) {
        List<String> errors = new ArrayList<>(); 
        
        if (dto.getInternalId().isEmpty()) {
            errors.add("Id de Utilizador é Obrigatório.");
        }
        if (dto.getName().isEmpty()) {
            errors.add("Nome é Obrigatório.");
        }
        if (dto.getEmail().isEmpty()) {
            errors.add("Email é Obrigatório.");
        }
        if (dto.getUserRole() == null) {
            errors.add("Indique um role.");
        }

        if (errors.isEmpty()) {
            User user;
            if (dto.isNew()) {
                user = new User();
            } else {
                user = getEntity(dto.getIdUser());
            }
            user.setInternalId(dto.getInternalId());
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            if (dto.hasNewPassword()) {
                user.setPassword(dto.getNewPassword());
            }
            user.setPhoto(dto.getPhoto());
            user.setUserRole(userRoleBean.getEntityFromDTO(dto.getUserRole()));
            user.setSearch(GenerateSlug(user.getInternalId() + " " + user.getName() + " " + user.getEmail(), true, true));

            if (dto.isNew()) {
                super.create(user);
            } else {
                super.edit(user);
            } 
        }

        return errors;
    }

    public enum UserOrderBy {

        InternalIdAsc, InternalIdDesc, NameAsc, NameDesc, EmailAsc, EmailDesc
    }

    public UserDTO find(String username, String password) {
        password = Security.GetMD5Hash(password);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u FROM User u WHERE (u.internalId = \"").append(username).append("\" OR u.email = \"").append(username).append("\") ");
        sb.append("AND u.password = \"").append(password).append("\"");

        return generateDTO(em.createQuery(sb.toString(), User.class).getSingleResult());
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

        if (pageId != 0 && pageSize != 0) {
            int offset = (pageId - 1) * pageSize;
            return generateDTOList(em.createQuery(query).setFirstResult(offset).setMaxResults(pageSize).getResultList());
        }

        return generateDTOList(em.createQuery(query, User.class).getResultList());
    }

}
