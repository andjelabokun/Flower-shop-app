/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.dto.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import njt.mavenproject1.dto.Dto;
import njt.mavenproject1.entity.impl.OrderStatus;

/**
 *
 * @author Korisnik
 */
public class OrderDto implements Dto{
    private Long id;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private String napomena;
    
    @NotNull(message = "User ID is required.")
    private Long userId;
    
    @Valid
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemDto> items;

    public OrderDto() {
    }

    public OrderDto(Long id, OrderStatus status, LocalDateTime createdAt, String napomena, Long userId, List<OrderItemDto> items) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
        this.napomena = napomena;
        this.userId = userId;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
    
    
}
