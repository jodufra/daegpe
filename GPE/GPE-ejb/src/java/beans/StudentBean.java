/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dtos.StudentDTO;
import entities.Student;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static utilities.Text.GenerateSlug;

/**
 *
 * @author joeld
 */
@Stateless
public class StudentBean extends AbstractBean<Student, StudentDTO> {

    @PersistenceContext(unitName = "GPE-ejbPU")
    private EntityManager em;

    public StudentBean() {
        super(Student.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Student getEntityFromDTO(StudentDTO dto) {
        return getEntity(dto.getIdUser());
    }

    @Override
    protected StudentDTO generateDTO(Student entity) {
        if(entity == null) return null;
        return new StudentDTO(entity.getIdUser(), entity.getInternalId(), entity.getName(), entity.getEmail(), entity.getPassword());
    }

    @Override
    public List<String> save(StudentDTO dto) {
        List<String> errors = new ArrayList<>();

        if (dto.getInternalId().isEmpty()) {
            errors.add("Id de Utilizador é Obrigatório.");
        }
        if (dto.getName().isEmpty()) {
            errors.add("Nome é Obrigatório.");
        }
        if (dto.getEmail().isEmpty()) {
            errors.add("Email é Obrigatório.");
        }

        if (errors.isEmpty()) {
            Student student;
            if (dto.isNew()) {
                student = new Student();
            } else {
                student = getEntity(dto.getIdUser());
            }
            student.setInternalId(dto.getInternalId());
            student.setName(dto.getName());
            student.setEmail(dto.getEmail());
            if (dto.hasNewPassword()) {
                student.setPassword(dto.getNewPassword());
            }
            student.setSearch(GenerateSlug(student.getInternalId() + " " + student.getName() + " " + student.getEmail(), true, true));

            if (dto.isNew()) {
                super.create(student);
            } else {
                super.edit(student);
            }
        }

        return errors;
    }

}
