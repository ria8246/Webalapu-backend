/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.rest.auth;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author iostream
 */
@XmlRootElement
public class AuthResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String token;
    private Integer code;
    private String error;
    
    public AuthResponse() {
        
    }
    
    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse(Integer code, String error) {
        this.code = code;
        this.error = error;
    }
    
    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
