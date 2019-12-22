package net.santa.secretsanta.wrapper.secure;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * This class allows me to dynamically tell you things when you progress through the verifiers, without
 * giving away the answers prematurely.
 * <p>
 * You can take a shot at cracking the encryption (AES CBC), but even if there are severe vulnerabilities in this
 * part of the code, I think it's easier to focus on the verifiers.
 * <p>
 * - Secret Santa
 */
public class MessageDecrypt {
    private String key;
    private String initVector;

    public MessageDecrypt(String key) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(key.toCharArray(), "salt".getBytes(), 1024, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            this.key = Base64.getEncoder().encodeToString(secret.getEncoded());
            this.initVector = "jg7dbA9#uk_W64jg";
        } catch (Exception e) {
            System.out.println("Well this is awkward, the wrapper could not handle your input. " +
                    "You can assume whatever whatever you tried was wrong." +
                    "" +
                    "- Secret Santa");
        }
    }

    public String decrypt(String message) throws Exception{
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes());
        SecretKeySpec secret = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

        cipher.init(Cipher.DECRYPT_MODE, secret, iv);
        return new String(cipher.doFinal(Base64.getDecoder().decode(message)));
    }
}
