package com.tylersuehr.security;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public interface ICryptography {
    String encrypt(String data);
    String decrypt(String data);
}