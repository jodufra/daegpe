/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.beans;

import pt.ipleiria.dae.gpe.lib.core.AbstractBean;
import pt.ipleiria.dae.gpe.lib.core.EntityValidationError;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.entities.UC;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;

/**
 *
 * @author joeld
 */
@Stateless
public class UCBean extends AbstractBean<UC, UCDTO> {

    @PersistenceContext(unitName = "GPE-ejbPU")
    private EntityManager em;

    public UCBean() {
        super(UC.class, UCDTO.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void save(UCDTO dto) throws EntityValidationException, EntityNotFoundException {
        List<EntityValidationError> errors = new ArrayList<>();

        if (dto.getInternalId().isEmpty()) {
            errors.add(EntityValidationError.UC_INTERNAL_REQUIRED);
        }
        if (dto.getName().isEmpty()) {
            errors.add(EntityValidationError.UC_NAME_REQUIRED);
        }

        if (errors.isEmpty()) {
            UC uc;
            if (dto.isNew()) {
                uc = new UC();
            } else {
                uc = getEntityFromDTO(dto);
            }
            uc.setInternalId(dto.getInternalId());
            uc.setName(dto.getName());
            uc.setSearch(uc.getInternalId() + " " + uc.getName());

            if (uc.isNew()) {
                create(uc);
            } else {
                edit(uc);
            }
        } else {
            throw new EntityValidationException(errors);
        }
    }

}
