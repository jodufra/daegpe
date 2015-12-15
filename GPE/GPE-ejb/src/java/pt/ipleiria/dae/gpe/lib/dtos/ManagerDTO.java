/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import pt.ipleiria.dae.gpe.lib.entities.Manager;
import pt.ipleiria.dae.gpe.lib.entities.GROUP;

@XmlRootElement(name = "manager")
@XmlAccessorType(XmlAccessType.FIELD)
public class ManagerDTO extends UserDTO {

    public ManagerDTO(Integer idUser, String internalId, String name, String email) {
        super(idUser, GROUP.Manager, internalId, name, email);
    }

    public ManagerDTO(String internalId, String name, String email, String password) {
        super(GROUP.Manager, internalId, name, email, password);
    }

    public ManagerDTO(Manager manager) {
        super(manager);
    }

}
