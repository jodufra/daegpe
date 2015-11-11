/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models;

import java.util.List;
import pt.ipleiria.dae.gpe.lib.beans.UCBean;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.utilities.UCOrderBy;

/**
 *
 * @author LuoSKraD
 */
public class UCIndexModel {

    private final UCBean ucBean;

    private int pageId;
    private final int pageSize = 20;
    public UCOrderBy orderBy;
    private List<UCDTO> ucs;

    public UCIndexModel(UCBean ucBean) {
        this.ucBean = ucBean;
        this.pageId = 1;
        this.orderBy = UCOrderBy.InternalIdAsc;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public UCOrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(UCOrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public List<UCDTO> getUcs() {
        return ucBean.find(pageId, pageSize, orderBy);
    }
   
    
}
