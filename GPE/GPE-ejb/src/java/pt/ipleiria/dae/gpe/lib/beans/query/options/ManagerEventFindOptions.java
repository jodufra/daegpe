/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.beans.query.options;

import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.beans.query.order.EventOrderBy;

/**
 *
 * @author joeld
 */
public class ManagerEventFindOptions {

    public long count;
    public int pageId;
    public int pageSize;
    public String search;
    public UserDTO user;
    public EventOrderBy orderBy;

    public ManagerEventFindOptions(int pageId, int pageSize, EventOrderBy orderBy, UserDTO user, String search) {
        this.pageId = pageId;
        this.pageSize = pageSize;
        this.search = search;
        this.orderBy = orderBy;
        this.user = user;
        this.count = 0;
    }

}
