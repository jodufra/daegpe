/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models.admin;

import java.util.List;
import pt.ipleiria.dae.gpe.lib.beans.EventBean;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.utilities.AdminEventFindOptions;
import pt.ipleiria.dae.gpe.lib.utilities.EventOrderBy;

/**
 *
 * @author pedroextendssilva
 */
public class EventIndexModel {

    private final EventBean eventBean;

    public int pageId;
    public final int pageSize = 20;
    public EventOrderBy orderBy;
    public List<EventDTO> users;
    public String search;
    
    public long count;
    public int pagesCount;

    public EventIndexModel(EventBean eventBean) {
        this.eventBean = eventBean;
        this.pageId = 1;
        this.orderBy = EventOrderBy.NameAsc;
        this.search = "";
        this.count = 0;
    }


    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getOrderBy() {
        switch (orderBy) {
            case NameAsc:
                return 1;
            case NameDesc:
                return 2;
            case TypeAsc:
                return 3;
            case TypeDesc:
                return 4;
            case DayWeekAsc:
                return 5;
            case DayWeekDesc:
                return 6;
            case TimeAsc:
                return 7;
            case TimeDesc:
                return 8;
            case LocalAsc:
                return 9;
            case LocalDesc:
                return 10;
            case StartWeekAsc:
                return 11;
            case StartWeekDesc:
                return 12;
            case ManagerAsc:
                return 13;
            case ManagerDesc:
                return 14;
        }
        return 0;
    }

    public void setOrderBy(int val) {
        switch (val) {
            case 1:
                orderBy = EventOrderBy.NameAsc;
                break;
            case 2:
                orderBy = EventOrderBy.NameDesc;
                break;
            case 3:
                orderBy = EventOrderBy.TypeAsc;
                break;
            case 4:
                orderBy = EventOrderBy.TypeDesc;
                break;
            case 5:
                orderBy = EventOrderBy.DayWeekAsc;
                break;
            case 6:
                orderBy = EventOrderBy.DayWeekDesc;
                break;
            case 7:
                orderBy = EventOrderBy.TimeAsc;
                break;
            case 8:
                orderBy = EventOrderBy.TimeDesc;
                break;
            case 9:
                orderBy = EventOrderBy.LocalAsc;
                break;
            case 10:
                orderBy = EventOrderBy.LocalDesc;
                break;
            case 11:
                orderBy = EventOrderBy.StartWeekAsc;
                break;
            case 12:
                orderBy = EventOrderBy.StartWeekDesc;
                break;
            case 13:
                orderBy = EventOrderBy.ManagerAsc;
                break;
            case 14:
                orderBy = EventOrderBy.ManagerDesc;
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
        this.orderBy = EventOrderBy.NameAsc;
        this.search = search;
    }
     public long getCount() {
        return count;
    }

    public int getPagesCount() {
        return pagesCount;
    }
     public List<EventDTO> getEvents() {
         AdminEventFindOptions options = new AdminEventFindOptions(pageId, pageSize, orderBy, search);
        List<EventDTO> list = eventBean.find(options);
        this.count = options.count;
        this.pagesCount = (int) Math.ceil((double) count / (double) pageSize);
        return list;
    }
    
}
