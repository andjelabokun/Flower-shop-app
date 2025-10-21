/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.entity.impl;

import java.util.List;
import jakarta.persistence.*;
import njt.mavenproject1.entity.MyEntity;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "category") //naziv tabele
public class Category implements MyEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //autoinkrement
    private Long id;
    private String name;
    
    
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL) //kategorija ima vise cvetova, cvet je jedna kategorija
    private List<Flower> flowers;
    
    public Category(){
    }
    
    public Category(Long supplierid){
        this.id = supplierid;
    }

    public Category(Long id, String name, List<Flower> flowers) {
        this.id = id;
        this.name = name;
        this.flowers = flowers;
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

    public List<Flower> getFlowers() {
        return flowers;
    }

    public void setFlowers(List<Flower> flowers) {
        this.flowers = flowers;
    }
    
}
