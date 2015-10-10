/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import beans.UserBean.UserOrderBy;
import dtos.UserDTO;
import java.util.List;

/**
 *
 * @author Joel
 */
public class UserIndexModel {
    public int pageId;    
    public final int pageSize = 20;
    public UserOrderBy orderBy;
    public List<UserDTO> users;

    public UserIndexModel() {
        this.pageId = 1;
        this.orderBy = UserOrderBy.InternalIdAsc;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public UserOrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(UserOrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
    
    
    
}
