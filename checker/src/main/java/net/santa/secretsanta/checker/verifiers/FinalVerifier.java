package net.santa.secretsanta.checker.verifiers;

// Hints:
// Hint 1: This verifier sums the char values and compares them to a constant.
//         There are a lot of hash conflicts for this one, so it isn't the most accurate one, but it can help you
//         find the last missing pieces of the puzzle, in case you're stuck on one verifier.

/**
 * You're almost there! If you cracked all previous verifiers, this one should already be cracked.
 * The sole purpose of this verifier is to give you something to work with, if you're stuck on one verifier.
 *
 * Although this code is relatively clear, there is one hint available for this verifier.
 *
 * - Secret Santa
 */
public class FinalVerifier implements Verifier {
    @Override
    public boolean verify(String password) throws Exception {
        int sum = 0;

        for (char c : password.toCharArray()) {
            sum += c;
        }

        return sum == 1972;
    }
}
