/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

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
public class UserFacade extends AbstractFacade<User> {
    @PersistenceContext(unitName = "GPE-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    public List<String> Save(User user){
        List<String> errors = new ArrayList<>();
        
        //TODO - check for errors
        
        if(errors.isEmpty()){
            if(user.isNew()){
                user.setDatecreated(new Date());
                super.create(user);
            }
            else{
                user.setDateupdated(new Date());
                super.edit(user);
            }
        }
        
        return errors;
    }
    
}
