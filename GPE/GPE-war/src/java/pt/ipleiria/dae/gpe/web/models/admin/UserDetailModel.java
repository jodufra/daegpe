/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models.admin;

import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.entities.GROUP;

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
    private GROUP type;

    public UserDetailModel() {
    }

    public String getTitle() {
        return isNew ? "Adicionar Utilizador" : name;
    }

    public void setUser(UserDTO user) {
        if (user != null && !user.isNew()) {
            this.idUser = user.getIdUser();
            this.internalId = user.getInternalId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.newPassword = "";
            this.isNew = user.isNew();
            this.type = user.getGroup();
        } else {
            this.idUser = 0;
            this.internalId = "";
            this.name = "";
            this.email = "";
            this.newPassword = "";
            this.isNew = true;
            this.type = GROUP.Student;
        }
    }

    public UserDTO provideUserDTO() {
        UserDTO user = new UserDTO(idUser, type, internalId, name, email);
        if (!newPassword.isEmpty()) {
            user.setNewPassword(newPassword);
        }
        return user;
    }

    public GROUP[] getUserTypes() {
        return GROUP.values();
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public GROUP getType() {
        return type;
    }

    public void setType(GROUP type) {
        this.type = type;
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
