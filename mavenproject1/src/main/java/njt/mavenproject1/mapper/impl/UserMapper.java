/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.mapper.impl;

import njt.mavenproject1.dto.impl.UserDto;
import njt.mavenproject1.entity.impl.User;
import njt.mavenproject1.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Korisnik
 */
@Component
public class UserMapper implements DtoEntityMapper<UserDto, User>{

    @Override
    public UserDto toDto(User e) {
        if(e == null) return null;
        return new UserDto(e.getId(), e.getUsername(), e.getEmail(), e.getRole());
        //password nikada ne punimo iz baze
    }

    @Override
    public User toEntity(UserDto t) {
        if(t == null) return null;
        User u = new User();
        u.setId(t.getId());
        u.setUsername(t.getUsername());
        u.setEmail(t.getEmail());
        //sifru stavljas u servisu
        u.setRole(t.getRole());
        return u;
    }
    
}
