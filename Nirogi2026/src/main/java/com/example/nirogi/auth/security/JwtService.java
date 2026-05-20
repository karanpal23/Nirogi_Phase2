package com.example.nirogi.auth.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.nirogi.auth.enums.CookieName;
import com.example.nirogi.user.entity.User;
import com.example.nirogi.user.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class JwtService {

    // FIX BUG 3: Externalize JWT secret from application.properties
    // Must be at least 256 bits (32 characters) for HS256
    @Value("${app.jwt.secret:changeme-set-app.jwt.secret-in-properties}")
    private String SECRET;

    @Autowired
    private  UserRepository userRepository;

    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken(User user) {

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 86400000)
                )
                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET
                )
                .compact();
    }

    public String extractToken(HttpServletRequest request) {

        if (request.getCookies() == null)
            return null;

        for (Cookie cookie : request.getCookies()) {

//            if (cookie.getName().equals("JWT_TOKEN"))
        	if (cookie.getName().equals(CookieName.JWT_TOKEN.name()))
                return cookie.getValue();
        }

        return null;
    }

    public boolean validateToken(String token) {

        try {

            Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token);

            return true;

        } catch (Exception ex) {

            return false;
        }
    }
    
    public String extractUsername(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    


    public Long extractUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Integer.class).longValue();
    }

    public String extractRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("role");
    }
}
