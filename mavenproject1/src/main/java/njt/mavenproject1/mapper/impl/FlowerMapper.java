/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.mapper.impl;

import njt.mavenproject1.dto.impl.FlowerDto;
import njt.mavenproject1.entity.impl.Flower;
import njt.mavenproject1.entity.impl.Category;
import njt.mavenproject1.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Korisnik
 */
@Component
public class FlowerMapper implements DtoEntityMapper<FlowerDto, Flower>{

    @Override
    public FlowerDto toDto(Flower e) {
        Long categoryId = e.getCategory() != null ? e.getCategory().getId() : null;
        return new FlowerDto(e.getId(), e.getName(), e.getDescription(), e.getPrice(), e.getImageUrl(), categoryId);
    }

    @Override
    public Flower toEntity(FlowerDto t) {
        //Entity ima ceo restoran
        //komunikacija od klijenta ka bazi
        //pretvaramo dto u entity
        //dto ima id dobavljaca na osnovu njega popunjavamo ceo atribut dobavljac posto nam je tako u entity klasi
        Category category = t.getCategoryId() != null ? new Category(t.getCategoryId()) : null;
        return new Flower(t.getId(), t.getName(), t.getDescription(), t.getPrice(), t.getImageUrl(), category);
    }

    
    
}
