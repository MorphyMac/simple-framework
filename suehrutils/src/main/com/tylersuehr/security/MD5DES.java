package com.tylersuehr.security;
import com.tylersuehr.Log;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * We will use this to encrypt and decrypt our passwords. It will focus on using
 * a small combination of MD5 with DES cryptography with 15 iterations.
 */
public final class MD5DES implements ICryptography {
    private static final String KEY = "671885af-43a7-40b7-887e-8bf98dd925bf";
    private static final String TAG = "CRYPT";
    private Cipher cipher1;
    private Cipher cipher2;


    public MD5DES() {
        try {
            // Create 8-byte salt
            byte[] saltBytes = {
                    (byte)0xA9, (byte)0x9B, (byte)0xC8, (byte)0x32,
                    (byte)0x56, (byte)0x34, (byte)0xE3, (byte)0x03
            };
            int iterations = 15;

            // Get specs for creating our key & use it to create our private key
            KeySpec spec = new PBEKeySpec(KEY.toCharArray(), saltBytes, iterations);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(spec);

            // Create our encryption & decryption ciphers
            cipher1 = Cipher.getInstance(key.getAlgorithm());
            cipher2 = Cipher.getInstance(key.getAlgorithm());

            // Generate the algorithm specs
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(saltBytes, iterations);
            cipher1.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            cipher2.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } catch (Exception ex) {
            final String inf = "Couldn't instantiate!";
            Log.e(TAG, inf, ex);
            throw new RuntimeException(inf);
        }
    }

    /**
     * Encrypts a string.
     * @param text String
     * @return Encrypted string
     */
    @Override
    public String encrypt(String text) {
        try {
            // Encode to bytes with utf8
            byte[] utf8 = text.getBytes("UTF-8");

            // Encrypt bytes
            byte[] encrypted = cipher1.doFinal(utf8);

            // Encode to string using base64
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            Log.e(TAG, "Couldn't encrypt password!", ex);
        }
        return null;
    }

    /**
     * Decrypts a string.
     * @param text String
     * @return Decrypted string
     */
    @Override
    public String decrypt(String text) {
        try {
            // Decode string from utf8
            byte[] decrypt = Base64.decode(text, Base64.DEFAULT);

            // Decrypt bytes
            byte[] utf8 = cipher2.doFinal(decrypt);

            // Get string encoded with utf8
            return new String(utf8, "UTF-8");
        } catch (Exception ex) {
            Log.e(TAG, "Couldn't decrypt password!", ex);
        }
        return null;
    }
}