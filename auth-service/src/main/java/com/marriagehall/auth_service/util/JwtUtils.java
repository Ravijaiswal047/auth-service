package com.marriagehall.auth_service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private final String SECRET = "mysecretkeymysecretkeymysecretkey123";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 1. Generate Token
    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email) // Who is this user
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hr
                .signWith(key , SignatureAlgorithm.HS256)
                .compact();
    }
  // 2. Extract Email
    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
 // 3.Validate token
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
