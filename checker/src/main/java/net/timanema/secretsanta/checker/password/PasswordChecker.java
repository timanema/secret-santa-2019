package net.timanema.secretsanta.checker.password;

public interface PasswordChecker {
    boolean checkPassword(String password, int part) throws Exception;
}
