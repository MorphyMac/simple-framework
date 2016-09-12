package com.tylersuehr.sql;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This wraps a {@link HashMap} map.
 *
 * We need to explicitly define the 'set' methods instead of using the map
 * directly because we only want to store certain data types in the SQLite database:
 *      - String, int, long, float, double, and boolean
 */
public final class ContentValues {
    private Map<String, Object> map;


    public ContentValues() {
        this.map = new HashMap<>();
    }

    public void put(String name, String value) {
        this.map.put(name, value);
    }

    public void put(String name, int value) {
        this.map.put(name, value);
    }

    public void put(String name, long value) {
        this.map.put(name, value);
    }

    public void put(String name, float value) {
        this.map.put(name, value);
    }

    public void put(String name, double value) {
        this.map.put(name, value);
    }

    public void put(String name, boolean value) {
        this.map.put(name, value);
    }

    Set<String> getKeys() {
        return map.keySet();
    }

    Collection<Object> getValues() {
        return map.values();
    }

    Object get(String key) {
        return map.get(key);
    }
}