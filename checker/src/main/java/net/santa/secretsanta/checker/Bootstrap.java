package net.santa.secretsanta.checker;

import net.santa.secretsanta.checker.password.HighTechChecker;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.util.Optional;

/**
 * This is where it all starts. You can look around here, but there isn't much to see.
 *
 * - Secret Santa
 */
public class Bootstrap {
    //TODO: Remove all non-javadoc comments for the final version
    public static void main(String... args) throws Exception {
        String password = System.getProperty("pass");
        int part = Integer.parseInt(Optional.ofNullable(System.getProperty("part")).orElse("1"));

        System.exit(new HighTechChecker().checkPassword(password, part) ? 0 : 1);
    }

    public static void segfault() {
        try {
            final Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();
            unsafeConstructor.setAccessible(true);
            final Unsafe unsafe = unsafeConstructor.newInstance();

            System.out.println(unsafe.getAddress(0));
        } catch (Exception e) {
            System.out.println("Yes java, lets make sure I catch the segfault");
        }
    }
}
