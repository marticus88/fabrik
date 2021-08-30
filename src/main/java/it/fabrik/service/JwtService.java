package it.fabrik.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.fabrik.exception.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtService {

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_CREATED = "iat";
    final ObjectMapper objectMapper;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public JwtService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public String getAccountIdFromToken(String token) throws JwtException {
        String accountId;
        try {
            final Claims claims = getClaimsFromToken(token);
            accountId = claims.getSubject();
        } catch (Exception e) {
            log.error("AccountId missing in jwt");
            throw new JwtException("AccountId missing in jwt");
        }
        return accountId;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + (expiration * 60000));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        if (expiration == null) {
            return true;
        }
        return expiration.before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    public String refreshToken(String token) {
        if (validateToken(token)) {
            String refreshedToken;
            try {
                final Claims claims = getClaimsFromToken(token);
                claims.put(CLAIM_KEY_CREATED, new Date());
                refreshedToken = generateToken(claims);
            } catch (Exception e) {
                refreshedToken = null;
            }
            return refreshedToken;
        }
        return null;
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
