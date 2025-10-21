/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.servis;

import java.util.List;
import java.util.stream.Collectors;
import njt.mavenproject1.dto.impl.CategoryDto;
import njt.mavenproject1.entity.impl.Category;
import njt.mavenproject1.mapper.impl.CategoryMapper;
import njt.mavenproject1.repository.impl.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Korisnik
 */
@Service
public class CategoryServis {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    
    @Autowired
    public CategoryServis(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }
    //da bi app znala da ova dva napravi da budu beanovi
    
    //vraca dto jer vraca controleru 
    public List<CategoryDto> findAll(){
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).collect(Collectors.toList());
        //zovemo findAll za bazu koja vraca listu entitya
        //mapiramo sa fjom iz mappera
    }
    
    public CategoryDto findById(Long id) throws Exception{
        return categoryMapper.toDto(categoryRepository.findById(id));
    }

    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    public void deteleById(Long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryDto update(CategoryDto categoryDto) {
        Category updated = categoryMapper.toEntity(categoryDto);
        categoryRepository.save(updated);
        return categoryMapper.toDto(updated);
    }
    
    
}
