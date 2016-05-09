/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.rest.api;

import java.util.List;
import java.util.logging.Logger;
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
import webshop.entity.Product;
import webshop.rest.auth.Secured;

/**
 *
 * @author iostream
 */
@Stateless
@Path("product")
public class ProductFacadeREST extends AbstractFacade<Product> {

    @PersistenceContext(unitName = "BackendPU")
    private EntityManager em;

    @Context
    private SecurityContext securityContext;
    
    public ProductFacadeREST() {
        super(Product.class);
    }

    @Secured
    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Product entity, @Context SecurityContext securityContext) {
        Customer authed = super.getCustomer(securityContext);
        super.checkAdmin(authed);
        
        super.create(entity);
    }

    @Secured
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Product entity, @Context SecurityContext securityContext) {
        Customer authed = super.getCustomer(securityContext);
        super.checkAdmin(authed);
        
        super.edit(entity);
    }

    @Secured
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id, @Context SecurityContext securityContext) {
        Customer authed = super.getCustomer(securityContext);
        super.checkAdmin(authed);
        
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Product find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Product> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Product> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
