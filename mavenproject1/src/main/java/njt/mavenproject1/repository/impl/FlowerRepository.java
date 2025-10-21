/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import njt.mavenproject1.entity.impl.Flower;
import njt.mavenproject1.repository.MyAppRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Korisnik
 */
@Repository
public class FlowerRepository implements MyAppRepository<Flower, Long>{

    //komunikacija sa bazom preko entitymanagera
    //jpa
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Flower> findAll() {
        //preko entity managera cemo da izvrsimo query sleect * from flower
        return entityManager.createQuery("SELECT f FROM Flower f", Flower.class).getResultList();
    }

    @Override
    public Flower findById(Long id) throws Exception {
        Flower flower = entityManager.find(Flower.class, id);
        if(flower == null){
           throw new Exception("Cvet nije pronadjen!");
        }
        return flower;
    }

    @Override
    @Transactional
    public void save(Flower entity) {
        //create i update
        if(entity.getId() == null){
            entityManager.persist(entity); //ako nema id radimo perisit i kreiramo obj u bazi
        } else{
            entityManager.merge(entity); //ako ima id merger azuriramo postojeci obj
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Flower flower  = entityManager.find(Flower.class, id);
        if(flower != null){
            entityManager.remove(flower);
        }
    }

    public List<Flower> findByCategory(Long categoryId) {
        return entityManager.createQuery("SELECT f FROM Flower f WHERE f.category.id = :cid", Flower.class).setParameter("cid", categoryId).getResultList();
    }
    
}
