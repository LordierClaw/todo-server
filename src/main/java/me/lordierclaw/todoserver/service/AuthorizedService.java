package me.lordierclaw.todoserver.service;

import me.lordierclaw.todoserver.model.base.User;
import me.lordierclaw.todoserver.security.UserManager;
import me.lordierclaw.todoserver.service.exception.UnauthorizedException;

import javax.inject.Inject;

public class AuthorizedService {
    @Inject
    protected UserManager userManager;

    protected int authorizeUser(String token) throws UnauthorizedException {
        Integer userId = userManager.getUserSession(token);
        if (userId == null) {
            throw new UnauthorizedException("Invalid token: " + token);
        }
        return userId;
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
