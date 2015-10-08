/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.UserRole;

/**
 *
 * @author joeld
 */
public class UserRoleDTO extends AbstractDTO<UserRole,UserRoleDTO> {
    
    
    private Integer idUserRole;
    private String name;

    public UserRoleDTO() {
        this.idUserRole = 0;
        this.name = "";
    }

    public UserRoleDTO(String name) {
        this.idUserRole = 0;
        this.name = name;
    }

    public UserRoleDTO(Integer idUserRole, String name) {
        this.idUserRole = idUserRole;
        this.name = name;
    }

    public Integer getIdUserRole() {
        return idUserRole;
    }

    public void setIdUserRole(Integer idUserRole) {
        this.idUserRole = idUserRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean isNew() {
        return idUserRole == 0;
    }
}
