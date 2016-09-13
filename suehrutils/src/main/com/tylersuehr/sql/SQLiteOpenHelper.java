package com.tylersuehr.sql;
import com.tylersuehr.Log;
import java.io.File;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This is a more legacy version of {@link SQLiteOpenHelper2}. This version does NOT
 * retain an instance of {@link SQLiteDatabase} when calling {@link #getWritableInstance()};
 * it generates a brand new instance every time.
 */
public abstract class SQLiteOpenHelper {
    private static final String TAG = "SQLiteHelper";
    private String dbName;


    public SQLiteOpenHelper(String dbName, int version) {
        if (version == 0) {
            throw new IllegalArgumentException("Version must be greater than zero!");
        }

        // Append database extension to its name and set it up
        dbName += (!dbName.contains(".db") ? ".db" : "");
        setupDatabase(dbName, version);
        this.dbName = dbName;
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
     * Gets a writable instance of our database.
     * @return {@link SQLiteDatabase}
     */
    public SQLiteDatabase getWritableInstance() {
        return new SQLiteDatabase(dbName);
    }

    /**
     * Gets a valid instance of our SQLiteDatabase.
     * @param name Name of the database
     * @param version Version of the database
     */
    private void setupDatabase(String name, int version) {
        SQLiteDatabase db;

        // We need to check if the database exists
        File file = new File(name);
        if (file.exists()) {
            db = new SQLiteDatabase(name);

            // Check if the database needs updated
            int currentVersion = db.getVersion();
            if (version > currentVersion) {
                onUpdate(db, currentVersion, version);
                db.setVersion(version);
                Log.i(TAG, "onUpdated()");
            }
        } else {
            // We create our database because it doesn't exist yet
            db = new SQLiteDatabase(name);
            onCreate(db);
            db.setVersion(version);
            Log.i(TAG, "onCreate()");
        }

        db.close();
    }
}