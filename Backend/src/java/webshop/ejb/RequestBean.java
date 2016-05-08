/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import webshop.entity.Customer;
import webshop.entity.Order;
import webshop.entity.Product;

/**
 *
 * @author iostream
 */
@Stateful
public class RequestBean {
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private static final Logger log = Logger.getLogger(RequestBean.class.getCanonicalName());
    
    @PersistenceContext(unitName = "BackendPU")
    private EntityManager em;
    
    public void createCustomer(String email, String name, String password, String address, Boolean newsletter, Boolean admin) {
        try {
            Customer customer = em.find(Customer.class, email);
            if (customer == null) {
                customer = new Customer(email, name, password, address, newsletter, admin);
                em.persist(customer);
                log.log(Level.INFO, "Customer " + customer.toString() + " persisted.");
            } else {
                log.log(Level.INFO, "Customer " + customer.toString() + " already exists.");
            }
            
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public void validateDefaults() {
        try {
            Customer customer = em.find(Customer.class, "admin@webshop.com");
            if (customer == null) {
                customer = new Customer("admin@webshop.com", "Admin", "admin", "Omicron Persei 8", Boolean.FALSE, Boolean.TRUE);
                em.persist(customer);
                log.log(Level.INFO, "Customer " + customer.toString() + " persisted.");
            } else {
                log.log(Level.INFO, "Customer " + customer.toString() + " already exists.");
            }
            
            Product product = em.find(Product.class, 1L);
            if (product == null) {
                product = new Product(1L, "Test product", 1990, "Test description", 10);
                em.persist(product);
                log.log(Level.INFO, "Product " + product.toString() + " persisted.");
            } else {
                log.log(Level.INFO, "Product " + product.toString() + " already exists.");
            }

            Order order = new Order(customer);
            order.addProduct(product, 2);
            
            em.persist(order);
            log.log(Level.INFO, "Order created.");
            
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
}
