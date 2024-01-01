package com.olympus.config.jwt;

import com.olympus.config.AuthDetailsImpl;
import com.olympus.config.Constant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

    /**
     * Generate jwt token
     */
    public String generateToken(AuthDetailsImpl authDetails) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + Constant.JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(authDetails.getUsername())
                .setIssuer("Olympus Social Network")
                .setIssuedAt(now)
                .setExpiration(exp)
                .claim("id", authDetails.getAuthentication().getUser().getId().toString())
                .signWith(SignatureAlgorithm.HS512, Constant.JWT_SECRET)
                .compact();
    }

    /**
     * Extract user id from jwt
     */
    public String getUserEmailFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Constant.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * Extract user id from jwt
     */
    public String getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Constant.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("id", String.class);
    }

    /**
     * Validate token
     */
    public boolean isTokenValidated(String token) {
        try {
            Jwts.parser().setSigningKey(Constant.JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            log.error("Invalid JWT");
        }
        return false;
    }
}
