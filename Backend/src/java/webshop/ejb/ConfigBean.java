/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author iostream
 */
@Singleton
@Startup
public class ConfigBean {
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @EJB
    private RequestBean request;
    
    @PostConstruct
    public void createData() {
        request.validateDefaults();
    }
    
    @PreDestroy
    public void deleteData() {
        
    }
}
