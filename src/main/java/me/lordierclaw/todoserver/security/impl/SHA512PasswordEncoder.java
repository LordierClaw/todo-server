package me.lordierclaw.todoserver.security.impl;

import me.lordierclaw.todoserver.security.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SHA512PasswordEncoder implements PasswordEncoder {
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
            Logger.getLogger(SHA512PasswordEncoder.class.getName()).log(Level.SEVERE, "Can't find matching Algorithm", e);
            return null;
        }
        return generatedPassword;
    }

    @Override
    public boolean compare(String encodedPassword, String password) {
        return encodedPassword.equals(encode(password));
    }
}
