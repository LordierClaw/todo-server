package me.lordierclaw.todoserver.security.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.lordierclaw.todoserver.security.TokenProvider;
import me.lordierclaw.todoserver.utils.ConfigManager;

import java.util.Date;

public class JWTTokenProvider implements TokenProvider {
    private static final String SECRET = ConfigManager.getInstance().load("security_config").get("JWT_SECRET");
    private static final long ACCESS_TOKEN_VALIDITY =
            Long.parseLong(ConfigManager.getInstance().load("security_config").get("ACCESS_TOKEN_VALIDITY"));
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
