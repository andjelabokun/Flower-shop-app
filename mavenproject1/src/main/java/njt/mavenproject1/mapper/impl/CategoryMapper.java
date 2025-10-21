/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.mapper.impl;

import njt.mavenproject1.dto.impl.CategoryDto;
import njt.mavenproject1.entity.impl.Category;
import njt.mavenproject1.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Korisnik
 */
@Component
public class CategoryMapper implements DtoEntityMapper<CategoryDto, Category>{

    @Override
    public CategoryDto toDto(Category e) {
        CategoryDto dto = new CategoryDto(e.getId(), e.getName());
        return dto;
    }

    @Override
    public Category toEntity(CategoryDto t) {
        return new Category(t.getId(), t.getName(), null);
    }
    
}
