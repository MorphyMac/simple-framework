package com.tylersuehr;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public final class TextUtils {
    public static boolean isEmpty(CharSequence text) {
        return (text == null || text.length() <= 0);
    }
}