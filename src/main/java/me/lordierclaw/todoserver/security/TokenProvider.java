package me.lordierclaw.todoserver.security;

import io.jsonwebtoken.Claims;

public interface TokenProvider {
    String newAccessToken(int id);

    Claims decode(String token);
}
