/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models;

import pt.ipleiria.dae.gpe.lib.entities.UserType;

/**
 *
 * @author joeld
 */
public class UserTypeDescription {
    private final UserType type;
    private final String label;

    public UserTypeDescription(UserType type, String label) {
        this.type = type;
        this.label = label;
    }

    public UserType getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }
    
    
}
