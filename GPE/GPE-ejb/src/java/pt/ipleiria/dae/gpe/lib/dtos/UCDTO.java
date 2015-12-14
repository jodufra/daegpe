/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.dtos;

import java.util.Collection;
import pt.ipleiria.dae.gpe.lib.core.AbstractDTO;
import pt.ipleiria.dae.gpe.lib.entities.UC;

/**
 *
 * @author joeld
 */
public class UCDTO extends AbstractDTO {

    private Integer idUC;
    private String internalId;
    private String name;
    private Collection<StudentDTO> student;

    public UCDTO(Integer idUc, String internalId, String name) {
        super(null);
        this.idUC = idUc;
        this.internalId = internalId;
        this.name = name;
        this.New = idUC == 0;
    }

    public UCDTO(String internalId, String name) {
        super(null);
        this.idUC = 0;
        this.internalId = internalId;
        this.name = name;
    }

    public UCDTO(UC uc) {
        super(uc);
        this.idUC = uc.getIdUC();
        this.internalId = uc.getInternalId();
        this.name = uc.getName();
    }

    public Integer getIdUC() {
        return idUC;
    }

    public void setIdUC(Integer idUC) {
        this.idUC = idUC;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object getRelationalId() {
        return getIdUC();
    }
    
    @Override
    public String toString(){
        return this.name;
    }

    public Collection<StudentDTO> getStudent() {
        return student;
    }
    
    public void setStudent(Collection<StudentDTO> student) {
        this.student = student;
    }
}
