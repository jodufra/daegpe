/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models.manager;

import java.util.List;
import javax.faces.context.FacesContext;
import pt.ipleiria.dae.gpe.lib.beans.EventBean;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.utilities.EventOrderBy;
import pt.ipleiria.dae.gpe.lib.utilities.ManagerEventFindOptions;

/**
 *
 * @author Joel
 */
public class EventIndexModel {

    private final EventBean eventBean;

    public int pageId;
    public final int pageSize = 20;
    public EventOrderBy orderBy;
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
            case UCNameAsc:
                return 3;
            case UCNameDesc:
                return 4;
            case ManagerNameAsc:
                return 5;
            case ManagerNameDesc:
                return 6;
            case DateEndAsc:
                return 7;
            case DateEndDesc:
                return 8;
            case DateStartAsc:
                return 9;
            case DateStartDesc:
                return 10;
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
                orderBy = EventOrderBy.UCNameAsc;
                break;
            case 4:
                orderBy = EventOrderBy.UCNameDesc;
                break;
            case 5:
                orderBy = EventOrderBy.ManagerNameAsc;
                break;
            case 6:
                orderBy = EventOrderBy.ManagerNameDesc;
                break;
            case 7:
                orderBy = EventOrderBy.DateEndAsc;
                break;
            case 8:
                orderBy = EventOrderBy.DateEndDesc;
                break;
            case 9:
                orderBy = EventOrderBy.DateStartAsc;
                break;
            case 10:
                orderBy = EventOrderBy.DateStartDesc;
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
        ManagerEventFindOptions options = new ManagerEventFindOptions(pageId, pageSize, orderBy, (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user"), search);
        List<EventDTO> list = eventBean.findEventsManager(options);
        this.count = options.count;
        this.pagesCount = (int) Math.ceil((double) count / (double) pageSize);
        return list;
    }
}
