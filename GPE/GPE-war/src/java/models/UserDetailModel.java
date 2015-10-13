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
    private String newPassword;
    private UserRoleDTO userRole;

    public String generateTitle() {
        return user == null || user.isNew() ? "Adicionar Utilizador" : user.getName();
    }

    public UserDTO save() {
        user.setInternalId(internalId);
        user.setName(name);
        user.setEmail(email);
        if (user.isNew() || !newPassword.isEmpty()) {
            user.setNewPassword(newPassword);
        }
        if (userRole != null) {
            user.setUserRole(userRole);
        }
        return user;
    }

    public UserDTO getUser() {
        if (user == null) {
            setUser(new UserDTO());
        }
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
        if (user == null) {
            return;
        }
        this.internalId = user.getInternalId();
        this.name = user.getName();
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public UserRoleDTO getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleDTO userRole) {
        this.userRole = userRole;
    }

}
