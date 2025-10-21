/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.servis;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import njt.mavenproject1.dto.impl.OrderDto;
import njt.mavenproject1.dto.impl.OrderItemDto;
import njt.mavenproject1.entity.impl.Flower;
import njt.mavenproject1.entity.impl.Order;
import njt.mavenproject1.entity.impl.OrderItem;
import njt.mavenproject1.entity.impl.OrderStatus;
import njt.mavenproject1.entity.impl.User;
import njt.mavenproject1.mapper.impl.OrderMapper;
import njt.mavenproject1.repository.impl.OrderRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author Korisnik
 */
@Service
public class OrderServis {
    private final OrderRepository orders;
    private final OrderMapper mapper;
    
    @PersistenceContext
    private EntityManager em;

    public OrderServis(OrderRepository orders, OrderMapper mapper) {
        this.orders = orders;
        this.mapper = mapper;
    }
    
    public List<OrderDto> findAll(){
        return orders.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
    
    public OrderDto findbyId(Long id) throws Exception{
        return mapper.toDto(orders.findById(id));
    }
    
    @Transactional
    public OrderDto create(OrderDto dto) throws Exception {
       try{
            //pravimo narudzbinu, entity, jer je servis
        Order order = new Order();
        order.setStatus(dto.getStatus() != null ? dto.getStatus() : OrderStatus.CREATED);
        order.setNapomena(dto.getNapomena());
        
        //povezujemo korisnika
        if(dto.getUserId() == null){
            throw new Exception("UserId is required");
        }
        
        order.setUser(em.find(User.class, dto.getUserId()));
        
        //pravimo stavke
        if(dto.getItems() == null || dto.getItems().isEmpty()){
            throw new Exception("Order must contain at least one item");
        }
        
        // Izmenjen deo u OrderServis.java

        for(OrderItemDto it : dto.getItems()){
            OrderItem oi = new OrderItem();

    // Promena: Koristi se em.find i eksplicitna provera
            Flower f = em.find(Flower.class, it.getFlowerId());

            if(f == null) {
                throw new Exception("Flower with ID " + it.getFlowerId() + " not found.");
            }
    
            oi.setFlower(f);
            oi.setQuantity(it.getQuantity());
    
    // Sada je sigurno pristupiti f.getPrice()
            double price = it.getUnitPrice()>0 ? it.getUnitPrice() : f.getPrice();
            oi.setUnitPrice(price);
    
            order.addItem(oi);
        }
        
        //sacuvaj, zbog cascade all sve se cuvaju
        orders.save(order);
        
        //vracamo dto jer ide nazad na klijenta
        return mapper.toDto(order);
       }catch(Exception e){
           // --- KLJUČNO: ŠTAMPANJE GREŠKE U KONZOLU ---
        System.err.println("\n--- DETALJAN ISPIS GREŠKE U OrderServis.create() ---");
        e.printStackTrace(); // OVO SIGURNO ISPISUJE GRESKU
        System.err.println("------------------------------------------------------\n");
        
        // Ponovno bacanje izuzetka je neophodno da bi se Springova transakcija poništila (rollback)
        throw e;
       }
    }

    @Transactional
    public OrderDto updateStatus(Long id, OrderStatus status) throws Exception{
        Order o = orders.findById(id);
        o.setStatus(status);
        orders.save(o);
        return mapper.toDto(o);
        //kada korisnik naruci created, kada admin prihvati confirmed, kada isporucimo completed
    }

    @Transactional
    public void deteleById(Long id) {
        orders.deleteById(id); //brise i narudzbinu i stavke
    }
}
