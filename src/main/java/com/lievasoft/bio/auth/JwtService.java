package com.lievasoft.bio.auth;

import com.lievasoft.bio.entity.CustomUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private static final long ONE_MINUTE = 60000;

    private final long expiration = ONE_MINUTE * 5;
    private final long refreshExpiration = ONE_MINUTE * 15;
    private final String secret = "miClaveSecretaSuperSeguraYLoSuficientementeLarga";

    public String generateToken(final CustomUser user) {
        return buildToken(user, expiration);
    }

    public String generateRefreshToken(final CustomUser user) {
        return buildToken(user, refreshExpiration);
    }

    private String buildToken(final CustomUser user, final long expiration) {
        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(now)
                .expiration(expirationDate)
                .claims(Map.of())
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
