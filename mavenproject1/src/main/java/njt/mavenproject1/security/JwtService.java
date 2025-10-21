/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Korisnik
 */
@Service
public class JwtService {
    @Value ("${app.jwt.secret}")
    private String secret;
    
    @Value ("${app.jwt.expiration-ms}")
    private long expirationMs;
    
    private Key key(){
       return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
    public String generate (UserDetails user, Map<String,Object> extra){
        Date now = new Date(); //token generisemo na osnovu  danasnjeg datuma
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder() //generisemo token
                .setSubject(user.getUsername())
                .addClaims(extra)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key(), SignatureAlgorithm.HS256) 
                .compact();
    }
    
    public String extractUsername(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
    
    public boolean isValid(String token, UserDetails user){
        //da li je validan token
        try{
            final String un = extractUsername(token);
            return un.equals(user.getUsername()) &&
                    !isExpired(token);
        }catch(JwtException e){
            return false;
        }
    }
    
    public boolean isExpired(String token){
        Date exp = Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getExpiration();
        if (exp == null) return true;
        return exp.before(new Date());
    }
    
}
