/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.dtos;

import pt.ipleiria.dae.gpe.lib.entities.Administrator;
import pt.ipleiria.dae.gpe.lib.entities.UserType;

/**
 *
 * @author joeld
 */
public class AdministratorDTO extends UserDTO {

    public AdministratorDTO(Integer idUser, String internalId, String name, String email) {
        super(idUser, UserType.Administrator, internalId, name, email);
    }

    public AdministratorDTO(String internalId, String name, String email, String newPassword) {
        super(UserType.Administrator, internalId, name, email, newPassword);
    }

    public AdministratorDTO(Administrator admin) {
        super(admin);
    }

}
