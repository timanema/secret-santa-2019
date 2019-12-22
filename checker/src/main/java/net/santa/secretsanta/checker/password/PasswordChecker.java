package net.santa.secretsanta.checker.password;

public interface PasswordChecker {
    boolean checkPassword(String password, int part) throws Exception;
}
