package me.lordierclaw.todoserver.security.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.lordierclaw.todoserver.security.ITokenProvider;

import java.util.Date;

public class JWTTokenProvider implements ITokenProvider {
    private static final String SECRET = "3a8f7b9cde245a17fcd89a53b6f687c482bc72f4a5d96c816ed2aeccab447d01";
    private static final long ACCESS_TOKEN_VALIDITY = 3600000; // 1 hour
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    @Override
    public String newAccessToken(int id) {
        return Jwts.builder()
                .id(String.valueOf(id))
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + ACCESS_TOKEN_VALIDITY))
                .signWith(SIGNATURE_ALGORITHM, SECRET)
                .compact();
    }

    @Override
    public Claims decode(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET).build().parseSignedClaims(token).getBody();
        } catch (JwtException e) {
            return null;
        }
    }

}
