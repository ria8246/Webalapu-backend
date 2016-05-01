/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author iostream
 */
@Entity
@Table(name = "ORDER_TABLE")
@XmlRootElement
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    
    private Customer customer;
    private Map<Product, Integer> products;
    private Long totalPrice;

    public Order() {
        this.products = new HashMap<>();
        this.totalPrice = 0L;
    }

    public Order(Customer customer) {
        this.customer = customer;
        this.totalPrice = 0L;
    }

    public Order(Customer customer, Map<Product, Integer> products) {
        this.customer = customer;
        this.products = products;
        this.totalPrice = 0L;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    public Map<Product, Integer> getProducts() {
        return products;
    }
    
    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }
    
    public Boolean addProduct(Product product, Integer quantity) {
        if (this.products == null) {
            this.products = new HashMap<>();
        }
        
        Integer stock = product.getStock();
        stock -= quantity;
        
        if (stock < 0) {
            return false;
        }
        
        this.products.put(product, quantity);
        this.totalPrice += product.getPrice() * quantity;
        
        product.setStock(stock);
        
        return true;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order)) {
            return false;
        }
        Order other = (Order) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "webshop.entity.Order[ id=" + id + " ]";
    }
    
}
