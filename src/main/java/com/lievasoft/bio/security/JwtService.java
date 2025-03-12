package com.lievasoft.bio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final String JWT_SECRET = "miClaveSecretaSuperSeguraYLoSuficientementeLarga";
    private static final long ONE_MINUTE = 60000;

    SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String subject) {
        long fiveMinutes = ONE_MINUTE * 5;
        Date now = new Date();
        Date expiration = new Date(now.getTime() + fiveMinutes);

        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public String getSubjectFromToken(String token) {
        return Jwts.parser()
                .decryptWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
