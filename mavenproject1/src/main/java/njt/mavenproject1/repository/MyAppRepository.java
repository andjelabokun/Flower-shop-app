/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package njt.mavenproject1.repository;

import java.util.List;

/**
 *
 * @author Korisnik
 */
public interface MyAppRepository<E, ID> {
    //E objekat nad kojim vrsimo operaciju
    //ID tip entiteta za koji se radi ta neka operacija
    List<E> findAll();
    E findById(ID id) throws Exception; //ucitavanje entiteta na osnovu id
    void save(E entity); //create(bez id, baza pravi novi) i update(sa id)
    void deleteById(ID id); 
}
