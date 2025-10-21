/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.security;

import java.util.List;
import njt.mavenproject1.entity.impl.User;
import njt.mavenproject1.repository.impl.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Korisnik
 */
@Service
public class AppUserDetailsService implements UserDetailsService{
    
    //veza izmedju jwtservice i jwtauthfiler sa user repository klasom

    private final UserRepository users;

    public AppUserDetailsService(UserRepository users) {
        this.users = users;
    }
    
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = users.findByUsername(username);
        if(u == null) throw new UsernameNotFoundException("Not found");
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_"+u.getRole().name()))
        );
        
    }
    
    
}
