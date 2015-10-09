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
                user.getEmail(), user.getPassword(), user.getPhoto(), 
                user.getDateBirth(),  role);
    }

    @Override
    public List<String> save(UserDTO dto) {
        List<String> errors = new ArrayList<>();

        //TODO: check for errors
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
            user.setDateBirth(dto.getDateBirth());
            user.setPhoto(dto.getPhoto());
            user.setUserRole(userRoleBean.getEntityFromDTO(dto.getUserRole()));
            user.setSearch(user.getInternalId() + " " + user.getName() + " " + user.getEmail());
            
            if (dto.isNew()) {
                super.create(user);
            } else {
                super.edit(user);
            }
        }

        return errors;
    }

}
