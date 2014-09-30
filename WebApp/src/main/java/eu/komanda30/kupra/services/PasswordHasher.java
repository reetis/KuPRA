package eu.komanda30.kupra.services;

public interface PasswordHasher {
    String hash(String rawPassword);
}
