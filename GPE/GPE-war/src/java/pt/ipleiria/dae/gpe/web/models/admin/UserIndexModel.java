/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models.admin;

import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import java.util.List;
import pt.ipleiria.dae.gpe.lib.utilities.AdminUserFindOptions;
import pt.ipleiria.dae.gpe.lib.utilities.UserOrderBy;

/**
 *
 * @author Joel
 */
public class UserIndexModel {

    private final UserBean userBean;

    public int pageId;
    public final int pageSize = 20;
    public UserOrderBy orderBy;
    public String search;

    public long count;
    public int pagesCount;

    public UserIndexModel(UserBean userBean) {
        this.userBean = userBean;
        this.pageId = 1;
        this.orderBy = UserOrderBy.InternalIdAsc;
        this.search = "";
        this.count = 0;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getCount() {
        return count;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getOrderBy() {
        switch (orderBy) {
            case EmailAsc:
                return 1;
            case EmailDesc:
                return 2;
            case InternalIdAsc:
                return 3;
            case InternalIdDesc:
                return 4;
            case NameAsc:
                return 5;
            case NameDesc:
                return 6;
            case TypeAsc:
                return 7;
            case TypeDesc:
                return 8;
        }
        return 0;
    }

    public void setOrderBy(int val) {

        switch (val) {
            case 1:
                orderBy = UserOrderBy.EmailAsc;
                break;
            case 2:
                orderBy = UserOrderBy.EmailDesc;
                break;
            case 3:
                orderBy = UserOrderBy.InternalIdAsc;
                break;
            case 4:
                orderBy = UserOrderBy.InternalIdDesc;
                break;
            case 5:
                orderBy = UserOrderBy.NameAsc;
                break;
            case 6:
                orderBy = UserOrderBy.NameDesc;
                break;
            case 7:
                orderBy = UserOrderBy.TypeAsc;
                break;
            case 8:
                orderBy = UserOrderBy.TypeDesc;
                break;
        }
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.pageId = 1;
        this.orderBy = UserOrderBy.InternalIdAsc;
        this.search = search;
    }

    public List<UserDTO> getUsers() {
        AdminUserFindOptions options = new AdminUserFindOptions(pageId, pageSize, orderBy, search);
        List<UserDTO> list = userBean.find(options);
        this.count = options.count;
        this.pagesCount = (int) Math.ceil((double) count / (double) pageSize);
        return list;
    }

}
