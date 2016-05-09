/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.rest.api;

import com.sun.istack.internal.logging.Logger;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import webshop.entity.Customer;
import webshop.entity.Order;
import webshop.entity.Product;
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
        
        Order order = new Order();
        Customer customer = em.find(Customer.class, entity.getCustomer().getEmail());
        order.setCustomer(customer);
        
        for (Map.Entry<Product, Integer> entry: entity.getProducts().entrySet()) {
            Product product = em.find(Product.class, entry.getKey().getId());
            order.addProduct(product, entry.getValue());
        }
        
        if (order.getProducts().size() > 0) {
            super.create(order);
        }
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
        
        Logger.getLogger(OrderFacadeREST.class).info(authed.getEmail());
        
        //List<Order> orders = (List<Order>) authed.getOrders();
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = cb.createQuery();

        Root<Order> from = criteriaQuery.from(Order.class);        
        CriteriaQuery<Object> select = criteriaQuery.select(from);

        select.where(cb.equal(from.get("customer"), authed));
        TypedQuery<Object> typedQuery = em.createQuery(select);
        List<Order> orders = (List)typedQuery.getResultList();
        
        
        Logger.getLogger(OrderFacadeREST.class).info("" + orders.size());
        
        for (Order order : orders) {
            Customer customer = order.getCustomer();
            em.detach(customer);
            
            customer.setPassword(null);
            customer.setSalt(null);
            customer.setToken(null);
        }
        
        return orders;
    }
    
    @Secured
    @GET
    @Path("findAll")
    @Produces({MediaType.APPLICATION_XML})
    public List<Order> findAllAdmin(@Context SecurityContext securityContext) {
        super.checkAdmin(super.getCustomer(securityContext));
        
        List<Order> orders = super.findAll();
        
        for (Order order : orders) {
            Customer customer = order.getCustomer();
            em.detach(customer);
            
            customer.setPassword(null);
            customer.setSalt(null);
            customer.setToken(null);
        }
        
        return orders;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
