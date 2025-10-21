/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.dto.impl;

import njt.mavenproject1.dto.Dto;
import njt.mavenproject1.entity.impl.Role;

/**
 *
 * @author Korisnik
 */
public class UserDto implements Dto{
    private Long id;
    private String username;
    private String email;
    private Role role;

    public UserDto() {
    }
//vracamo sve bez lozinke jer admin/cvecar ne treba da ima lozinku klijneta
    
    public UserDto(Long id, String username, String email, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
}
