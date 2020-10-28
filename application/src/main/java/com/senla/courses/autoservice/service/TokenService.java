package com.senla.courses.autoservice.service;

import com.google.common.io.BaseEncoding;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class TokenService {

    private final SecretKey secretKey;

    public TokenService() {
        String jwtKey = "jwtKey13579";
        byte[] decodedKey = BaseEncoding.base64().decode(jwtKey);
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public String generateToken(@NonNull String userName, @NonNull LocalDateTime expireDate) {
        String token = Jwts.builder()
                .setSubject(userName)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(Date.from(expireDate.atZone(ZoneId.systemDefault()).toInstant()))
                .compact();
        return token;
    }

    public String getUserNameFromToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return claimsJws.getBody().getSubject();
        } catch (RuntimeException e) {
            return null;
        }

    }

}
