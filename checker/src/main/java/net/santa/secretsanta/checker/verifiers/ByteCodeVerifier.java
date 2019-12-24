package net.santa.secretsanta.checker.verifiers;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import net.santa.secretsanta.checker.password.HighTechChecker;
import net.santa.secretsanta.checker.password.TestChecker;

import java.lang.reflect.Field;

// Hints:
// Hint 1: This verifier adds bytecode to the TestChecker class. If you supply the correct password part, the
//         bytecode added to the class will satisfy all requirements in the final if-statement.
// Hint 2: The code uses the 5th char after the pass char to find the bytecode. Any bytecode not changing all variables
//         is not part of the password, because it would not change the variable enough.
// Hint 3: The password consists of 4 words, in the following format 'xxxx_yyyy_zzzzz_ttt'. This part only tests
//         the middle word (zzzzz)
// Hint 4: The password should contain '_train' in the middle of it.

/**
 * A nice piece of code, which modifies a class at runtime and then loads it. It's almost like reflection++.
 * This one is similar to the previous one, but it is probably it bit more challenging.
 *
 * Since the code is relatively straightforward, the hints might not be as useful as you maybe hoped. Nevertheless,
 * there are still four hints available.
 *
 * Good luck on the last 'real' verifier!
 *
 * - Secret Santa
 */
public class ByteCodeVerifier implements Verifier {
    private volatile String a = "{ yes /= 761; no *= -2233; maybe = !maybe; return yes < 8723874; }";
    private volatile String b = "{ return false; maybe = !maybe; }";
    private volatile String c = "{ return true; }";
    private volatile String d = "{ yes += 1841; no -= 738; maybe = check(); }"; // _
    private volatile String e = "{ yes *= -54; no += -48; return no > 8723874; }";
    private volatile String f = "{ yes /= 95; no *= 138; maybe = !maybe; maybe = check(); }"; // a
    private volatile String g = "{ return true; }";
    private volatile String h = "{ yes *= 183452; no >>= 16; maybe = !maybe; return maybe; }";
    private volatile String i = "{ return true; }";
    private volatile String j = "{ yes += 18; no *= -23; maybe = !maybe; maybe = check(); }";
    private volatile String k = "{ yes += 18; no *= -23; maybe = check(); }";
    private volatile String l = "{ yes -= 23769; no *= 23; maybe = !maybe; maybe = check(); }";
    private volatile String m = "{ yes /= -9; no *= -1246; maybe = check(); }";
    private volatile String n = "{ yes *= 21; no <<= 19; maybe = !maybe; return maybe; }"; // i
    private volatile String o = "{ yes <<= 8; no ^= 1323; }";
    private volatile String p = "{ yes >>= 4; no &= 8312; maybe = !maybe; }";
    private volatile String q = "{ return true; }";
    private volatile String r = "{ return false; }";
    private volatile String s = "{ yes <<= 9; no -= 9842; return (yes / 42) < 42; }"; // n
    private volatile String t = "{ return false; }";
    private volatile String u = "{ yes >>= 1; no &= 123; maybe = !maybe; return maybe;}";
    private volatile String v = "{ return false; }";
    private volatile String w = "{ yes -= 141234; no += 826; return maybe;}"; // r
    private volatile String x = "{ yes <<= 18; no *= 836098; maybe = !maybe; maybe = check(); }";
    private volatile String y = "{ yes ^= 2348789; no /= 26; maybe = !maybe; maybe = check(); }"; // t
    private volatile String z = "{ return false; maybe = !maybe; return maybe;}";


    @Override
    public boolean verify(String password) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass checker = pool.get("net.santa.secretsanta.checker.password.TestChecker");

        checker.defrost();

        CtMethod method = checker.getDeclaredMethod("checkPassword");
        CtMethod check = checker.getDeclaredMethod("check");

        char[] pw = password.substring(9, 15).toCharArray();

        for (char c : pw) {
            Field f = this.getClass().getDeclaredField(String.valueOf((char) (c + 5)));
            f.setAccessible(true);
            String instruction = (String) f.get(this);

            if (instruction.contains("check()")) {
                method.insertBefore(instruction);
            } else {
                check.insertBefore(instruction);
            }
        }

        checker.toClass(HighTechChecker.class);

        return new TestChecker().checkPassword(password, 0);
    }
}
