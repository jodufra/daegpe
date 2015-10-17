/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dtos.ManagerDTO;
import entities.Manager;
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
public class ManagerBean extends AbstractBean<Manager, ManagerDTO> {

    @PersistenceContext(unitName = "GPE-ejbPU")
    private EntityManager em;

    public ManagerBean() {
        super(Manager.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Manager getEntityFromDTO(ManagerDTO dto) {
        return getEntity(dto.getIdUser());
    }

    @Override
    protected ManagerDTO generateDTO(Manager entity) {
        if(entity == null) return null;
        return new ManagerDTO(entity.getIdUser(), entity.getInternalId(), entity.getName(), entity.getEmail(), entity.getPassword());
    }

    @Override
    public List<String> save(ManagerDTO dto) {
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
            Manager manager;
            if (dto.isNew()) {
                manager = new Manager();
            } else {
                manager = getEntity(dto.getIdUser());
            }
            manager.setInternalId(dto.getInternalId());
            manager.setName(dto.getName());
            manager.setEmail(dto.getEmail());
            if (dto.hasNewPassword()) {
                manager.setPassword(dto.getNewPassword());
            }
            manager.setSearch(GenerateSlug(manager.getInternalId() + " " + manager.getName() + " " + manager.getEmail(), true, true));

            if (dto.isNew()) {
                super.create(manager);
            } else {
                super.edit(manager);
            }
        }

        return errors;
    }

}
