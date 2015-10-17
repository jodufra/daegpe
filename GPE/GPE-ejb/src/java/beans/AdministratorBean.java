/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dtos.AdministratorDTO;
import dtos.UserDTO;
import entities.Administrator;
import entities.User;
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
public class AdministratorBean extends AbstractBean<Administrator, AdministratorDTO> {

    @PersistenceContext(unitName = "GPE-ejbPU")
    private EntityManager em;

    public AdministratorBean() {
        super(Administrator.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Administrator getEntityFromDTO(AdministratorDTO dto) {
        return getEntity(dto.getIdUser());
    }

    @Override
    protected AdministratorDTO generateDTO(Administrator entity) {
        if(entity == null) return null;
        return new AdministratorDTO(entity.getIdUser(), entity.getInternalId(), entity.getName(), entity.getEmail(), entity.getPassword());
    }

    @Override
    public List<String> save(AdministratorDTO dto) {
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
            Administrator admin;
            if (dto.isNew()) {
                admin = new Administrator();
            } else {
                admin = getEntity(dto.getIdUser());
            }
            admin.setInternalId(dto.getInternalId());
            admin.setName(dto.getName());
            admin.setEmail(dto.getEmail());
            if (dto.hasNewPassword()) {
                admin.setPassword(dto.getNewPassword());
            }
            admin.setSearch(GenerateSlug(admin.getInternalId() + " " + admin.getName() + " " + admin.getEmail(), true, true));

            if (dto.isNew()) {
                super.create(admin);
            } else {
                super.edit(admin);
            }
        }

        return errors;
    }

}
