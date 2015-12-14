/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.beans.query.options;

import pt.ipleiria.dae.gpe.lib.beans.query.order.UserOrderBy;


/**
 *
 * @author joeld
 */
public class ManagerUserFindOptions {

    public long count;
    public int pageId;
    public int pageSize;
    public String search;
    public UserOrderBy orderBy;

    public ManagerUserFindOptions(int pageId, int pageSize, UserOrderBy orderBy, String search) {
        this.pageId = pageId;
        this.pageSize = pageSize;
        this.search = search;
        this.orderBy = orderBy;
        this.count = 0;
    }

}
