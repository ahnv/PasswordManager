package com.ahnv.crypt;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by Abhinav.
 */
public class CryptionHelper {
    private static Random rand = new Random((new Date()).getTime());

    public static String encrypt(String str) {
        BASE64Encoder encoder  = new BASE64Encoder();
        byte[] salt = new byte[8];
        rand.nextBytes(salt);
        return encoder.encode(salt) + encoder.encode(str.getBytes());
    }

    public static String decrypt(String encstr){
        if (encstr.length() > 12) {
            String cipher = encstr.substring(12);
            BASE64Decoder decoder = new BASE64Decoder();
            try{ return new String(decoder.decodeBuffer(cipher));}
            catch (IOException e){ System.out.println(e); }
        }
        return null;
    }

    public static void main(String[] args) {
        CryptionHelper cryptionHelper = new CryptionHelper();
        String str = "thisisatestpassword";
        String enc = "FJPMQ7yLP9A=dGVzdA==";
        System.out.println(enc);
        CryptionHelper cryptionHelper1 = new CryptionHelper();
        System.out.println(cryptionHelper1.decrypt(enc));
    }
}
