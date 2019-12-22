package net.santa.secretsanta.wrapper;

import net.santa.secretsanta.wrapper.secure.MessageDecrypt;
import net.santa.secretsanta.wrapper.secure.Messages;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * This program makes it easier for you to work with the checker.
 * You are free to dissect this code, but I'll doubt you'll find anything of use.
 *
 * - Secret Santa
 */
public class Bootstrap {
    public static void main(String... args) {
        Console console = System.console();

        if (console == null) {
            System.out.println("Are you running this from an interactive terminal?");
            System.out.println("\tIf you're using docker, try: docker run -i -t secret-santa");
            System.out.println("\tIf you're using an IDE: don't (IDEs don't give a console, you can still " +
                    "attach a remote debugger should you need it)");
            return;
        }

        String password;

        while (true) {
            System.out.print("Password: ");
            password = console.readLine().trim();

            if (launch(password, 1) && launch(password, 2) && launch(password, 3) && launch(password, 4)) {
                System.out.println("Correct password: " + password);
                break;
            } else {
                System.out.println("Incorrect password: " + password);
            }
        }
    }

    private static boolean launch(String password, int part) {
        try {
            int exit = runProcess("java -jar -Dpass=" + password + " -Dpart=" + part + " " + new File(Bootstrap.class
                    .getProtectionDomain().getCodeSource().getLocation().toURI()).getPath()
                    .replace("ss-wrapper.jar", "ss-checker.jar"));

            if (exit == 0) {
                String msg = printResult(part, part == 1 ? password.substring(0, 9) : part == 3 ? password.substring(9, 15) : part == 2 ? password.substring(15) : password);
                System.out.println("Check " + part + " completed");
                System.out.println(msg);
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Check " + part + " failed");
            return false;
        }
    }

    private static String printResult(int part, String password) {
        try {
            MessageDecrypt messageDecrypt = new MessageDecrypt(password);
            String decrypt = messageDecrypt.decrypt((String) Messages.class.getDeclaredField("CHECK_" + part).get(null));
            String verifier = (String) Messages.class.getDeclaredField("VERIFY_" + part).get(null);

            if (!decrypt.equals(verifier)) {
                throw new Exception();
            }

            return messageDecrypt.decrypt((String) Messages.class.getDeclaredField("SUCCEED_" + part).get(null));
        } catch (Exception e) {
            return "            It appears you managed to complete a verifier, even though you didn't use the intended password part.\n" +
                    "            Since it might be annoying to continue looking for the correct part, even though the verifier accepts it, \n" +
                    "            you can ask me for the intended part. You may also continue looking for the correct part, whatever you prefer.\n" +
                    "            \n" +
                    "            - Secret Santa";
        }
    }

    private static int runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);

        //TODO: Remove debug lines
        boolean debug = Optional.ofNullable(System.getProperty("debug")).isPresent();

        if (debug) {
            printLines(command + " stdout:", pro.getInputStream());
            printLines(command + " stderr:", pro.getErrorStream());
        }

        pro.waitFor(4, TimeUnit.SECONDS);

        if (debug) {
            System.out.println("Exit code checker: " + pro.exitValue());
        }

        return pro.exitValue();
    }

    private static void printLines(String name, InputStream ins) throws Exception {
        String line;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(name + " " + line);
        }
    }
}
