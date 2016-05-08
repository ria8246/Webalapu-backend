/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.rest.api;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
@Path("order")
public class OrderFacadeREST extends AbstractFacade<Order> {

    @PersistenceContext(unitName = "BackendPU")
    private EntityManager em;
    
    @Context
    private SecurityContext securityContext;

    public OrderFacadeREST() {
        super(Order.class);
    }

    @Secured
    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Order entity, @Context SecurityContext securityContext) {
        super.getCustomer(securityContext);
        
        super.create(entity);
    }

    @Secured
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Order entity, @Context SecurityContext securityContext) {
        super.checkAdmin(super.getCustomer(securityContext));
        
        super.edit(entity);
    }

    @Secured
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id, @Context SecurityContext securityContext) {
        super.checkAdmin(super.getCustomer(securityContext));
        
        super.remove(super.find(id));
    }

    @Secured
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Order find(@PathParam("id") Long id, @Context SecurityContext securityContext) {
        Customer authed = super.getCustomer(securityContext);
        Order order = em.find(Order.class, id);
        Customer customer = order.getCustomer();
        
        super.checkPermission(authed, customer);
        
        return order;
    }

    @Secured
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<Order> findAll(@Context SecurityContext securityContext) {
        Customer authed = super.getCustomer(securityContext);
        return (List<Order>) authed.getOrders();
    }
    
    @Secured
    @GET
    @Path("findAll")
    @Produces({MediaType.APPLICATION_XML})
    public List<Order> findAllAdmin(@Context SecurityContext securityContext) {
        super.checkAdmin(super.getCustomer(securityContext));
        
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
