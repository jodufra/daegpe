/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author joeld
 */
public class ManagerDTO extends UserDTO {

    public ManagerDTO() {
    }

    public ManagerDTO(String internalId, String name, String email, String password) {
        super(internalId, name, email, password);
    }

    public ManagerDTO(Integer idUser, String internalId, String name, String email, String password) {
        super(idUser, internalId, name, email, password);
    }
    
    
}
