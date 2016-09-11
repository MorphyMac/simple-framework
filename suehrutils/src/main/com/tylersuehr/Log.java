package com.tylersuehr;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * Allows us to conveniently log information or errors fast.
 */
public final class Log {
    /**
     * Logs an error.
     * @param tag Tag of the message
     * @param message Message to log
     * @param ex Exception thrown
     */
    public static void e(String tag, String message, Exception ex) {
        System.err.println(tag + " > " + message);
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    }

    /**
     * Logs information.
     * @param tag Tag of the message
     * @param message Message to log
     */
    public static void i(String tag, String message) {
        System.out.println(tag + " > " + message);
    }
}