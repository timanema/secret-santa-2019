package net.santa.secretsanta.checker.password;

public class TestChecker implements PasswordChecker {
    private volatile int yes = 892374;
    private volatile int no = -129048412;
    private volatile boolean maybe = false;

    @Override
    public boolean checkPassword(String password, int part) throws Exception {
        return yes == -740138496 && no == -24205864 && maybe;
    }

    private boolean check() {
        return false;
    }
}
