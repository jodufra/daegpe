/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author joeld
 */
public class UCDTO extends AbstractDTO {

    private Integer idUC;
    private String internalId;
    private String name;

    public UCDTO() {
        this.idUC = 0;
        this.internalId = "";
        this.name = "";
    }

    public UCDTO(String internalId, String name) {
        this.idUC = 0;
        this.internalId = internalId;
        this.name = name;
    }

    public UCDTO(Integer idUC, String internalId, String name) {
        this.idUC = idUC;
        this.internalId = internalId;
        this.name = name;
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
    public boolean isNew() {
        return idUC == 0;
    }


}
