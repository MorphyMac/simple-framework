package com.tylersuehr.security;
import com.tylersuehr.Log;
import java.security.MessageDigest;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This utility is to allow you to hash something using top standard hashing algorithms.
 *
 * NOTE: If you need to "un-hash" something... use {@link ICryptography} instead of this.
 */
public final class Hasher {
    private static volatile Hasher instance;
    private static final String TAG = "HASH";
    public static final int SHA1 = 0;
    public static final int SHA256 = 1;
    public static final int SHA384 = 2;
    public static final int SHA512 = 3;
    public static final int MD2 = 4;
    public static final int MD5 = 5;
    private String algorithm;


    private Hasher() {}
    public static Hasher using(int algorithm) {
        if (instance == null) {
            synchronized (Hasher.class) {
                if (instance == null) {
                    instance = new Hasher();
                    instance.algorithm = instance.getAlgorithm(algorithm);
                }
            }
        }
        return instance;
    }

    public String hash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }

            return sb.toString();
        } catch (Exception ex) {
            Log.e(TAG, "Couldn't hash!", ex);
        }
        return null;
    }

    private String getAlgorithm(int algorithm) {
        switch (algorithm) {
            case SHA1:
                return "SHA-1";
            case SHA256:
                return "SHA-256";
            case SHA384:
                return "SHA-384";
            case SHA512:
                return "SHA-512";
            case MD2:
                return "MD2";
            case MD5:
                return "MD5";
        }
        throw new  IllegalStateException("Invalid algorithm type!");
    }
}