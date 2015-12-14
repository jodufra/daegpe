/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.beans.query.options;

import pt.ipleiria.dae.gpe.lib.beans.query.order.EventOrderBy;

/**
 *
 * @author LuoSKraD
 */
public class AdminEventFindOptions {
    
    public long count;
    public int pageId;
    public int pageSize;
    public String search;
    public String internalId;
    public EventOrderBy orderBy;

    public AdminEventFindOptions(int pageId, int pageSize, EventOrderBy orderBy, String search) {
        this.pageId = pageId;
        this.pageSize = pageSize;
        this.search = search;        
        this.internalId = "";
        this.orderBy = orderBy;
        this.count = 0;
    }

    public AdminEventFindOptions(int pageId, int pageSize, EventOrderBy orderBy, String search, String internalId) {
        this.pageId = pageId;
        this.pageSize = pageSize;
        this.search = search;        
        this.internalId = internalId;
        this.orderBy = orderBy;
        this.count = 0;
    }
}
