package com.tylersuehr.security;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class HasherTest {
    @Test
    public void testSHA1() {
        String normal = "Hello!";
        String hashed = Hasher.using(Hasher.SHA1).hash(normal);
        assertNotEquals(normal, hashed);
    }

    @Test
    public void testSHA256() {
        String normal = "Hello!";
        String hashed = Hasher.using(Hasher.SHA256).hash(normal);
        assertNotEquals(normal, hashed);
    }

    @Test
    public void testSHA384() {
        String normal = "Hello!";
        String hashed = Hasher.using(Hasher.SHA384).hash(normal);
        assertNotEquals(normal, hashed);
    }

    @Test
    public void testSHA512() {
        String normal = "Hello!";
        String hashed = Hasher.using(Hasher.SHA512).hash(normal);
        assertNotEquals(normal, hashed);
    }

    @Test
    public void testMD2() {
        String normal = "Hello!";
        String hashed = Hasher.using(Hasher.MD2).hash(normal);
        assertNotEquals(normal, hashed);
    }

    @Test
    public void testMD5() {
        String normal = "Hello!";
        String hashed = Hasher.using(Hasher.MD5).hash(normal);
        assertNotEquals(normal, hashed);
    }
}