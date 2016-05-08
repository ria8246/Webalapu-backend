/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.rest.auth;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import javax.annotation.Priority;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import webshop.entity.Customer;

/**
 *
 * @author iostream
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
@Stateless
public class AuthenticationFilter implements ContainerRequestFilter {

    @PersistenceContext(unitName = "BackendPU")
    private EntityManager em;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = 
            requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            String email = validateToken(token);
            
            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return new Principal() {
                        @Override
                        public String getName() {
                            return email;
                        }
                    };
                }
                
                @Override
                public boolean isUserInRole(String role) {
                    return true;
                }

                @Override
                public boolean isSecure() {
                    return false;
                }

                @Override
                public String getAuthenticationScheme() {
                    return null;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private String validateToken(String token) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = cb.createQuery();

        Root<Customer> from = criteriaQuery.from(Customer.class);        
        CriteriaQuery<Object> select = criteriaQuery.select(from);

        select.where(cb.equal(from.get("token"), token));
        TypedQuery<Object> typedQuery = em.createQuery(select);
        List<Customer> customers = (List)typedQuery.getResultList();
        
        if (customers.size() != 1) {
            throw new Exception();
        }
        
        return customers.get(0).getEmail();
    }
}
