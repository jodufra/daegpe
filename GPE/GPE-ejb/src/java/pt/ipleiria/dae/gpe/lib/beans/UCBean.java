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
import java.util.Objects;
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
        super(UC.class, UCDTO.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<EntityValidationError> save(UCDTO dto) {
        List<EntityValidationError> errors = new ArrayList<>();

        if (dto.getInternalId().isEmpty()) {
            errors.add(EntityValidationError.UC_INTERNAL_REQUIRED);
        }
        else {
            UCDTO ucWithSameID = find(dto.getInternalId());
            if (ucWithSameID != null && !Objects.equals(ucWithSameID.getIdUC(), dto.getIdUC())) {
                errors.add(EntityValidationError.UC_INTERNALID_NOT_UNIQUE);
            }
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
            uc.setIdUC(dto.getIdUC());
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
    
    public enum UCOrderBy {

        InternalIdAsc, InternalIdDesc, NameAsc, NameDesc
    }
    
    
    public List<UCDTO> find(int pageId, int pageSize, UCOrderBy orderBy) {
        String query = "SELECT u FROM UC u";

        switch (orderBy) {
            case InternalIdAsc:
                query += " ORDER BY u.internalId";
                break;
            case InternalIdDesc:
                query += " ORDER BY u.internalId desc";
                break;
            case NameAsc:
                query += " ORDER BY u.name";
                break;
            case NameDesc:
                query += " ORDER BY u.name desc";
                break;
        }

        if (pageId != 0 && pageSize != 0) {
            int offset = (pageId - 1) * pageSize;
            return generateDTOList(em.createQuery(query).setFirstResult(offset).setMaxResults(pageSize).getResultList());
        }

        return generateDTOList(em.createQuery(query, UC.class).getResultList());
    }
}
