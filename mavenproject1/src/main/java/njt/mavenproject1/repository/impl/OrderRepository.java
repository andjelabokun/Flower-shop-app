/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import njt.mavenproject1.entity.impl.Order;
import njt.mavenproject1.repository.MyAppRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Korisnik
 */
@Repository
public class OrderRepository implements MyAppRepository<Order, Long>{
    //jedan repository za narudzbinu i stavke narudzbine
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Order> findAll() {
        return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    }

    @Override
    public Order findById(Long id) throws Exception {
        Order o = em.find(Order.class, id);
        if (o == null) throw new Exception("Order not found"+id);
        return o;
    }

    @Override
    @Transactional
    public void save(Order entity) {
        if (entity.getId() == null) em.persist(entity);
        else em.merge(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Order o = em.find(Order.class, id);
        if(o != null) em.remove(o); //posto kod order entity klase smo stavilo orphan entity removal i cascae znaci obrisace i sve stavke koje su povezane za id ordera
    }
    
}
