/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joeld
 */
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class GPEResponse<T>  implements Serializable{
    public static final String Success = "success";
    public static final String Error = "error";
    
    private String status;
    private T response;

    public GPEResponse() {
    }
   
    public GPEResponse(String status, T message) {
        this.status = status;
        this.response = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getMessage() {
        return response;
    }

    public void setMessage(T message) {
        this.response = message;
    }
    
        
}
