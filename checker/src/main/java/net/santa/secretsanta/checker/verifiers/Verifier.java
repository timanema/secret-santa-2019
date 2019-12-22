package net.santa.secretsanta.checker.verifiers;

public interface Verifier {
    // If password is correct continue to the next one, otherwise return null -> crash
    boolean verify(String password) throws Exception;
}
