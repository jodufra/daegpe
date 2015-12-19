/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joeld
 */
@XmlRootElement(name = "responseList")
@XmlAccessorType(XmlAccessType.FIELD)
public class GPEResponseList<T> implements Serializable{
    
    private List<T> list;

    public GPEResponseList() {
    }

    public GPEResponseList(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
    
    
}
