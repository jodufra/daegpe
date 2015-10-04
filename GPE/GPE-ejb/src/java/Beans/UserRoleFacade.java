/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entities.User;
import Entities.UserRole;
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
public class UserRoleFacade extends AbstractFacade<UserRole> {
    @PersistenceContext(unitName = "GPE-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserRoleFacade() {
        super(UserRole.class);
    }
    
    
    public List<String> Save(UserRole userRole){
        List<String> errors = new ArrayList<>();
        
        //TODO - check for errors
        
        if(errors.isEmpty()){
            if(userRole.isNew()){
                userRole.setDatecreated(new Date());
                super.create(userRole);
            }
            else{
                userRole.setDateupdated(new Date());
                super.edit(userRole);
            }
        }
        
        return errors;
    }
}
