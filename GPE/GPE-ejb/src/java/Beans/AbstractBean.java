/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DTOs.AbstractDTO;
import Entities.AbstractEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author joeld
 * @param <Entity>
 * @param <DTO>
 */
public abstract class AbstractBean<Entity extends AbstractEntity, DTO extends AbstractDTO> {

    private final Class<Entity> entityClass;

    public AbstractBean(Class<Entity> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    protected abstract DTO transformToDTO(Entity entity);

    protected abstract Entity findEntityFromDTO(DTO dto);

    public abstract List<String> Save(DTO dto);

    protected List<DTO> transformToDTOList(List<Entity> list) {
        List<DTO> results = new ArrayList<>();
        for (Entity entity : list) {
            results.add(transformToDTO(entity));
        }
        return results;
    }

    protected void create(Entity entity) {
        getEntityManager().persist(entity);
    }

    protected void edit(Entity entity) {
        getEntityManager().merge(entity);
    }

    public void remove(DTO dto) {
        if (!dto.isNew()) {
            getEntityManager().remove(getEntityManager().merge(findEntityFromDTO(dto)));
        }
    }

    public DTO find(Object id) {
        return transformToDTO(getEntityManager().find(entityClass, id));
    }

    public List<DTO> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return transformToDTOList(getEntityManager().createQuery(cq).getResultList());
    }

    public List<DTO> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return transformToDTOList(q.getResultList());
    }

    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<Entity> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
