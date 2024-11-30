package com.abhi.BasicAuth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationTime;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime()+jwtExpirationTime);

        String token = Jwts.builder()
                .claim("sub", username)
                .claim("iat", new Date())
                .claim("exp", expireDate)
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }

    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token){
        Claims claims = Jwts.parser().setSigningKey(key())
                .build().parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        return username;
    }

    public boolean validateToken(String token){
        Jwts.parser().setSigningKey(key()).build().parse(token);
        return true;
    }
}
