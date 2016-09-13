package com.tylersuehr.sql;
import static org.junit.Assert.*;
import com.tylersuehr.concurrent.LocalThreadFactory;
import org.junit.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ThreadFactory;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class SQLiteDatabaseTest {
    private final String DB_TEST_NAME = "test_db.db";


    @Test
    public void testSQLiteConcurrency() {
        ThreadFactory threadFactory = new LocalThreadFactory();
        threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db2 = new SQLiteDatabase(DB_TEST_NAME);
                for (int i = 0; i < 20; i++) {
                    db2.execSql("INSERT INTO [people] values ('" + UUID.randomUUID().toString() + "','person_" + i + "');");
                }
                db2.close();
            }
        }).start();

        SQLiteDatabase db = new SQLiteDatabase(DB_TEST_NAME);
        for (int i = 0; i < 30; i++) {
            db.execSql("INSERT INTO [users] values ('" + UUID.randomUUID().toString() + "','users_" + i + "');");
        }
        db.close();
    }

    @Test
    public void testSQLiteQuery() throws SQLException {
        SQLiteDatabase db = new SQLiteDatabase(DB_TEST_NAME);

        String selection = "[id]='350cf046-0d57-46fe-a3cf-6f05ef889765'";
        ResultSet c = db.query("people", selection, null, null);
        assertNotNull(c);

        String name = c.getString("name");
        assertNotNull(name);

        c.close();
        db.close();
    }

    @Test
    public void testSQLiteRawSql() throws SQLException {
        SQLiteDatabase db = new SQLiteDatabase(DB_TEST_NAME);

        String sql = "SELECT * FROM [users];";
        ResultSet c = db.rawQuery(sql);
        assertNotNull(c);

        String name = c.getString("name");
        assertNotNull(name);

        c.close();
        db.close();
    }

    @Test
    public void testSQLiteInsert() {
        SQLiteDatabase db = new SQLiteDatabase(DB_TEST_NAME);

        ContentValues values = new ContentValues();
        values.put("id", UUID.randomUUID().toString());
        values.put("name", "Tyler Suehr");

        db.insert("users", values);
        db.close();
    }

    @Test
    public void testSQLiteUpdate() {
        SQLiteDatabase db = new SQLiteDatabase(DB_TEST_NAME);
        final String ID = UUID.randomUUID().toString();

        ContentValues values = new ContentValues();
        values.put("id", ID);
        values.put("name", "Tyler Suehr");

        db.insert("people", values);

        values = new ContentValues();
        values.put("id", ID);
        values.put("name", "John Doe");
        db.update("people", values, "[id]='" + ID + "'");
        db.close();
    }

    @Test
    public void testSQLiteDelete() {
        SQLiteDatabase db = new SQLiteDatabase(DB_TEST_NAME);
        final String ID = UUID.randomUUID().toString();

        ContentValues values = new ContentValues();
        values.put("id", ID);
        values.put("name", "Jimmy Johnson");

        db.insert("people", values);
        db.delete("people", "[id]='" + ID + "'");
        db.close();
    }

    @Test
    public void testSQLiteGetVersion() {
        SQLiteDatabase db = new SQLiteDatabase(DB_TEST_NAME);

        int version = db.getVersion();

        db.close();
        assertTrue(version > -1);
    }

    @Test
    public void testSQLiteSetVersion() {
        SQLiteDatabase db = new SQLiteDatabase(DB_TEST_NAME);

        int version = db.getVersion();
        version++;

        db.setVersion(version);
        int newVersion = db.getVersion();

        db.setVersion(0);

        db.close();
        assertEquals(version, newVersion);
    }
}