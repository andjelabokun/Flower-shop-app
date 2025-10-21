/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package njt.mavenproject1.mapper;

/**
 *
 * @author Korisnik
 */
public interface DtoEntityMapper<T, E> {
    //za mapiranje iz DTO u ENTITY i obrnuto
    // 1 interfejs za supplier mapper u flower mapper zato korisnimo GENERICKE TIPOVE
    //List<E> kao u C# poruka<T>
    //T je za DTO
    //E je za Entiy
    
    T toDto(E e);
    E toEntity(T t);
    
}
