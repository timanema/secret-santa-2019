package net.timanema.secretsanta.wrapper;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

public class Bootstrap {
    public static void main(String... args) throws Exception {
        Console console = System.console();

        if (console == null) {
            System.out.println("Are you running this from an interactive terminal?");
            return;
        }

        String password;

        while (true) {
            System.out.print("Password: ");
            password = console.readLine().trim();

            if (launch(password, 1) && launch(password, 2) && launch(password, 3)) {
                System.out.println("Correct password: " + password);
                break;
            } else {
                System.out.println("Incorrect password: " + password);
            }
        }
    }

    private static boolean launch(String password, int part) throws Exception {
        int exit =  runProcess("java -jar -Dpass=" + password + " -Dpart=" + part + " " + new File(Bootstrap.class
                .getProtectionDomain().getCodeSource().getLocation().toURI()).getPath()
                .replace("ss-wrapper.jar", "ss-checker.jar"));

        if (exit == 0) {
            System.out.println("Check " + part + " completed");
            return true;
        } else {
            System.out.println("Check " + part + " failed");
            return false;
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

        pro.waitFor();

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
