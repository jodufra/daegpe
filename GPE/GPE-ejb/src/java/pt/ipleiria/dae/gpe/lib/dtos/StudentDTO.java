/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import pt.ipleiria.dae.gpe.lib.entities.Student;
import pt.ipleiria.dae.gpe.lib.entities.GROUP;

@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentDTO extends UserDTO {

    public StudentDTO(Integer idUser, String internalId, String name, String email) {
        super(idUser, GROUP.Student, internalId, name, email);
    }

    public StudentDTO(String internalId, String name, String email, String newPassword) {
        super(GROUP.Student, internalId, name, email, newPassword);
    }

    public StudentDTO(Student student) {
        super(student);
    }
    
    public StudentDTO(){
        super();
    }

}
