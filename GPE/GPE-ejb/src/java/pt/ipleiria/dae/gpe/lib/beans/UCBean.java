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
import javax.persistence.TypedQuery;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.entities.Student;
import pt.ipleiria.dae.gpe.lib.entities.UserType;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.lib.utilities.AdminUCFindOptions;
import pt.ipleiria.dae.gpe.lib.utilities.StudentUCFindOptions;
import static pt.ipleiria.dae.gpe.lib.utilities.Text.GenerateSlug;
import pt.ipleiria.dae.gpe.lib.utilities.UCOrderBy;

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
            errors.add(EntityValidationError.UC_INTERNALID_REQUIRED);
        } else {
            UCDTO ucWithSameInternalId;
            try {
                ucWithSameInternalId = find(dto.getInternalId());
                if (ucWithSameInternalId != null && !Objects.equals(ucWithSameInternalId.getIdUC(), dto.getIdUC())) {
                    errors.add(EntityValidationError.UC_INTERNALID_NOT_UNIQUE);
                }
            } catch (EntityNotFoundException ex) {
                // Do nothing. This execption means that there are no users with repeated InternalId.
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
            uc.setInternalId(dto.getInternalId());
            uc.setName(dto.getName());
            uc.setSearch(GenerateSlug(uc.getInternalId() + " " + uc.getName(), true, true));

            if (uc.isNew()) {
                super.create(uc);
            } else {
                super.edit(uc);
            }
        } else {
            throw new EntityValidationException(errors);
        }
    }

    public void addStudentUC(UCDTO ucDTO, UserDTO userDTO) throws EntityNotFoundException, EntityValidationException {
        List<EntityValidationError> errors = new ArrayList<>();
        if (userDTO.isNew()) {
            errors.add(EntityValidationError.USER_IS_NEW);
        }
        if (userDTO.getType() != UserType.Student) {
            errors.add(EntityValidationError.USER_IS_NOT_STUDENT);
        }
        if (ucDTO.isNew()) {
            errors.add(EntityValidationError.UC_IS_NEW);
        }

        if (errors.isEmpty()) {
            UC uc = em.find(UC.class, ucDTO.getIdUC());
            Student student = em.find(Student.class, userDTO.getIdUser());
            if (!uc.getStudents().contains(student)) {
                uc.addStudent(student);
                edit(uc);
            }
        } else {
            throw new EntityValidationException(errors);
        }
    }

    public UCDTO find(String internalId) throws EntityNotFoundException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u FROM UC u WHERE u.internalId = \"").append(internalId).append("\"");
        TypedQuery<UC> query = em.createQuery(sb.toString(), UC.class);
        List<UC> ucs = query.getResultList();
        if (ucs.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return generateDTO(ucs.get(0));
    }

    public List<UCDTO> find(int pageId, int pageSize, UCOrderBy orderBy) {
        return find(new AdminUCFindOptions(pageId, pageSize, orderBy, null));
    }

    public List<UCDTO> find(AdminUCFindOptions options) {
        String query = "SELECT u FROM UC u";

        if (options.search != null && !options.search.isEmpty()) {
            String[] pieces = options.search.split(" ");
            boolean first = true;
            for (int i = 0; i < pieces.length; i++) {
                if (pieces[i].equals(" ") || pieces[i].isEmpty()) {
                    continue;
                }
                pieces[i] = GenerateSlug(pieces[i], true, true);
                if (first) {
                    query += " WHERE ";
                    first = false;
                } else {
                    query += " AND ";
                }
                query += "u.search LIKE '%" + pieces[i] + "%'";
            }
        }

        options.count = (long) em.createQuery(query.replace("SELECT u", "SELECT COUNT(u)")).getSingleResult();

        switch (options.orderBy) {
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

        if (options.pageId > 0 && options.pageSize > 0) {
            int offset = (options.pageId - 1) * options.pageSize;
            return generateDTOList(em.createQuery(query).setFirstResult(offset).setMaxResults(options.pageSize).getResultList());
        }

        return generateDTOList(em.createQuery(query, UC.class).getResultList());
    }

    public List<UCDTO> findFromStudent(StudentUCFindOptions options) {
        String query = "SELECT u FROM Student s JOIN s.ucs u WHERE s.idUser = " + options.user.getIdUser();

        if (options.search != null && !options.search.isEmpty()) {
            String[] pieces = options.search.split(" ");
            for (String piece : pieces) {
                if (piece.equals(" ") || piece.isEmpty()) {
                    continue;
                }
                query += " AND u.search LIKE '%" + GenerateSlug(piece, true, true) + "%'";
            }
        }

        options.count = (long) em.createQuery(query.replace("SELECT u", "SELECT COUNT(u)")).getSingleResult();

        switch (options.orderBy) {
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

        if (options.pageId > 0 && options.pageSize > 0) {
            int offset = (options.pageId - 1) * options.pageSize;
            return generateDTOList(em.createQuery(query).setFirstResult(offset).setMaxResults(options.pageSize).getResultList());
        }

        return generateDTOList(em.createQuery(query, UC.class).getResultList());
    }

}
