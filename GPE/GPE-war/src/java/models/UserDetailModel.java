/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dtos.UserDTO;
import dtos.UserRoleDTO;

/**
 *
 * @author joeld
 */
public class UserDetailModel {

    private UserDTO user;

    private String internalId;
    private String name;
    private String email;
    private UserRoleDTO userRole;

    public UserDTO newUser() {
        return new UserDTO();
    }

    public UserDTO save() {
        user.setName(name);
        user.setEmail(email);
        user.setUserRole(userRole);
        return user;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
        this.internalId = user.getInternalId();
        this.email = user.getEmail();
        this.userRole = user.getUserRole();
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoleDTO getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleDTO userRole) {
        this.userRole = userRole;
    }

}
