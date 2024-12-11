package com.system.bank.devops.auth.insfrastructure.config;

import com.system.bank.devops.auth.domain.model.Token;
import com.system.bank.devops.auth.domain.model.User;
import com.system.bank.devops.auth.domain.port.out.TokenPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Key;
import java.time.ZoneId;
import java.util.*;

@Service
public class JwtService implements TokenPort {

    @Value("${security.jwt.time}")
    private long JWT_TOKEN_VALIDITY;

    @Value("${security.jwt.secret}")
    private String secretKey;

    @Override
    public String generate(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("roles", user.getRoles().stream().map(role -> role.getDescription()).toList());
        claims.put("state",
                Objects.nonNull(user.getCustomerId()) ? user.getStatus() : "PENDING");
        claims.put("customerId",
                Objects.nonNull(user.getCustomerId()) ? user.getCustomerId() : BigDecimal.ZERO);
        return createToken(claims, user.getEmail());
    }

    @Override
    public Token getInfo(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return Token.builder()
                .userId(claims.get("id").toString())
                .createdAt(claims.getIssuedAt().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .expiresAt(claims.getExpiration().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime())
                .revoked(Boolean.FALSE)
                .build();
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder().setClaims(claims).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getCustomerId(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get("customerId").toString();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get("username").toString();
    }

    List<String> extractRoles(String jwt) {
        final Claims claims = getAllClaimsFromToken(jwt);
        return (List<String>) claims.get("roles");
    }

}
