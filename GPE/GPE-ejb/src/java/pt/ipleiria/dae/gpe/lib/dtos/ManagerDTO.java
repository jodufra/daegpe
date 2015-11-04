/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.dtos;

import pt.ipleiria.dae.gpe.lib.entities.Manager;
import pt.ipleiria.dae.gpe.lib.entities.UserType;

/**
 *
 * @author joeld
 */
public class ManagerDTO extends UserDTO {

    public ManagerDTO(Integer idUser, String internalId, String name, String email) {
        super(idUser, UserType.Manager, internalId, name, email);
    }

    public ManagerDTO(String internalId, String name, String email, String password) {
        super(UserType.Manager, internalId, name, email, password);
    }

    public ManagerDTO(Manager manager) {
        super(manager);
    }

}
