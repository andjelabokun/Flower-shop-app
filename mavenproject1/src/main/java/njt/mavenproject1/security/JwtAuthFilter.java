/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Korisnik
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter{

    private final JwtService jwt;
    private final AppUserDetailsService uds;

    public JwtAuthFilter(JwtService jwt, AppUserDetailsService uds) {
        this.jwt = jwt;
        this.uds = uds;
    }
    
    
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String path = request.getServletPath();
    
    if (path.startsWith("/api/auth")) {
        filterChain.doFilter(request, response);
        return;
    }
    
    String authHeader = request.getHeader("Authorization");
    // ✅ 1. Ako nema tokena ili nije Bearer — pusti dalje (npr. login, register)
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }

    // ✅ 2. Inače — izvuci token (bez "Bearer ")
    String token = authHeader.substring(7);
    String username = null;

    try {
        username = jwt.extractUsername(token);
    } catch (Exception ignored) {}

    // ✅ 3. Ako imamo username i korisnik nije već autentifikovan
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = uds.loadUserByUsername(username);

        // ✅ 4. Proveri validnost tokena
        if (jwt.isValid(token, userDetails)) {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    // ✅ 5. Uvek pozovi dalje filter chain
    filterChain.doFilter(request, response);


    }
    
}
