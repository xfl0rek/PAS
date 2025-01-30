package pl.pas.aplikacjarest.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class Jws {
    private static final String SECRET_KEY = "superSecureJwsKeySuperSecureJwsKey";

    private static SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public static String generateJws(String userId) {
        return Jwts.builder()
                .claims(Map.of("id", userId))
                .issuedAt(new Date())
                .signWith(getSignInKey())
                .compact();
    }

    public static boolean validateJws(String jws, String expectedId) {
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
