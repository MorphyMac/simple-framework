package com.tylersuehr.security;
import com.tylersuehr.Log;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * We will use this to encrypt and decrypt our passwords. It will focus on using
 * a standardized 128bit AES cryptography method.
 */
public final class AES implements ICryptography {
    private static final String TAG = "AES";
    private static final String DEFAULT_IV = "~2>_5~4%8c-9=8f~";
    private final String iv;
    private final String key;


    public AES(String key) {
        // Prevent from entering a key that is less than 128 bit
        if (key.length() < 16) {
            Hasher hasher = Hasher.using(Hasher.SHA256);
            key = hasher.hash(key).substring(0, 16); // 16*8 = 128
        }
        this.key = key;
        this.iv = DEFAULT_IV;
    }

    public AES(String key, String iv) {
        // Prevent from entering a key & iv that is less than 128 bits
        if (key.length() < 16) {
            Hasher hasher = Hasher.using(Hasher.SHA256);
            key = hasher.hash(key).substring(0, 16); // 16*8 = 128
            if (iv.length() < 16) {
                iv = hasher.hash(iv).substring(0, 16); // 16*8 = 128
            }
        }
        this.key = key;
        this.iv = iv;
    }

    @Override
    public String encrypt(String data) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            Log.e(TAG, "Couldn't encrypt!", ex);
        }
        return null;
    }

    @Override
    public String decrypt(String data) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));
            byte[] original = cipher.doFinal(Base64.decode(data, Base64.DEFAULT));
            return new String(original);
        } catch (Exception ex) {
            Log.e(TAG, "Couldn't decrypt!", ex);
        }
        return null;
    }
}