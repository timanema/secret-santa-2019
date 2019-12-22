package net.timanema.secretsanta.checker.password;

public class TestPasswordChecker implements PasswordChecker {
    @Override
    public boolean checkPassword(String password, int part) {
        return password.equals("yeet42");
    }
}
