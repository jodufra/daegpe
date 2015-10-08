/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dtos.UserRoleDTO;
import entities.User;
import entities.UserRole;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Size;

/**
 *
 * @author joeld
 */
@Stateless
public class UserRoleBean extends AbstractBean<UserRole, UserRoleDTO> {

    @PersistenceContext(unitName = "GPE-ejbPU")
    private EntityManager em;

    public UserRoleBean() {
        super(UserRole.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected UserRoleDTO generateDTO(UserRole entity) {
        return new UserRoleDTO(entity.getIdUserRole(), entity.getName());
    }

    @Override
    public List<String> save(UserRoleDTO dto) {
        List<String> errors = new ArrayList<>();

        if (dto.getName() == null || (dto.getName().isEmpty())) {
            errors.add("Nome é um campo obrigatório");
        }
        if (dto.getName() != null && dto.getName().length() > 50) {
            errors.add("Nome não deve exceder 50 caracteres.");
        }

        if (errors.isEmpty()) {
            UserRole userRole;
            if (dto.isNew()) {
                userRole = new UserRole(dto.getName());
                super.create(userRole);
            } else {
                userRole = super.findEntityFromDTO(dto.getIdUserRole());
                userRole.setName(dto.getName());
                super.edit(userRole);
            }
        }

        return errors;
    }

    public UserRoleDTO findByName(String name) {
        TypedQuery<UserRole> query = em.createNamedQuery("UserRole.findByName", UserRole.class);
        return generateDTO((UserRole) query.setParameter("name", name).getSingleResult());
    }
}
