/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.dtos;

import pt.ipleiria.dae.gpe.lib.entities.User;

/**
 *
 * @author joeld
 */
public class AdministratorDTO extends UserDTO {

    public AdministratorDTO(Integer idUser, String internalId, String name, String email) {
        super(idUser, internalId, name, email);
    }

    public AdministratorDTO(String internalId, String name, String email, String newPassword) {
        super(internalId, name, email, newPassword);
    }

    public AdministratorDTO(User user) {
        super(user);
    }

}
