/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DTOs.UserDTO;
import Entities.User;
import java.util.ArrayList;
import java.util.Date;
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
    protected UserDTO transformToDTO(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected User findEntityFromDTO(UserDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> Save(UserDTO dto) {
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
