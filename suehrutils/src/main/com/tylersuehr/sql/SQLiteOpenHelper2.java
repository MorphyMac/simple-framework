package com.tylersuehr.sql;
import com.tylersuehr.Log;
import java.io.Closeable;
import java.io.File;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This will help us manage the creation/updating of our database.
 *
 * {@link #onCreate(SQLiteDatabase)} is a callback that will ALWAYS be called
 * when a new database is created!
 *
 * {@link #onUpdate(SQLiteDatabase, int, int)} is a callback that will ALWAYS be
 * called when an existing database is updated!
 *
 * {@link #close()} must ALWAYS be called to clean up the database connection.
 * Remember, leaving the connection open makes the database very vulnerable!
 */
public abstract class SQLiteOpenHelper2 implements Closeable {
    private static final String TAG = "SQLiteHelper2";
    private SQLiteDatabase database;
    private String name;
    private int version;


    public SQLiteOpenHelper2(String dbName, int version) {
        this.name = dbName;
        this.version = version;
    }

    @Override
    public void close() {
        if (database != null) {
            database.close();
        }
    }

    /**
     * Called when a new database will be created.
     * @param db Database being created
     */
    protected abstract void onCreate(SQLiteDatabase db);

    /**
     * Called when an existing database is being updated.
     * @param db Database being updated
     * @param oldV Old database version
     * @param newV New database version
     */
    protected abstract void onUpdate(SQLiteDatabase db, int oldV, int newV);

    /**
     * Gets a valid instance of our SQLiteDatabase.
     */
    public SQLiteDatabase getWritableInstance() {
        // The database is already opened so return it
        if (database != null) {
            return database;
        }

        // We need to check if the database exists
        File file = new File(name);
        if (file.exists()) {
            database = new SQLiteDatabase(name);

            // We need to check if the database needs updated
            int currentVersion = database.getVersion();
            if (version > currentVersion) {
                onUpdate(database, currentVersion, version);
                database.setVersion(version);
                Log.i(TAG, "onUpdate()");
            }
        } else {
            // Since our database doesn't exist yet, create it
            database = new SQLiteDatabase(name);
            onCreate(database);
            database.setVersion(version);
            Log.i(TAG, "onCreate()");
        }

        return database;
    }
}