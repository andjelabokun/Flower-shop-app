/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.dto.impl;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import njt.mavenproject1.dto.Dto;

/**
 *
 * @author Korisnik
 */
public class CategoryDto implements Dto{
    //validacija koju radi Controller
    private Long id;
    
    @NotEmpty(message = "Ime je obavezno!")
    private String name;
    
    public CategoryDto(){
        
    }

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
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

    
    
}
