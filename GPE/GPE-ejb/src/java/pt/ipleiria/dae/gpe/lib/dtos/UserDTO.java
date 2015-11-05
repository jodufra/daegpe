/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.dtos;

import pt.ipleiria.dae.gpe.lib.core.AbstractDTO;
import pt.ipleiria.dae.gpe.lib.entities.User;
import pt.ipleiria.dae.gpe.lib.entities.UserType;
import pt.ipleiria.dae.gpe.lib.utilities.Security;

/**
 *
 * @author joeld
 */
public class UserDTO extends AbstractDTO {

    public static boolean IsAdministrator(UserDTO user){
        return (user instanceof AdministratorDTO);
    }
    public static boolean IsManager(UserDTO user){
        return (user instanceof ManagerDTO);
    }
    public static boolean IsStudent(UserDTO user){
        return (user instanceof StudentDTO);
    }
    
    protected Integer idUser;
    protected UserType type;
    protected String internalId;
    protected String name;
    protected String email;
    protected final String password;
    protected String newPassword;

    public UserDTO(Integer idUser, UserType type, String internalId, String name, String email) {
        super(null);
        this.idUser = idUser;
        this.type = type;
        this.internalId = internalId;
        this.name = name;
        this.email = email;
        this.password = "";
        this.newPassword = "";
        this.New = idUser == 0;
    }

    public UserDTO(UserType type, String internalId, String name, String email, String newPassword) {
        super(null);
        this.idUser = 0;
        this.type = type;
        this.internalId = internalId;
        this.name = name;
        this.email = email;
        this.password = "";
        setNewPassword(newPassword);
    }

    public UserDTO(User user) {
        super(user);
        this.idUser = user.getIdUser();
        this.type = user.getType();
        this.internalId = user.getInternalId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.newPassword = "";
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public UserType getType() {
        return type;
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

    public final void setNewPassword(String newPassword) {
        this.newPassword = Security.GenerateMD5Hash(newPassword);
    }

    public boolean hasNewPassword() {
        return !password.equals(newPassword);
    }

    @Override
    public Object getRelationalId() {
        return getIdUser();
    }
    
}
