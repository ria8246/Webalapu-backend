/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.rest.auth;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import webshop.entity.Customer;

/**
 *
 * @author iostream
 */
@Stateless
@Path("/authentication")
public class AuthenticationEndpoint {
    @PersistenceContext(unitName = "BackendPU")
    private EntityManager em;
    
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public AuthResponse authenticateUser(Credentials credentials) {

        try {
            authenticate(credentials.getEmail(), credentials.getPassword());
            String token = issueToken(credentials.getEmail());

            // Return the token on the response
            //return Response.ok(token).type(MediaType.APPLICATION_XML).entity(token).build();
            return new AuthResponse(token);

        } catch (Exception ignored) {
            //ignored.printStackTrace();
            return new AuthResponse(Response.Status.UNAUTHORIZED.getStatusCode(), Response.Status.UNAUTHORIZED.toString());
        }      
    }

    private void authenticate(String email, String password) throws Exception {
        Customer customer = em.find(Customer.class, email);
        if (!customer.checkPassword(password)) {
            throw new Exception();
        }
    }

    private String issueToken(String email) {
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = cb.createQuery();

        Root<Customer> from = criteriaQuery.from(Customer.class);        
        CriteriaQuery<Object> select = criteriaQuery.select(from);

        String token;
        Integer count;
        do {
            token = UUID.randomUUID().toString().replaceAll("-", "");
            select.where(cb.equal(from.get("token"), token));
            TypedQuery<Object> typedQuery = em.createQuery(select);
            count = typedQuery.getResultList().size();
        } while (count > 0);

        Customer customer = em.find(Customer.class, email);
        customer.setToken(token);
        em.merge(customer);
        
        return token;
    }
}
