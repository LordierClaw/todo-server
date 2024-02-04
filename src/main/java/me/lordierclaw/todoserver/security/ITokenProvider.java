package me.lordierclaw.todoserver.security;

import io.jsonwebtoken.Claims;

public interface ITokenProvider {
    String newAccessToken(int id);

    Claims decode(String token);
}
