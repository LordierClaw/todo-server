package me.lordierclaw.todoserver.security;

import me.lordierclaw.todoserver.model.base.User;
import me.lordierclaw.todoserver.security.utils.ITokenGenerator;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class UserManager {
    private final HashMap<String, Integer> userMap = new HashMap<>();

    @Inject
    private ITokenGenerator tokenGenerator;

    private String generateNewToken() {
        String token;
        do {
            token = tokenGenerator.newToken();
        } while (userMap.containsKey(token));
        return token;
    }

    public String setUserSession(User user) {
        if (userMap.containsValue(user.getId())) {
            for(Map.Entry<String, Integer> entry: userMap.entrySet()) {
                if (entry.getValue().equals(user.getId())) {
                    userMap.remove(entry.getKey());
                    break;
                }
            }
        }
        String token = generateNewToken();
        userMap.put(token, user.getId());
        return token;
    }

    public Integer getUserSession(String token) {
        return userMap.getOrDefault(token, null);
    }

    public void clearSessions() {
        userMap.clear();
    }
}
