package me.lordierclaw.todoserver.service;

import io.jsonwebtoken.Claims;
import me.lordierclaw.todoserver.model.base.User;
import me.lordierclaw.todoserver.security.ITokenProvider;
import me.lordierclaw.todoserver.service.exception.UnauthorizedException;

import javax.inject.Inject;
import java.util.Date;

public class AuthorizedService {
    @Inject
    protected ITokenProvider tokenProvider;

    protected int authorizeUser(String token) throws UnauthorizedException {
        Claims claims = tokenProvider.decode(token);
        if (claims == null) {
            throw new UnauthorizedException("Invalid token: " + token);
        }
        Date date = new Date();
        Date expired = claims.getExpiration();
        if (expired.compareTo(date) < 0) {
            throw new UnauthorizedException("Expired Token: " + token);
        }
        try {
            return Integer.parseInt(claims.getId());
        } catch (NumberFormatException e) {
            throw new UnauthorizedException("Invalid token: " + token);
        }
    }

    protected void authorizationMatch(String token, int userId) throws UnauthorizedException {
        Integer tokenId = authorizeUser(token);
        if (!tokenId.equals(userId)) {
            throw new UnauthorizedException("User token doesn't matching");
        }
    }

    protected void authorizationMatch(String token, User user) throws UnauthorizedException {
        authorizationMatch(token, user.getId());
    }
}
