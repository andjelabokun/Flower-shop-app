/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.mapper.impl;

import njt.mavenproject1.dto.impl.OrderItemDto;
import njt.mavenproject1.entity.impl.Flower;
import njt.mavenproject1.entity.impl.OrderItem;
import njt.mavenproject1.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Korisnik
 */
@Component
public class OrderItemMapper implements DtoEntityMapper<OrderItemDto, OrderItem>{

    @Override
    public OrderItemDto toDto(OrderItem e) {
        return new OrderItemDto(e.getId(), e.getFlower().getId(), e.getQuantity(), e.getUnitPrice());
    }

    @Override
    public OrderItem toEntity(OrderItemDto t) {
        OrderItem oi = new OrderItem();
        oi.setId(t.getId());
        oi.setFlower(new Flower(t.getFlowerId()));
        oi.setQuantity(t.getQuantity());
        oi.setUnitPrice(t.getUnitPrice());
       return oi;
        
        
    }
    
}
