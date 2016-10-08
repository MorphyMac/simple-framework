package com.tylersuehr;
import java.util.Arrays;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public final class TextUtils {
    public static boolean isEmpty(CharSequence text) {
        return (text == null || text.length() <= 0);
    }

    public static String toPassword(char[] password) {
        return Arrays.toString(password).replace("[", "")
                .replace("]", "").replace(", ", "");
    }
}