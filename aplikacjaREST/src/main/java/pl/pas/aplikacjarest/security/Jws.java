package pl.pas.aplikacjarest.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class Jws {

    @Value("${JWS_SECRET}")
    private String SECRET_KEY;

    private SecretKey getSignInKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJws(String userId) {
        return Jwts.builder()
                .claims(Map.of("id", userId))
                .issuedAt(new Date())
                .signWith(getSignInKey())
                .compact();
    }

    public boolean validateJws(String jws, String expectedId) {
        try {
            String idFromToken = Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(jws)
                    .getPayload()
                    .get("id", String.class);

            return idFromToken.equals(expectedId);
        } catch (Exception e) {
            return false;
        }
    }
}
