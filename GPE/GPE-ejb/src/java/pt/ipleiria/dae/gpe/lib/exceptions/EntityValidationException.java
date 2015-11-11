/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.exceptions;

import java.util.ArrayList;
import java.util.List;
import pt.ipleiria.dae.gpe.lib.core.EntityValidationError;

/**
 *
 * @author joeld
 */
public class EntityValidationException extends Exception{
    private final List<EntityValidationError> entityValidationErrors;

    public EntityValidationException() {
        this.entityValidationErrors = new ArrayList<>();
    }

    public EntityValidationException(List<EntityValidationError> entityValidationErrors) {
        this.entityValidationErrors = entityValidationErrors;
    }

    public List<EntityValidationError> getEntityValidationErrors() {
        return this.entityValidationErrors;
    }

    public void addEntityValidationError(EntityValidationError entityValidationError) {
        this.entityValidationErrors.add(entityValidationError);
    }
    
    
    
}
