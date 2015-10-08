/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dtos.UserDTO;
import entities.User;
import java.util.ArrayList;
import java.util.List;
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

    public UserBean() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected UserDTO generateDTO(User entity) {
        return new UserDTO();
    }

    @Override
    public List<String> save(UserDTO dto) {
        List<String> errors = new ArrayList<>();

        //TODO: check for errors
        
        if (errors.isEmpty()) {
            User user = null;
            if (dto.isNew()) {
                super.create(user);
            } else {
                super.edit(user);
            }
        }

        return errors;
    }

}
