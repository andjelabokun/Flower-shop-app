/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.dto.impl;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import njt.mavenproject1.dto.Dto;
import njt.mavenproject1.entity.impl.Category;
import org.hibernate.validator.constraints.URL;

/**
 *
 * @author Korisnik
 */
public class FlowerDto implements Dto{
    private Long id;
    @NotEmpty(message = "Ime je obavezno!")
    private String name;
    @Size(max = 200, message = "Opis ne treba da bude duzi od 200 karaktera!")
    private String description;
    @NotNull(message = "Cena je obavezna!")
    @Positive(message = "Cena mora biti veca od 0!")
    private double price;
    
    @URL(message = "Slika mora biti kao link!")
    private String imageUrl;
    //private Supplier supplier; necemo ceo objekat nego samo id
    private Long categoryId;
    
    public FlowerDto(){
        
    }

    public FlowerDto(Long id, String name, String description, double price, String imageUrl, Long categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    

    
    
    
}
