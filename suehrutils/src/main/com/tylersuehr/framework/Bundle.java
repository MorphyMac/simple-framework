package com.tylersuehr.framework;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * Basic {@link Map} wrapper that only lets us put specified data types into the map.
 */
public final class Bundle {
    private Map<String, Object> map = new HashMap<>();


    public Bundle() {}

    public Bundle putString(String key, String value) {
        this.map.put(key, value);
        return this;
    }

    public Bundle putInt(String key, int value) {
        this.map.put(key, value);
        return this;
    }

    public Bundle putLong(String key, long value) {
        this.map.put(key, value);
        return this;
    }

    public Bundle putFloat(String key, float value) {
        this.map.put(key, value);
        return this;
    }

    public Bundle putDouble(String key, double value) {
        this.map.put(key, value);
        return this;
    }

    public Bundle putBoolean(String key, boolean value) {
        this.map.put(key, value);
        return this;
    }

    public Bundle putSerializable(String key, Serializable value) {
        this.map.put(key, value);
        return this;
    }

    public String getString(String key) {
        return (String)map.get(key);
    }

    public int getInt(String key) {
        return (int)map.get(key);
    }

    public long getLong(String key) {
        return (long)map.get(key);
    }

    public float getFloat(String key) {
        return (float)map.get(key);
    }

    public double getDouble(String key) {
        return (double)map.get(key);
    }

    public boolean getBoolean(String key) {
        return (boolean)map.get(key);
    }

    public Serializable getSerializable(String key) {
        return (Serializable)map.get(key);
    }
}