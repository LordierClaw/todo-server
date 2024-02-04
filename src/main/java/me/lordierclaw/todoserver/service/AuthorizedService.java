package me.lordierclaw.todoserver.service;

import io.jsonwebtoken.Claims;
import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.exception.response.ResponseValue;
import me.lordierclaw.todoserver.model.base.User;
import me.lordierclaw.todoserver.security.TokenProvider;

import javax.inject.Inject;
import java.util.Date;

public class AuthorizedService {
    @Inject
    protected TokenProvider tokenProvider;

    protected int authorizeUser(String token) throws ResponseException {
        Claims claims = tokenProvider.decode(token);
        if (claims == null) {
            throw new ResponseException(ResponseValue.INVALID_TOKEN);
        }
        Date date = new Date();
        Date expired = claims.getExpiration();
        if (expired.compareTo(date) < 0) {
            throw new ResponseException(ResponseValue.EXPIRED_TOKEN);
        }
        try {
            return Integer.parseInt(claims.getId());
        } catch (NumberFormatException e) {
            throw new ResponseException(ResponseValue.INVALID_TOKEN);
        }
    }

    protected void authorizationMatch(String token, int userId) throws ResponseException {
        Integer tokenId = authorizeUser(token);
        if (!tokenId.equals(userId)) {
            throw new ResponseException(ResponseValue.INVALID_TOKEN);
        }
    }

    protected void authorizationMatch(String token, User user) throws ResponseException {
        authorizationMatch(token, user.getId());
    }
}
