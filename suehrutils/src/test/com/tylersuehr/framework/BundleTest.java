package com.tylersuehr.framework;
import org.junit.Assert;
import org.junit.Test;
import java.io.Serializable;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class BundleTest {
    @Test
    public void testString() {
        Bundle b = new Bundle();
        b.putString("test", "value");

        Assert.assertEquals("value", b.getString("test"));
    }

    @Test
    public void testInt() {
        Bundle b = new Bundle();
        b.putInt("test", 4);

        Assert.assertEquals(4, b.getInt("test"));
    }

    @Test
    public void testLong() {
        Bundle b = new Bundle();
        b.putLong("test", 4L);

        Assert.assertEquals(4L, b.getLong("test"));
    }

    @Test
    public void testFloat() {
        Bundle b = new Bundle();
        b.putFloat("test", 4f);

        Assert.assertTrue(4f == b.getFloat("test"));
    }

    @Test
    public void testDouble() {
        Bundle b = new Bundle();
        b.putDouble("test", 4.3);

        Assert.assertTrue(4.3 == b.getDouble("test"));
    }

    @Test
    public void testBoolean() {
        Bundle b = new Bundle();
        b.putBoolean("test", true);

        Assert.assertEquals(true, b.getBoolean("test"));
    }

    @Test
    public void testSerializable() {
        Person p = new Person("Jim Johnson");

        Bundle b = new Bundle();
        b.putSerializable("test", p);

        Assert.assertNotNull(b.getSerializable("test"));
        Assert.assertEquals("Jim Johnson", ((Person)b.getSerializable("test")).name);
    }

    private class Person implements Serializable {
        String name;


        public Person(String name) {
            this.name = name;
        }
    }
}