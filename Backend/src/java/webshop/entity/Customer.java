/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author iostream
 */
@Entity
@Table(name = "CUSTOMER_TABLE")
@XmlRootElement
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    private String email;

    @Id
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    private String name;
    private String password;
    private String address;
    private Boolean newsletter;
    private Boolean admin;
    
    private Collection<Order> orders;

    public Customer() {
        this.orders = new ArrayList<>();
    }

    public Customer(String email, String name, String password, String address, Boolean newsletter, Boolean admin) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.address = address;
        this.newsletter = newsletter;
        this.admin = admin;
        this.orders = new ArrayList<>();
    } 
    
    public Customer(String email, String name, String password, String address, Boolean newsletter) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.address = address;
        this.newsletter = newsletter;
        this.admin = false;
        this.orders = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(Boolean newsletter) {
        this.newsletter = newsletter;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @OneToMany(cascade = ALL, mappedBy = "customer")
    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "webshop.entity.Customer[ id=" + email + " ]";
    }
    
}
