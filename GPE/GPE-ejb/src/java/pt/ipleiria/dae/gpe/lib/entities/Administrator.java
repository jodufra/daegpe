/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.entities;

import javax.persistence.Entity;

/**
 *
 * @author joeld
 */
@Entity
public class Administrator extends User{

    public Administrator() {
        super();
        this.type = UserType.Administrator;
    }

    public Administrator(String internalId, String name, String email, String password) {
        super(internalId, name, email, password);
        this.type = UserType.Administrator;
    }

    public Administrator(Integer idUser, String internalId, String name, String email, String password, String search) {
        super(idUser, internalId, name, email, password, search);
        this.type = UserType.Administrator;
    }
    
}
