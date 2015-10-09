/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.User;
import java.util.Date;
import utilities.Security;

/**
 *
 * @author joeld
 */
public class UserDTO extends AbstractDTO<User, UserDTO> {

    private Integer idUser;
    private String internalId;
    private String name;
    private String email;
    private String password;
    private String newPassword;
    private String photo;
    private Date dateBirth;
    private UserRoleDTO userRole;

    public UserDTO() {
        this.idUser = 0;
        this.internalId = "";
        this.name = "";
        this.email = "";
        this.password = "";
        this.photo = "";
        this.dateBirth = new Date();
        this.userRole = new UserRoleDTO();
    }

    public UserDTO(String internalId, String name, String email, String password, String photo, Date dateBirth, UserRoleDTO userRole) {
        this.idUser = 0;
        this.internalId = internalId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.dateBirth = dateBirth;
        this.userRole = userRole;
    }

    public UserDTO(Integer idUser, String internalId, String name, String email, String password, String photo, Date dateBirth, UserRoleDTO userRole) {
        this.idUser = idUser;
        this.internalId = internalId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.dateBirth = dateBirth;
        this.userRole = userRole;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public UserRoleDTO getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleDTO userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean isNew() {
        return idUser == 0;
    }

}
