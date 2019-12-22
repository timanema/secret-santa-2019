package net.timanema.secretsanta.checker.password;

import net.timanema.secretsanta.checker.verifiers.EasyReflectionVerifier;
import net.timanema.secretsanta.checker.verifiers.HashCodeVerifier;

public class HighTechChecker implements PasswordChecker {
    // password should be: home_good_train_bad
    @Override
    public boolean checkPassword(String password, int part) throws Exception {
        switch (part) {
            case 1:
                return new HashCodeVerifier().verify(password);
            case 2:
                return new EasyReflectionVerifier().verify(password);
            default:
                return false;
        }
    }
}
