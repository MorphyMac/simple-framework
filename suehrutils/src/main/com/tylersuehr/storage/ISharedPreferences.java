package com.tylersuehr.storage;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This is an abstraction for what we will use to manage
 * preferences in a key-value pair with.
 */
public interface ISharedPreferences {
    /**
     * Gets a string value using preference name.
     * @param pref Preference name
     * @return String
     */
    String getString(String pref);

    /**
     * Gets an integer value using preference name.
     * @param pref Preference name
     * @param def Default value
     * @return Integer
     */
    int getInt(String pref, int def);

    /**
     * Gets a long value using preference name.
     * @param pref Preference name
     * @param def Default value
     * @return Long
     */
    long getLong(String pref, long def);

    /**
     * Gets a float value using preference name.
     * @param pref Preference name
     * @param def Default value
     * @return Float
     */
    float getFloat(String pref, float def);

    /**
     * Gets a double value using preference name.
     * @param pref Preference name
     * @param def Default value
     * @return Double
     */
    double getDouble(String pref, double def);

    /**
     * Gets a boolean value using preference name.
     * @param pref Preference name
     * @param def Default value
     * @return Boolean
     */
    boolean getBoolean(String pref, boolean def);

    /**
     * Gets an instance of the {@link IEditor} to allow
     * changes in com.tylersuehr.data.
     * @return {@link IEditor} instance
     */
    IEditor edit();


    interface IEditor {
        IEditor put(String pref, String value);
        IEditor put(String pref, int value);
        IEditor put(String pref, long value);
        IEditor put(String pref, float value);
        IEditor put(String pref, double value);
        IEditor put(String pref, boolean value);

        /**
         * Finalize and save changes made asynchronously.
         */
        void apply();

        /**
         * Finalize and save changes made.
         */
        void commit();
    }
}