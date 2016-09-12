package com.tylersuehr.storage;
import static org.junit.Assert.*;
import com.tylersuehr.security.AES;
import org.junit.Test;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class SharedPreferencesTest {
    private final String TEST_SECURE_PATH = "test_secure_prefs";
    private final String TEST_PATH = "test_prefs";


    @Test
    public void testWriteReadPreferences() {
        String name = "Tyler Suehr";
        int count = 2;
        long lCount = 2L;
        float fCount = 4f;
        double dCount= 5.2;
        boolean ttyl = true;

        SharedPreferences prefs = new SharedPreferences(TEST_PATH);
        prefs.edit()
                .put("name", name)
                .put("count", count)
                .put("lCount", lCount)
                .put("fCount", fCount)
                .put("dCount", dCount)
                .put("ttyl", ttyl)
                .commit();

        assertNotNull(prefs.getString("name"));
        assertTrue(prefs.getInt("count", 0) != 0);
        assertTrue(prefs.getLong("lCount", 0) != 0);
        assertTrue(prefs.getFloat("fCount", 0) != 0);
        assertTrue(prefs.getDouble("dCount", 0) != 0);
        assertTrue(prefs.getBoolean("ttyl", false));
    }

    @Test
    public void testWriteReadSecurePreferences() {
        final String KEY = "secure_prefs_key";

        String name = "Tyler Suehr";
        int count = 2;
        long lCount = 2L;
        float fCount = 4f;
        double dCount= 5.2;
        boolean ttyl = true;

        SharedPreferences prefs = new SharedPreferences(TEST_SECURE_PATH, new AES(KEY));
        prefs.edit()
                .put("name", name)
                .put("count", count)
                .put("lCount", lCount)
                .put("fCount", fCount)
                .put("dCount", dCount)
                .put("ttyl", ttyl)
                .commit();

        assertNotNull(prefs.getString("name"));
        assertTrue(prefs.getInt("count", 0) != 0);
        assertTrue(prefs.getLong("lCount", 0) != 0);
        assertTrue(prefs.getFloat("fCount", 0) != 0);
        assertTrue(prefs.getDouble("dCount", 0) != 0);
        assertTrue(prefs.getBoolean("ttyl", false));
    }
}