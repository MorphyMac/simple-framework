package com.tylersuehr.security;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class AESTest {
    @Test
    public void testCryptography() {
        final String KEY = "test_key";
        String original = "Hello there!!!!";
        AES crypt = new AES(KEY);

        String encrypted = crypt.encrypt(original);
        assertNotEquals(original, encrypted);

        String decrypted = crypt.decrypt(encrypted);
        assertEquals(original, decrypted);
    }
}