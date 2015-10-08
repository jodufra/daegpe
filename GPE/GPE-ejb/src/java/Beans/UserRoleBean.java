/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DTOs.UserRoleDTO;
import Entities.UserRole;
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
public class UserRoleBean extends AbstractBean<UserRole, UserRoleDTO> {

    @PersistenceContext(unitName = "GPE-ejbPU")
    private EntityManager em;

    public UserRoleBean() {
        super(UserRole.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected UserRoleDTO transformToDTO(UserRole entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected UserRole findEntityFromDTO(UserRoleDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> Save(UserRoleDTO dto) {
        List<String> errors = new ArrayList<>();

        //TODO: check for errors
        
        if (errors.isEmpty()) {
            UserRole userRole = null;
            if (dto.isNew()) {
                super.create(userRole);
            } else {
                super.edit(userRole);
            }
        }

        return errors;
    }
}
