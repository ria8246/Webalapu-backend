/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.ejb;

import java.util.HashMap;
import java.util.Map;
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
        
    public void validateDefaults() {
        try {
            persistCustomer("admin@webshop.com", "Admin", "admin", "Omicron Persei 8", Boolean.FALSE, Boolean.TRUE);
            Customer customer = persistCustomer("user@webshop.com", "User", "user", "Omicron Persei 8", Boolean.FALSE, Boolean.TRUE);

            persistProduct("Aigisz", 2000000, "Győzelemre segít e pajzs, ha Athene is úgy akarja. Szoborkert készítésére is alkalmas.", "images/1.jpg", 1);
            Product product = persistProduct("Álomdoboz", 599999, "Álmatlanságban szenved? E ládika segíthet. Utána felkelni nehézkes.", "images/2.jpg", 5);
            persistProduct("Aphrodité öve", 1499000, "Legyen ön bármíly csúnya, megszépül ha ezt hordja.", "images/3.jpg", 2);
            persistProduct("Apollón íja", 1200000, "Ha vadász szenvedély hajtja, de tehetségnek híja. Ez íjjal kilőtt vessző mellé sosem lő.", "images/4.jpg", 2);
            persistProduct("Hádész sisakja", 2999999, "Láthatatlanná válna ön is néha? Erre a süvegre van szüksége! Általa kínos helyzetből könnyedén kikerülhet.", "images/6.jpg", 1);
            persistProduct("Erósz aranyhegyű nyilai", 499999, "Ha szerelem fűti de viszonzását nem leli. Erosz arany nyílvesszője segítséget nyújthat.", "images/7.jpg", 27);
            persistProduct("Erósz ólomhegyű nyilai", 499999, "Erosz isten áldásával a legkitartóbb hódolójától is megszabadulhat. Vigyázat, hatása visszavonhatatlan.", "images/8.jpg", 31);
            persistProduct("Héphaisztosz csapdája", 999999, "Ha úgy véli párja csalfa, e hálóval őket elkaphatja.", "images/9.jpg", 3);
            persistProduct("Aranyalma", 999999, "Viszály szításra kitűnő. Egyenesen Erisz istennőtől (aki Hérától lopta, de ez lényegtelen). Felirat igény szerint.", "images/10.jpg", 7);
            persistProduct("Héphaisztosz trónja", 1199999, "Bosszúra vágyék, bár nem gyilkolásra? E trónussal bárkit csapdába csalhat, s elengedni csak ön tudja.", "images/11.jpg", 5);
            persistProduct("Héra zárja", 1599999, "Bitonsága kérdéses, s nem bízik senkiben? E zár távoltart bárkit, tulaját kivéve.", "images/12.jpg", 3);
            persistProduct("Hermész saruja", 699999, "Ha úgy szeretne szálni, mint már járni is tud, e saruva eget szelheti. Légiforgalommal tessék vigyázni!", "images/13.jpg", 12);
            persistProduct("Bőségszaru", 799999, "Étekből általa sosem lesz hiánya!", "images/14.jpg", 10);
            persistProduct("Médeia kenõcse", 299999, "Ha tűzbe készül menni, életét ez megmenti. Tűzoltóknak ideális.", "images/15.jpg", 24);
            persistProduct("Poszeidón szigonya", 1399999, "Ha nyugodt útra vágyik tengernek vizén, vagy vihart kavarni ellenség vesztére, e szigony alkalmas mindkettőre.", "images/16.jpg", 1);

            Map<Product, Integer> products = new HashMap<>();
            products.put(product, 1);
            persistOrder(customer, products);

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    private Customer persistCustomer(String email, String name, String password, String address, Boolean newsletter, Boolean admin) {
        try {
            Customer customer = em.find(Customer.class, email);
            if (customer == null) {
                customer = new Customer(email, name, password, address, newsletter, admin);
                em.persist(customer);
                log.log(Level.INFO, "Customer " + customer.toString() + " persisted.");
            } else {
                log.log(Level.INFO, "Customer " + customer.toString() + " already exists.");
            }
            return customer;
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    private Product persistProduct(String name, Integer price, String description, String image, Integer stock) {
        Product product = new Product(name, price, description, image, stock);
        em.persist(product);
        log.log(Level.INFO, "Product " + product.toString() + " persisted.");
        
        return product;
    }
    
    private Order persistOrder(Customer customer, Map<Product, Integer> products) {
        Order order = new Order(customer);
        
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            order.addProduct(entry.getKey(), entry.getValue());
        }

        em.persist(order);
        log.log(Level.INFO, "Order created.");
        
        return order;
    }
}
