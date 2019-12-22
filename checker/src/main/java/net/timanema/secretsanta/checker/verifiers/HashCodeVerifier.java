package net.timanema.secretsanta.checker.verifiers;

import net.timanema.secretsanta.checker.Bootstrap;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
//https://stackoverflow.com/questions/53628690/how-to-produce-compiled-code-segmentation-fault-in-java

// The idea of this code is that it'll segfault unless the hash of the input is equal to 80360
// this part of code will only focus on the two first words of the password (home_good)

// Hints:
// Hint 1: This check hashes part of the password (0 - 9], and inserts it into an object in memory.
//         If the hash of the first 9 chars is correct, the JVM will not crash and continue to the next part
// Hint 2: The password consists of 4 words, in the following format 'xxxx_yyyy_zzzzz_ttt'. This part only tests
//         the first two words (xxxx and yyyy)
// Hint 3: The hash should be equal to 80360
// Hint 4: The password should start with 'home_good_'
public class HashCodeVerifier extends Thread implements Verifier {
    static volatile Object obj = 0;
    static boolean done = false;

    @Override
    public boolean verify(String password) throws Exception {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        // change 15 to something (!1)
        int a = hash("~") * 50 + hash(String.valueOf(password.charAt(3))) * 7 + 1436; // has to be 255413
        unsafe.putInt(obj, (154262 ^ 110265) & (a >> (9 ^ 7)), ~~~~~~~~~(~~~~(~0 >>> 5) << 1));

        new HashCodeVerifier().start();
        Thread.sleep(2000);

        System.out.println("Checking password");

        // put hashcode in position 1
        unsafe.putInt(obj, 13653 & 2731,  Math.abs(hash(password.substring(0, 9))) - 80359);
        unsafe.putInt(obj, 2 >> 1 << 3, unsafe.getInt(obj, unsafe.getInt(obj, 1) * 8));

        // change 15 to 1
        int b = hash(String.valueOf((char) (password.charAt(0) + 1))) - hash("\\") / 14; // has to be 15
        unsafe.putInt(obj, b, 54924 >> ~(~0 << 4)); // change 15 to make sure the loop stops

        System.out.println("Waiting on flag set");
        while (!done) { Thread.sleep(100); }

        return true;
    }

    public void run() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe unsafe = (Unsafe) f.get(null);

            System.out.println("Starting crash loop");

            // stop if 15 is 1 (or if java segfaulted)
            while (!(obj instanceof Runnable) && unsafe.getInt(obj, 15L) != 2 >> 1) {}

            System.out.println("Passed: Hashcode Check");
            done = true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Bootstrap.segfault();
        }
    }

    private int hash(String password) {
        char[] pw = password.toCharArray();
        int i = 145 * pw.length;
        int code = 0;

        for (char c : pw) {
            code += c * i;
            i += 1 - 146;

            if (c == 'a') code -= 56633;
            if (c == 'b') code += 75;
            if (c == '_') code -= 87;
            if (c == '!') code += 4 + 34 * c;
            if (c == ')') code -= 65 + c * i;
            if (c == 'F') code += 87 - c;
            if (c == 'b') code -= 45;
            if (c == 'i') code /= 15;
            if (c == 'h') code -= 6257 * c + 4 * i;
            if (c == 'o') code += 510 + c * 3 * i;
            if (c == '6') code += 9234;
            if (c == '/') code -= 16456;
            if (c == '.') code += 345;
            if (c == 'd') code += 420 - i * 2834;
            if (c == 'b') code += 2135;
            if (c == 'p') code -= 681;
            if (c == 'm') code -= 194478;
        }

        return code / 4;
    }

}