/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import pt.ipleiria.dae.gpe.lib.entities.Administrator;
import pt.ipleiria.dae.gpe.lib.entities.GROUP;

@XmlRootElement(name = "administrator")
@XmlAccessorType(XmlAccessType.FIELD)
public class AdministratorDTO extends UserDTO {

    public AdministratorDTO(Integer idUser, String internalId, String name, String email) {
        super(idUser, GROUP.Administrator, internalId, name, email);
    }

    public AdministratorDTO(String internalId, String name, String email, String newPassword) {
        super(GROUP.Administrator, internalId, name, email, newPassword);
    }

    public AdministratorDTO(Administrator admin) {
        super(admin);
    }
    
    public AdministratorDTO(){
        super();
    }
}
