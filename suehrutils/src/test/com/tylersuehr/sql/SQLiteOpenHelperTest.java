package com.tylersuehr.sql;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class SQLiteOpenHelperTest {
    private static final String DB = "test_db2.db";
    private static final int VERSION = 2;


    @Test
    public void testSQLiteOpenHelper() {
        DatabaseHelper helper = new DatabaseHelper();
        SQLiteDatabase db = helper.getWritableInstance();

        assertNotNull(db);

        db.close();
    }

    @Test
    public void testSQLiteOpenHelper2() {
        DatabaseHelper2 helper = new DatabaseHelper2();
        SQLiteDatabase db = helper.getWritableInstance();

        assertNotNull(db);

        db.close();
    }


    private class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper() {
            super(DB, VERSION);
        }

        @Override
        protected void onCreate(SQLiteDatabase db) {
            db.execSql("CREATE TABLE [users] (" +
                    "[id] INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "[name] TEXT NOT NULL" +
                    ");");
        }

        @Override
        protected void onUpdate(SQLiteDatabase db, int oldV, int newV) {
            db.execSql("DROP TABLE IF EXISTS [users]");
            onCreate(db);
        }
    }

    private class DatabaseHelper2 extends SQLiteOpenHelper2 {
        DatabaseHelper2() {
            super(DB, VERSION);
        }

        @Override
        protected void onCreate(SQLiteDatabase db) {
            db.execSql("CREATE TABLE [users] (" +
                    "[id] INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "[name] TEXT NOT NULL" +
                    ");");
        }

        @Override
        protected void onUpdate(SQLiteDatabase db, int oldV, int newV) {
            db.execSql("DROP TABLE IF EXISTS [users]");
            onCreate(db);
        }
    }
}