package com.lievasoft.cefac.auth;

import com.lievasoft.cefac.entity.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final long ONE_MINUTE = 60000;

    private final long expiration = ONE_MINUTE * 15;
    private final long refreshExpiration = ONE_MINUTE * 30;
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

        Set<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Map<String, Object> claims = Map.of(
                "name", user.getName(),
                "authorities", authorities
        );

        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(now)
                .expiration(expirationDate)
                .claims(claims)
                .signWith(getSignInKey())
                .compact();
    }

    public String extractUsername(final String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        Claims extractedClaims = extractClaims(token);
        return claimsResolver.apply(extractedClaims);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
