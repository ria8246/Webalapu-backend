/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.entity;

import com.sun.istack.logging.Logger;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.Basic;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    private String name;
    private String password;
    private String salt;
    private String address;
    private Boolean newsletter;
    private Boolean admin;
    private String token;
    
    private Collection<Order> orders;

    public Customer() {
        this.orders = new ArrayList<>();
    }

    public Customer(String email, String name, String password, String address, Boolean newsletter, Boolean admin) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.newsletter = newsletter;
        this.admin = admin;
        this.orders = new ArrayList<>();
        
        setPassword(password, true);
    } 
    
    public Customer(String email, String name, String password, String address, Boolean newsletter) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.newsletter = newsletter;
        this.admin = false;
        this.orders = new ArrayList<>();
        
        setPassword(password, true);
    }
    
    @Id
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic(fetch=FetchType.LAZY)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password, Boolean hash) {
        hash = (hash == null) ? false : hash;
        if (hash) {
            try {
                byte[] salt = new byte[32];
                new SecureRandom().nextBytes(salt);

                SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
                PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 32, 256);

                Base64.Encoder enc = Base64.getEncoder();
                this.password = enc.encodeToString(skf.generateSecret(spec).getEncoded());
                this.salt = enc.encodeToString(salt);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                this.password = password;
            } 
        } else {
            this.password = password;
        }
    }
    
    public void setPassword(String password) {
        setPassword(password, Boolean.FALSE);
    }
    
    public Boolean checkPassword(String password) {
        try {
            byte[] salt = Base64.getDecoder().decode(this.salt);

            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 32, 256);
            SecretKey key = skf.generateSecret(spec);
            
            if (Arrays.equals(key.getEncoded(), Base64.getDecoder().decode(this.password))) {
                return Boolean.TRUE;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            
        } 
        return Boolean.FALSE;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
