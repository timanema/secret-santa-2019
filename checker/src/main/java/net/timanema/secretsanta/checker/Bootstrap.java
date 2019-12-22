package net.timanema.secretsanta.checker;

import net.timanema.secretsanta.checker.password.HighTechChecker;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.util.Optional;

public class Bootstrap {
    public static void main(String... args) throws Exception {
        String password = Optional.ofNullable(System.getProperty("pass")).orElse("home_good");
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
