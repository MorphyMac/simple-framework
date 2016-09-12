package com.tylersuehr.security;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class MD5DESTest {
    @Test
    public void testCryptography() {
        String original = "Hello, my name is Tyler!";
        MD5DES crypt = new MD5DES();

        String encrypted = crypt.encrypt(original);
        assertNotEquals(original, encrypted);

        String decrypted = crypt.decrypt(encrypted);
        assertEquals(original, decrypted);
    }
}