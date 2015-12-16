/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models.admin;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import pt.ipleiria.dae.gpe.lib.beans.UCBean;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.beans.query.options.AdminUserFindOptions;
import pt.ipleiria.dae.gpe.lib.beans.query.order.UserOrderBy;

/**
 *
 * @author LuoSKraD
 */
public class UCDetailModel {

    private final UserBean userBean;
    private final UCBean ucBean;

    private String tab;

    private int idUc;
    private String internalId;
    private String name;
    private boolean isNew;

    private UCDTO uc;
    private String userSearch;

    public UCDetailModel(UCBean ucBean, UserBean userBean) {
        this.userBean = userBean;
        this.ucBean = ucBean;
        this.userSearch = "";
        this.tab = "details";
    }

    public void setUc(UCDTO uc) {
        this.uc = uc;
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
        return isNew ? "Adicionar Evento" : name;
    }

    public UCDTO provideUCDTO() {
        uc = new UCDTO(idUc, internalId, name);
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

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getUserSearch() {
        return userSearch;
    }

    public void setUserSearch(String userSearch) {
        this.userSearch = userSearch;
    }

    public List<UserDTO> getSearchedUsers() {
        if (uc == null || uc.isNew() || userSearch.isEmpty()) {
            return new ArrayList<>();
        }
        AdminUserFindOptions options = new AdminUserFindOptions(1, 5, UserOrderBy.InternalIdAsc, userSearch);
        return userBean.find(options);
    }

    public List<UserDTO> getUcUsers() {
        if (uc == null || uc.isNew()) {
            return new ArrayList<>();
        }
        AdminUserFindOptions options = new AdminUserFindOptions(0, 0, UserOrderBy.InternalIdAsc, uc);
        return userBean.findFromUC(options);
    }
}
