/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.exceptions;

/**
 *
 * @author joeld
 */
public class EntityIsNullException extends Exception{

    public EntityIsNullException() {
    }

    public EntityIsNullException(String message) {
        super(message);
    }
    
}
