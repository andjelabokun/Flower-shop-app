/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.servis;

import java.util.List;
import java.util.stream.Collectors;
import njt.mavenproject1.dto.impl.FlowerDto;
import njt.mavenproject1.entity.impl.Flower;
import njt.mavenproject1.mapper.impl.FlowerMapper;
import njt.mavenproject1.repository.impl.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Korisnik
 */
@Service
public class FlowerServis {
    private final FlowerRepository flowerRepository;
    private final FlowerMapper flowerMapper;

    @Autowired
    public FlowerServis(FlowerRepository flowerRepository, FlowerMapper flowerMapper) {
        this.flowerRepository = flowerRepository;
        this.flowerMapper = flowerMapper;
    }
    
    public List<FlowerDto> findAll(){
        return flowerRepository.findAll().stream().map(flowerMapper::toDto).collect(Collectors.toList());
    }

    public FlowerDto create(FlowerDto flowerDto) {
        Flower flower = flowerMapper.toEntity(flowerDto);
        flowerRepository.save(flower);
        return flowerMapper.toDto(flower);
    }

    public FlowerDto update(FlowerDto flowerDto) {
        Flower updated = flowerMapper.toEntity(flowerDto);
        flowerRepository.save(updated);
        return flowerMapper.toDto(updated);
    }

    public void deteleById(Long id) {
        flowerRepository.deleteById(id);
    }

    public List<FlowerDto> findByCategory(Long categoryId) {
        return flowerRepository.findByCategory(categoryId).stream().map(flowerMapper::toDto).collect(Collectors.toList());

    }
    
}
