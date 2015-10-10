/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.AbstractEntity;
import java.io.Serializable;

/**
 *
 * @author Joel
 * @param <Entity>
 * @param <DTO>
 */
public abstract class AbstractDTO<Entity extends AbstractEntity, DTO extends AbstractDTO> implements Serializable{
    public abstract boolean isNew();
}
