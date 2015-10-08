/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import beans.UserBean;
import dtos.UserDTO;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author joeld
 */
@ManagedBean
@SessionScoped
public class UsersManager {
    
    @EJB 
    private UserBean userBean;
    
    public List<UserDTO> allUsers(){
        return userBean.findAll();
    }
    
}
