/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.utilities;

/**
 *
 * @author joeld
 */
public class UserFindOptions {

    public long count;
    public int pageId;
    public int pageSize;
    public String search;
    public UserOrderBy orderBy;

    public UserFindOptions(int pageId, int pageSize, UserOrderBy orderBy, String search) {
        this.pageId = pageId;
        this.pageSize = pageSize;
        this.search = search;
        this.orderBy = orderBy;
        this.count = 0;
    }

}
