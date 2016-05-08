/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.rest.api;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import webshop.entity.Customer;
import webshop.entity.Order;
import webshop.rest.auth.Secured;

/**
 *
 * @author iostream
 */
@Stateless
@Path("customer")
public class CustomerFacadeREST extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "BackendPU")
    private EntityManager em;
    
    @Context
    private SecurityContext securityContext;

    public CustomerFacadeREST() {
        super(Customer.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Customer entity) {
        super.create(entity);
    }

    @Secured
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") String id, Customer entity, @Context SecurityContext securityContext) {
        Customer authed = super.getCustomer(securityContext);
        super.checkPermission(authed, entity);
        
        Customer customer = em.find(Customer.class, id);
        String address = entity.getAddress();
        Boolean admin = entity.getAdmin();
        String name = entity.getName();
        Boolean newsletter = entity.getNewsletter();
        
        if (address != null) {
            customer.setAddress(address);
        }
        
        if (admin != null && authed.getAdmin()) {
            customer.setAdmin(admin);
        }
        
        if (name != null) {
            customer.setName(name);
        }
        
        if (newsletter != null) {
            customer.setNewsletter(newsletter);
        }
        
        super.edit(customer);
    }

    @Secured
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id, @Context SecurityContext securityContext) {
        Customer authed = super.getCustomer(securityContext);
        Customer customer = em.find(Customer.class, id);
        
        super.checkPermission(authed, customer);
        
        Collection<Order> orders = customer.getOrders();
        
        for (Order order : orders) {
            em.remove(order);
        }
        
        super.remove(super.find(id));
    }

    @Secured
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Customer find(@PathParam("id") String id, @Context SecurityContext securityContext) {
        Customer authed = super.getCustomer(securityContext);
        Customer customer = em.find(Customer.class, id);
        super.checkPermission(authed, customer);
        
        return super.find(id);
    }

    @Secured
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> findAll(@Context SecurityContext securityContext) {
        Customer authed = super.getCustomer(securityContext);
        super.checkAdmin(authed);
        
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
