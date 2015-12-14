/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.core;

import java.io.Serializable;

/**
 *
 * @author Joel
 */
public abstract class AbstractDTO implements Serializable {

    protected boolean New;
    
    public abstract Object getRelationalId();

    public AbstractDTO(AbstractEntity entity) {
        this.New = (entity == null || entity.isNew());
    }

    public boolean isNew() {
        return New;
    }
}
