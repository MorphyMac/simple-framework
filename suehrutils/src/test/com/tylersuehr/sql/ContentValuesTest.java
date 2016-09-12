package com.tylersuehr.sql;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class ContentValuesTest {
    @Test
    public void testContentValuesPut() {
        ContentValues values = create();
        assertNotNull(values);
    }

    @Test
    public void testContentValuesGet() {
        ContentValues values = create();

        Set<String> keys = values.getKeys();
        Collection<Object> objects = values.getValues();

        assertNotNull(keys);
        assertNotNull(objects);
    }

    private ContentValues create() {
        ContentValues values = new ContentValues();
        values.put("id", UUID.randomUUID().toString());
        values.put("name", "Tyler");
        values.put("email", "tylersuehr7@yahoo.com");
        values.put("isFavorite", true);
        return values;
    }
}