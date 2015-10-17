/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dtos.UCDTO;
import entities.UC;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author joeld
 */
@Stateless
public class UCBean extends AbstractBean<UC, UCDTO> {

    @PersistenceContext(unitName = "GPE-ejbPU")
    private EntityManager em;

    public UCBean() {
        super(UC.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected UC getEntityFromDTO(UCDTO dto) {
        return getEntity(dto.getIdUC());
    }

    @Override
    protected UCDTO generateDTO(UC entity) {
        return new UCDTO(entity.getIdUC(), entity.getInternalId(), entity.getName());
    }

    @Override
    public List<String> save(UCDTO dto) {
        List<String> errors = new ArrayList<>();

        if (dto.getInternalId().isEmpty()) {
            errors.add("Id é obrigatório.");
        }

        // TODO
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
        }

        return errors;
    }

}
