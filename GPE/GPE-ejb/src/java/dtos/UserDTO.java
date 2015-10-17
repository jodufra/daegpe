/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import utilities.Security;

/**
 *
 * @author joeld
 */
public class UserDTO extends AbstractDTO {

    protected Integer idUser;
    protected String internalId;
    protected String name;
    protected String email;
    protected final String password;
    protected String newPassword;

    public UserDTO() {
        this.idUser = 0;
        this.internalId = "";
        this.name = "";
        this.email = "";
        this.password = "";
    }

    public UserDTO(String internalId, String name, String email, String password) {
        this.idUser = 0;
        this.internalId = internalId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.newPassword = password;
    }

    public UserDTO(Integer idUser, String internalId, String name, String email, String password) {
        this.idUser = idUser;
        this.internalId = internalId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.newPassword = password;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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

    public boolean isPasswordEqual(String password) {
        return this.password.equals(Security.GetMD5Hash(newPassword));
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = Security.GetMD5Hash(newPassword);
    }

    public boolean hasNewPassword() {
        return !password.equals(newPassword);
    }

    @Override
    public boolean isNew() {
        return idUser == 0;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "idUser=" + idUser + ", internalId=" + internalId + ", name=" + name + ", email=" + email + ", password=" + password + ", newPassword=" + newPassword + '}';
    }
    
}
