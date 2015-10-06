/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author joeld
 */
public abstract class AbstractBean<T,I> {
    
    public abstract I Transform (T object);
    
}
