/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import njt.mavenproject1.entity.impl.User;
import njt.mavenproject1.repository.MyAppRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Korisnik
 */
@Repository
public class UserRepository implements MyAppRepository<User, Long>{
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> findAll() {
         return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User findById(Long id) throws Exception {
        User user = em.find(User.class, id);
        if(user == null){
           throw new Exception("User nije pronadjen!");
        }
        return user;
    }

    @Override
    public void save(User entity) {
        //create i update
        if(entity.getId() == null){
            em.persist(entity); //ako nema id radimo perisit i kreiramo obj u bazi
        } else{
            em.merge(entity); //ako ima id merger azuriramo postojeci obj
        }
    }

    @Override
    public void deleteById(Long id) {
        User user  = em.find(User.class, id);
        if(user != null){
            em.remove(user);
        }
    }
    
    //SAMO ZA USERA I LOGOVANJE
    public User findByUsername(String username){
        List<User> list = em.createQuery("SELECT u FROM User u WHERE u.username = :un", User.class)
                .setParameter("un", username).getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
    
    public boolean existsByUsername(String username){
        Long c = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :un", Long.class)
                .setParameter("un", username).getSingleResult();
        return c>0;
    }
    
    public boolean existsByEmail(String email){
        Long c = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :em", Long.class)
                .setParameter("em", email).getSingleResult();
        return c>0;
    }
    
}
