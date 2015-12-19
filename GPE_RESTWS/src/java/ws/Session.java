/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;

/**
 *
 * @author joeld
 */
public class Session {

    public static Session Current = new Session();

    private UserDTO user;

    private Session() {
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

}
