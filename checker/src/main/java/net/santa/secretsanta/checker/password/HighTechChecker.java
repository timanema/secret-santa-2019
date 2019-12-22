package net.santa.secretsanta.checker.password;

import net.santa.secretsanta.checker.verifiers.EasyReflectionVerifier;
import net.santa.secretsanta.checker.verifiers.FinalVerifier;
import net.santa.secretsanta.checker.verifiers.HashCodeVerifier;

/**
 * The part that makes sure the correct verifier gets called. Again, not much to see here.
 *
 * - Secret Santa
 */
public class HighTechChecker implements PasswordChecker {
    // password should be: home_good_train_bad
    @Override
    public boolean checkPassword(String password, int part) throws Exception {
        switch (part) {
            case 1:
                return new HashCodeVerifier().verify(password);
            case 2:
                return new EasyReflectionVerifier().verify(password);
            case 3:
                return true; // TODO: WIP
            case 4:
                return new FinalVerifier().verify(password);
            default:
                return false;
        }
    }
}
