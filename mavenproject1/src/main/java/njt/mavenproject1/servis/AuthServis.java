/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.servis;

import jakarta.transaction.Transactional;
import java.util.Map;
import njt.mavenproject1.dto.impl.AuthResponse;
import njt.mavenproject1.dto.impl.LoginRequest;
import njt.mavenproject1.dto.impl.RegisterRequest;
import njt.mavenproject1.dto.impl.UserDto;
import njt.mavenproject1.entity.impl.Role;
import njt.mavenproject1.entity.impl.User;
import njt.mavenproject1.mapper.impl.UserMapper;
import njt.mavenproject1.repository.impl.UserRepository;
import njt.mavenproject1.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Korisnik
 */
@Service
public class AuthServis {
    private final AuthenticationManager authManager;
    private final JwtService jwt; //token
    private final UserRepository users;
    private final PasswordEncoder encoder; //enkriptovanje lozinke
    private final UserMapper userMapper;

    public AuthServis(AuthenticationManager authManager, JwtService jwt, UserRepository users, PasswordEncoder encoder, UserMapper userMapper) {
        this.authManager = authManager;
        this.jwt = jwt;
        this.users = users;
        this.encoder = encoder;
        this.userMapper = userMapper;
    }
    
    @Transactional
    public UserDto register(RegisterRequest req) throws Exception{
        //svi podaci
        //provera da li vec postoji u bazi
        if (users.existsByUsername(req.getUsername()))
            throw new Exception("Username already taken");
        if(users.existsByEmail(req.getEmail()))
            throw new Exception("Email already taken.");
        
        User u = new User();
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setPasswordHash(encoder.encode(req.getPassword()));
        u.setRole(Role.USER);//rola uvek user
        
        users.save(u); //userRepository save, cuva ga u bazi
        return userMapper.toDto(u);
    }
    
    public AuthResponse login(LoginRequest req){
        Authentication auth = authManager.authenticate( 
        new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        //ako se ne baci ex onda je autentifikacija prosla
        String token = jwt.generate((org.springframework.security.core.userdetails.User) auth.getPrincipal(), Map.of("role", "USER"));
        //moze rola da se promeni
        User me = users.findByUsername(req.getUsername());//nalazimo usera u bazi
        return new AuthResponse(token, userMapper.toDto(me));//vracamo dto authresponse
    }
}
