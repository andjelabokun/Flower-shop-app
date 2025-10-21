/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.dto.impl;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import njt.mavenproject1.dto.Dto;

/**
 *
 * @author Korisnik
 */
public class OrderItemDto implements Dto{
    //sta vracamo korisniku
    private Long id;
    
    @NotNull(message = "Flower ID is required.")
    private Long flowerId;
    
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
    
    //cena u trenutku cuvanja
    private double unitPrice;

    public OrderItemDto() {
    }

    public OrderItemDto(Long id, Long flowerId, int quantity, double unitPrice) {
        this.id = id;
        this.flowerId = flowerId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(Long productId) {
        this.flowerId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    
}
