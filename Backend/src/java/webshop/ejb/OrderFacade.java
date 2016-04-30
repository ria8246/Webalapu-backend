/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import webshop.entity.Order;

/**
 *
 * @author iostream
 */
@Stateless
public class OrderFacade extends AbstractFacade<Order> {

    @PersistenceContext(unitName = "BackendPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderFacade() {
        super(Order.class);
    }
    
}
