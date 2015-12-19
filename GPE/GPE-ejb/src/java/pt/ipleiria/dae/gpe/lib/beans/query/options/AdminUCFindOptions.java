/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.beans.query.options;

import pt.ipleiria.dae.gpe.lib.beans.query.order.UCOrderBy;


/**
 *
 * @author joeld
 */
public class AdminUCFindOptions {

    public long count;
    public int pageId;
    public int pageSize;
    public String search;
    public UCOrderBy orderBy;

    public AdminUCFindOptions(int pageId, int pageSize, UCOrderBy orderBy, String search) {
        this.pageId = pageId;
        this.pageSize = pageSize;
        this.search = search;
        this.orderBy = orderBy;
        this.count = 0;
    }

}
