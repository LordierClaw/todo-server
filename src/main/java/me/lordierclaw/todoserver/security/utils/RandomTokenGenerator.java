package me.lordierclaw.todoserver.security.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomTokenGenerator implements ITokenGenerator {
    private final SecureRandom secureRandom = new SecureRandom();
    private final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    @Override
    public String newToken() {
        byte[] randomBytes = new byte[tokenLength];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
