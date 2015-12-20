/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models.manager;

import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import pt.ipleiria.dae.gpe.lib.beans.EventBean;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.beans.query.order.EventOrderBy;
import pt.ipleiria.dae.gpe.lib.beans.query.options.ManagerEventFindOptions;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;

/**
 *
 * @author Joel
 */
public class EventIndexModel {

    private final EventBean eventBean;
    private final UserBean userBean;

    public int pageId;
    public final int pageSize = 20;
    public EventOrderBy orderBy;
    public String search;

    public long count;
    public int pagesCount;

    public EventIndexModel(EventBean eventBean, UserBean userBean) {
        this.eventBean = eventBean;
        this.userBean = userBean;
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
        this.orderBy = EventOrderBy.NameAsc;
        this.search = search;
    }

    public long getCount() {
        return count;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public List<EventDTO> getEvents() throws EntityNotFoundException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        
        UserDTO user = userBean.findByUsername(request.getUserPrincipal().getName());
        ManagerEventFindOptions options = new ManagerEventFindOptions(pageId, pageSize, orderBy, user, search);
        List<EventDTO> list = eventBean.findEventsManager(options);
        this.count = options.count;
        this.pagesCount = (int) Math.ceil((double) count / (double) pageSize);
        return list;
    }
}
