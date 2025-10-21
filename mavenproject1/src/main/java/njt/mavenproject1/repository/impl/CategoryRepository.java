/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import njt.mavenproject1.entity.impl.Category;
import njt.mavenproject1.repository.MyAppRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Korisnik
 */
@Repository
public class CategoryRepository implements MyAppRepository<Category, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Category> findAll() {
        return entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }

    @Override
    public Category findById(Long id) throws Exception {
        Category category = entityManager.find(Category.class, id);
        if(category == null){
           throw new Exception("Dobavljac nije pronadjen!");
        }
        return category;
    }

    @Override
    @Transactional
    public void save(Category entity) {
        //create i update, obavljaju se u okviu jedne transakcije ybog transactional
        if(entity.getId() == null){
            entityManager.persist(entity); //ako nema id radimo perisit i kreiramo obj u bazi
        } else{
            entityManager.merge(entity); //ako ima id merger azuriramo postojeci obj
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Category category = entityManager.find(Category.class, id);
        if(category != null){
            entityManager.remove(category);
        }
    }
    
}
