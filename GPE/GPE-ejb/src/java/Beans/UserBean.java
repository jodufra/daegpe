/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DTOs.UserDTO;
import Entities.User;
import Facades.UserFacade;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author joeld
 */
@Stateless
public class UserBean extends AbstractBean<User, UserDTO> implements Serializable {
    
    @Inject
    private UserFacade userFacade;

    @Override
    public UserDTO Transform(User object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
