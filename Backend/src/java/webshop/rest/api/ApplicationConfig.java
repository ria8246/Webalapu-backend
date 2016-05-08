/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.rest.api;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author iostream
 */
@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(webshop.rest.api.CustomerFacadeREST.class);
        resources.add(webshop.rest.api.OrderFacadeREST.class);
        resources.add(webshop.rest.api.ProductFacadeREST.class);
        resources.add(webshop.rest.auth.AuthenticationEndpoint.class);
        resources.add(webshop.rest.auth.AuthenticationFilter.class);
    }
    
}
