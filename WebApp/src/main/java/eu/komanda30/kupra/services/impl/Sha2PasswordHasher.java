package eu.komanda30.kupra.services.impl;

import eu.komanda30.kupra.services.PasswordHasher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;

@Service
public class Sha2PasswordHasher implements PasswordHasher {
    @Override
    public String hash(String rawPassword) {
        final MessageDigest sha256;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw Throwables.propagate(e);
        }
        final byte[] passBytes = rawPassword.getBytes();
        final byte[] passHash = sha256.digest(passBytes);
        final StringBuilder stringEncodedHash = new StringBuilder(passHash.length*2);
        for (byte b : passHash) {
            stringEncodedHash.append(String.format("%02x",b));
        }
        return stringEncodedHash.toString();
    }
}
