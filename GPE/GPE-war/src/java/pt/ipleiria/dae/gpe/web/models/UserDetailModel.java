/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models;

import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;

/**
 *
 * @author joeld
 */
public class UserDetailModel {

    private int idUser;
    private String internalId;
    private String name;
    private String email;
    private String newPassword;
    private boolean isNew;

    public void setUser(UserDTO user) {
        if (user != null && !user.isNew()) {
            this.idUser = user.getIdUser();
            this.internalId = user.getInternalId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.newPassword = "";
            this.isNew = user.isNew();
        } else {
            this.idUser = 0;
            this.internalId = "";
            this.name = "";
            this.email = "";
            this.newPassword = "";
            this.isNew = true;
        }
    }

    public String title() {
        return isNew ? "Adicionar Utilizador" : name;
    }

    public UserDTO save() {
        UserDTO user = new UserDTO(idUser, internalId, name, email);
        if (!newPassword.isEmpty()) {
            user.setNewPassword(newPassword);
        }

        return user;
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

    public boolean isIsNew() {
        return isNew;
    }

}
