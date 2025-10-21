/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.mapper.impl;

import java.util.List;
import java.util.stream.Collectors;
import njt.mavenproject1.dto.impl.OrderDto;
import njt.mavenproject1.entity.impl.Order;
import njt.mavenproject1.entity.impl.User;
import njt.mavenproject1.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Korisnik
 */
@Component
public class OrderMapper implements DtoEntityMapper<OrderDto, Order>{
    
    private final OrderItemMapper itemMapper;

    public OrderMapper(OrderItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }
    

    @Override
    public OrderDto toDto(Order e) {
        List items = e.getItems().stream().map(itemMapper::toDto).collect(Collectors.toList());
        return new OrderDto(e.getId(), e.getStatus(), e.getCreatedAt(), e.getNapomena(), e.getUser().getId(), items);
    }

    @Override
    public Order toEntity(OrderDto t) {
        Order o = new Order();
        o.setId(t.getId());
        if(t.getStatus() != null) o.setStatus(t.getStatus());
        o.setNapomena(t.getNapomena());
        
        if(t.getUserId() != null){
            o.setUser(new User(t.getUserId()));
        }
        
        if(t.getItems() != null){
            t.getItems().forEach(d -> o.addItem(itemMapper.toEntity(d)));
        }
        return o;
    }
    
}
