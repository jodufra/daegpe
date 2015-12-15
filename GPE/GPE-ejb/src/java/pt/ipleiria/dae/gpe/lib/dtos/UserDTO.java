/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import pt.ipleiria.dae.gpe.lib.core.AbstractDTO;
import pt.ipleiria.dae.gpe.lib.entities.User;
import pt.ipleiria.dae.gpe.lib.entities.GROUP;
import pt.ipleiria.dae.gpe.lib.utilities.Security;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDTO extends AbstractDTO {

    public static boolean IsAdministrator(UserDTO user) {
        return (user instanceof AdministratorDTO || user.group == GROUP.Administrator);
    }

    public static boolean IsManager(UserDTO user) {
        return (user instanceof ManagerDTO || user.group == GROUP.Manager);
    }

    public static boolean IsStudent(UserDTO user) {
        return (user instanceof StudentDTO || user.group == GROUP.Student);
    }

    protected Integer idUser;
    protected GROUP group;
    protected String internalId;
    protected String name;
    protected String email;
    protected final String password;
    protected String newPassword;

    public UserDTO(Integer idUser, GROUP type, String internalId, String name, String email) {
        super(null);
        this.idUser = idUser;
        this.group = type;
        this.internalId = internalId;
        this.name = name;
        this.email = email;
        this.password = "";
        this.newPassword = "";
        this.New = idUser == 0;
    }

    public UserDTO(GROUP type, String internalId, String name, String email, String newPassword) {
        super(null);
        this.idUser = 0;
        this.group = type;
        this.internalId = internalId;
        this.name = name;
        this.email = email;
        this.password = "";
        setNewPassword(newPassword);
    }

    public UserDTO(User user) {
        super(user);
        this.idUser = user.getIdUser();
        this.group = user.getUserGroup().getGroupName();
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

    public GROUP getGroup() {
        return group;
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
        this.newPassword = Security.GenerateSHA256Hash(newPassword);
    }

    public boolean hasNewPassword() {
        return !password.equals(newPassword);
    }

    @Override
    public Object getRelationalId() {
        return getIdUser();
    }

}
