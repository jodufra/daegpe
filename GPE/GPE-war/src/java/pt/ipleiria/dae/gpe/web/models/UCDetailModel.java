/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models;

import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;

/**
 *
 * @author LuoSKraD
 */
public class UCDetailModel {

    private int idUc;
    private String internalId;
    private String name;
    private boolean isNew;

    public void setUc(UCDTO uc) {
        if (uc != null && !uc.isNew()) {
            this.idUc = uc.getIdUC();
            this.internalId = uc.getInternalId();
            this.name = uc.getName();
            this.isNew = uc.isNew();
        } else {
            this.idUc = 0;
            this.internalId = "";
            this.name = "";
            this.isNew = true;
        }
    }

    public String title() {
        return isNew ? "Adicionar Unidade Curricular" : name;
    }

    public UCDTO save() {
        UCDTO uc = new UCDTO(idUc, internalId, name);
        return uc;
    }

    public int getIdUc() {
        return idUc;
    }

    public void setIdUc(int idUc) {
        this.idUc = idUc;
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

    public boolean isIsNew() {
        return isNew;
    }

}
