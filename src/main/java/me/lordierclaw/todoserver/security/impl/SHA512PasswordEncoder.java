package me.lordierclaw.todoserver.security.impl;

import me.lordierclaw.todoserver.security.IPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA512PasswordEncoder implements IPasswordEncoder {
    private static final byte[] SALT = "SomeConstantSaltValue".getBytes(StandardCharsets.UTF_8);
    private static final String ALGORITHM = "SHA-512";

    @Override
    public String encode(String password) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(SALT);
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return generatedPassword;
    }

    @Override
    public boolean compare(String encodedPassword, String password) {
        return encodedPassword.equals(encode(password));
    }
}
