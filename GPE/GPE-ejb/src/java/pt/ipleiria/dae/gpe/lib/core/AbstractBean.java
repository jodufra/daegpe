/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;

/**
 *
 * @author joeld
 * @param <Entity>
 * @param <DTO>
 */
public abstract class AbstractBean<Entity extends AbstractEntity, DTO extends AbstractDTO> {

    private final Class<Entity> entityClass;
    private final Class<DTO> dtoClass;

    public AbstractBean(Class<Entity> entityClass, Class<DTO> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    protected abstract EntityManager getEntityManager();

    protected Entity getEntityFromDTO(DTO dto) throws EntityNotFoundException {
        return getEntity(dto.getRelationalId());
    }

    protected DTO generateDTO(Entity entity) {
        if (entity != null) {
            try {
                Constructor<DTO> declaredConstructor = (Constructor<DTO>) DeclaredConstructorCache.CACHE.getDeclaredConstructor(dtoClass, entityClass);
                return declaredConstructor.newInstance(entity);
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(AbstractBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public abstract void save(DTO dto) throws EntityValidationException, EntityNotFoundException;

    protected List<DTO> generateDTOList(List<Entity> entities) {
        List<DTO> results = new ArrayList<>();
        for (Entity entity : entities) {
            results.add(generateDTO(entity));
        }
        return results;
    }

    protected void create(Entity entity) {
        getEntityManager().persist(entity);
    }

    protected void edit(Entity entity) {
        getEntityManager().merge(entity);
    }

    public void remove(DTO dto) throws EntityNotFoundException {
        if (!dto.isNew()) {
            getEntityManager().remove(getEntityManager().merge(getEntityFromDTO(dto)));
        }
    }

    public void removeById(Object id) throws EntityNotFoundException {
        Entity entity = getEntity(id);
        if (!entity.isNew()) {
            getEntityManager().remove(getEntityManager().merge(entity));
        }
    }

    protected Entity getEntity(Object id) throws EntityNotFoundException {
        return getEntityManager().find(entityClass, id);
    }

    public DTO find(Object obj) throws EntityNotFoundException {
        Entity entity;
        if (obj.getClass().equals(AbstractDTO.class)) {
            entity = getEntityFromDTO((DTO) obj);
        } else {
            entity = getEntity(obj);
        }
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        return generateDTO(entity);
    }

    public List<DTO> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return generateDTOList(getEntityManager().createQuery(cq).getResultList());
    }

    public List<DTO> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return generateDTOList(q.getResultList());
    }

    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<Entity> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
