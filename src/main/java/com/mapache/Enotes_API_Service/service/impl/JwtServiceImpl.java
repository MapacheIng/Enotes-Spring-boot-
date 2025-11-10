package com.mapache.Enotes_API_Service.service.impl;

import com.mapache.Enotes_API_Service.entity.Role;
import com.mapache.Enotes_API_Service.entity.User;
import com.mapache.Enotes_API_Service.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secretKey; // Your secret key here

    @Override
    public String generateToken(User user) {

        List<String> roleNames = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", roleNames);
        claims.put("status", user.getStatus().getIsActive());


        return Jwts.builder()
                .claims().add(claims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) // Token valid for 5 minutes
                .and()
                .signWith(getKey())
                .compact();


    }

    @Override
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }



    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration().before(new Date());
    }


    private String role(String token){
        Claims claims = extractAllClaims(token);
        return claims.get("role", String.class);
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(decryptKey(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey decryptKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
