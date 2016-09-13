package com.tylersuehr.sql;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.UUID;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class SQLBuilderTest {
    @Test
    public void testQuery() {
        // SELECT * FROM [table] WHERE [col] = value ORDER BY [col] LIMIT 0;
        final String SQL = "SELECT * FROM [users] WHERE [name]='Tyler Suehr';";

        String selection = "[name]='Tyler Suehr'";
        String sql = new SQLBuilder().createQuery("users", selection, null, null);
        assertEquals(SQL, sql);
    }

    @Test
    public void testQueryCols() {
        // SELECT (cols) FROM [table] WHERE [col] = value ORDER BY [col] LIMIT 0;
        final String SQL = "SELECT ([name],[username]) FROM [users] WHERE [name]='Tyler Suehr';";

        String[] cols = new String[] { "name", "username" };
        String selection = "[name]='Tyler Suehr'";
        String sql = new SQLBuilder().createQuery("users", cols, selection, null, null);
        assertEquals(SQL, sql);
    }

    @Test
    public void testInsert() {
        // INSERT INTO [table] ([col1],[col2],[col3],[col4]) VALUES ('test', 'test2', 123, 12.123);
        final String ID = UUID.randomUUID().toString();
        final String SQL = "INSERT INTO [users] ([id],[username],[height],[age],[name]) VALUES ('" + ID + "','tylersuehr7',5.9,20,'Tyler Suehr');";

        final ContentValues values = new ContentValues();
        values.put("id", ID);
        values.put("name", "Tyler Suehr");
        values.put("username", "tylersuehr7");
        values.put("age", 20);
        values.put("height", 5.9);

        String sql = new SQLBuilder().createInsert("users", values);
        assertEquals(SQL, sql);
    }

    @Test
    public void testUpdate() {
        // UPDATE [table] SET [col1] = 'test' WHERE [col2] = 3;
        final String ID = UUID.randomUUID().toString();
        final String SQL = "UPDATE [users] SET [id]='" + ID + "',[username]='tylersuehr7',[name]='Jim Johnson' WHERE [username]='tylersuehr7';";

        final ContentValues values = new ContentValues();
        values.put("id", ID);
        values.put("name", "Jim Johnson");
        values.put("username", "tylersuehr7");

        String sql = new SQLBuilder().createUpdate("users", values, "[username]='tylersuehr7'");
        assertEquals(SQL, sql);
    }

    @Test
    public void testDelete() {
        // DELETE FROM [table] WHERE [col1] = 23;
        final String ID = UUID.randomUUID().toString();
        final String SQL = "DELETE FROM [users] WHERE [id]='" + ID + "';";

        String sql = new SQLBuilder().createDelete("users", "[id]='" + ID + "'");
        assertEquals(SQL, sql);
    }

    @Test
    public void testDeleteAll() {
        // DELETE FROM [users];
        final String SQL = "DELETE FROM [users];";

        String sql = new SQLBuilder().createDelete("users", null);
        assertEquals(SQL, sql);
    }
}