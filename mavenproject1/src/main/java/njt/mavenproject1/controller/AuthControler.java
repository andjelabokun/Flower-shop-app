/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import njt.mavenproject1.dto.impl.AuthResponse;
import njt.mavenproject1.dto.impl.LoginRequest;
import njt.mavenproject1.dto.impl.RegisterRequest;
import njt.mavenproject1.dto.impl.UserDto;
import njt.mavenproject1.entity.impl.User;
import njt.mavenproject1.repository.impl.UserRepository;
import njt.mavenproject1.servis.AuthServis;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Korisnik
 */
@CrossOrigin(origins = "hhtp://localhost:3000")
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
public class AuthControler {
    
    private final AuthServis authServis;
    private final UserRepository users;

    public AuthControler(AuthServis authServis, UserRepository users) {
        this.authServis = authServis;
        this.users = users;
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest req) throws Exception{
        return ResponseEntity.ok(authServis.register(req));
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req){
        return ResponseEntity.ok(authServis.login(req));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(){
        return ResponseEntity.ok().build();
        //brisemo token
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Authentication auth) throws Exception{
        User u = users.findByUsername(auth.getName());
        UserDto dto = new UserDto(u.getId(), u.getUsername(), u.getEmail(), u.getRole());
        return ResponseEntity.ok(dto);
        //vraca podatke ulogovanom korisniku
        //zasticena ruta mozeo pristupiti preko tokena
        //token je iz klase authentication
    }
    
        
}
