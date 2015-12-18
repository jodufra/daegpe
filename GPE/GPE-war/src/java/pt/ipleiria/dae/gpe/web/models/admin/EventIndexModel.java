/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models.admin;

import java.util.List;
import pt.ipleiria.dae.gpe.lib.beans.EventBean;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.beans.query.options.AdminEventFindOptions;
import pt.ipleiria.dae.gpe.lib.beans.query.order.EventOrderBy;

/**
 *
 * @author pedroextendssilva
 */
public class EventIndexModel {

    private final EventBean eventBean;
    
    public String internalId;

    public int pageId;
    //TODO - By Pedro
    public final int pageSize = 50;
    public EventOrderBy orderBy;
    public List<EventDTO> users;
    public String search;

    public long count;
    public int pagesCount;

    public EventIndexModel(EventBean eventBean) {
        this.eventBean = eventBean;
        this.pageId = 1;
        this.orderBy = EventOrderBy.InternalIdAsc;
        this.search = "";
        this.count = 0;
    }
    
    public void setInternalId(String internalId){
        this.internalId = internalId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getPageId() {
        return this.pageId;
    }

    public int getOrderBy() {
        switch (orderBy) {
            case InternalIdAsc:
                return 1;
            case InternalIdDesc:
                return 2;
            case NameAsc:
                return 3;
            case NameDesc:
                return 4;
            case TypeAsc:
                return 5;
            case TypeDesc:
                return 6;
            case UCNameAsc:
                return 7;
            case UCNameDesc:
                return 8;
            case ManagerNameAsc:
                return 9;
            case ManagerNameDesc:
                return 10;
        }
        return 0;
    }

    public void setOrderBy(int val) {
        switch (val) {
            case 1:
                orderBy = EventOrderBy.InternalIdAsc;
                break;
            case 2:
                orderBy = EventOrderBy.InternalIdDesc;
                break;
            case 3:
                orderBy = EventOrderBy.NameAsc;
                break;
            case 4:
                orderBy = EventOrderBy.NameDesc;
                break;
            case 5:
                orderBy = EventOrderBy.TypeAsc;
                break;
            case 6:
                orderBy = EventOrderBy.TypeDesc;
                break;
            case 7:
                orderBy = EventOrderBy.UCNameAsc;
                break;
            case 8:
                orderBy = EventOrderBy.UCNameDesc;
                break;
            case 9:
                orderBy = EventOrderBy.ManagerNameAsc;
                break;
            case 10:
                orderBy = EventOrderBy.ManagerNameDesc;
                break;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.pageId = 1;
        this.orderBy = EventOrderBy.InternalIdAsc;
        this.search = search;
    }

    public long getCount() {
        return count;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public List<EventDTO> getEvents() {
        AdminEventFindOptions options = new AdminEventFindOptions(pageId, pageSize, orderBy, search, internalId);
        List<EventDTO> list = eventBean.find(options);
        this.count = options.count;
        this.pagesCount = (int) Math.ceil((double) count / (double) pageSize);
        return list;
    }

}
