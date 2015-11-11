/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.dtos;

import pt.ipleiria.dae.gpe.lib.entities.Student;
import pt.ipleiria.dae.gpe.lib.entities.UserType;

/**
 *
 * @author joeld
 */
public class StudentDTO extends UserDTO {

    public StudentDTO(Integer idUser, String internalId, String name, String email) {
        super(idUser, UserType.Student, internalId, name, email);
    }

    public StudentDTO(String internalId, String name, String email, String newPassword) {
        super(UserType.Student, internalId, name, email, newPassword);
    }

    public StudentDTO(Student student) {
        super(student);
    }

}