/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.entity.impl;

import jakarta.persistence.*;
import njt.mavenproject1.entity.MyEntity;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name="users",
        uniqueConstraints = {
            @UniqueConstraint(name="uk_user_username", columnNames="username"),
            @UniqueConstraint(name="uk_user_email", columnNames="email") 
            //ne moze biti vise korisnika sa istim mailom ili sifrom
        }
                )
public class User implements MyEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String username;
    
    @Column(nullable = false, length = 120)
    private String email;
    
    @Column(nullable = false)
    private String passwordHash; //cuvamo kao Hash ne kao lozinku
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.USER;

    public User() {
    }

    public User(Long id) {
        this.id = id;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    
}
