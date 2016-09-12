package com.tylersuehr.storage;
import com.tylersuehr.security.ICryptography;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This will be used to get valid instances of our {@link SharedPreferences} object.
 * This contains methods to get both secured (MD5&DES) and unsecured preferences.
 */
public final class PreferenceManager {
    public static ISharedPreferences getPreferences(String name) {
        return new SharedPreferences(name);
    }

    public static ISharedPreferences getSecurePreferences(String name, ICryptography method) {
        return new SharedPreferences(name, method);
    }
}