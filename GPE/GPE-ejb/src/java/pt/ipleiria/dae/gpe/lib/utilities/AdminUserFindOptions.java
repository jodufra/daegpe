/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.utilities;

import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;


/**
 *
 * @author joeld
 */
public class AdminUserFindOptions {

    public long count;
    public int pageId;
    public int pageSize;
    public UCDTO ucdto;
    public String search;
    public UserOrderBy orderBy;

    public AdminUserFindOptions(int pageId, int pageSize, UserOrderBy orderBy, String search) {
        this.pageId = pageId;
        this.pageSize = pageSize;
        this.search = search;
        this.ucdto = null;
        this.orderBy = orderBy;
        this.count = 0;
    }

    public AdminUserFindOptions(int pageId, int pageSize, UserOrderBy orderBy, UCDTO ucdto) {
        this.pageId = pageId;
        this.pageSize = pageSize;
        this.search = "";
        this.ucdto = ucdto;
        this.orderBy = orderBy;
        this.count = 0;
    }

}
