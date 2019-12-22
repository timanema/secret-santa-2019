package net.santa.secretsanta.checker.verifiers;

import net.santa.secretsanta.checker.Bootstrap;

import java.lang.reflect.Field;

// Hints:
// Hint 1: This program reads private values according to the given password, and attempts to read it from the class
//         with an offset of 6. This gives you the range of the possible chars. It then does some magic and
//         compares the values retrieved with two constants.
// Hint 2: If the value of i is negative at any point, the verifies crashes. Which means that the password is limited to
//         characters which resolve to low value non-negative ints. This should remove quite a few options.
// Hint 3: The password consists of 4 words, in the following format 'xxxx_yyyy_zzzzz_ttt'. This part only tests
//         the last word (ttt)
// Hint 4: The password should end with '_bad'

/**
 * Good to see you've overcome the first hurdle!
 * This one should be a lot easier than the previous one, and should give you some time to breath.
 *
 * I suspect you won't need them, but there are four hints available for this part.
 *
 * - Secret Santa
 */
public class EasyReflectionVerifier implements Verifier {
    public class CharToNum {
        private volatile int a = 812;
        private volatile int b = -96;
        private volatile int c = 75;
        private volatile int d = 45;
        private volatile int e = 6; // _
        private volatile int f = -124;
        private volatile int g = 2; // a
        private volatile int h = -6; // b
        private volatile int i = -1;
        private volatile int j = 8; // d
        private volatile int k = 34;
        private volatile int l = -98;
        private volatile int m = 323;
        private volatile int n = -643;
        private volatile int o = 873;
        private volatile int p = -945;
        private volatile int q = -13;
        private volatile int r = -81;
        private volatile int s = 37;
        private volatile int t = 12;
        private volatile int u = 98;
        private volatile int v = -123;
        private volatile int w = 862;
        private volatile int x = 432;
        private volatile int y = -103;
        private volatile int z = -24;
    }


    @Override
    public boolean verify(String password) throws Exception {
        CharToNum chars = new CharToNum();
        char[] pw = password.substring(15).toCharArray();
        int i = 234;
        int sum = 0;

        for (char c : pw) {
            Field f = chars.getClass().getDeclaredField(String.valueOf((char) (c + 6)));
            f.setAccessible(true);
            sum += (Integer) f.get(chars);
            sum *= (i / 15);
            i *= c * 88999;

            if (i < 0) Bootstrap.segfault();
        }

        return i == 737725488 && sum == 1273637970;
    }
}
