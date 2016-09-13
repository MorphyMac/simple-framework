package com.tylersuehr.sql;
import com.tylersuehr.Log;
import java.sql.*;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This represents our SQLite database as an object.
 * This will keep track of references to database using {@link SQLiteCloseable}.
 * This uses a singleton pattern so that only one instance of this database
 * (and connection) can be in memory.
 *
 * NOTE: Most sql methods will throw a {@link SQLException} so unfortunately,
 *       we have a lot of error handling in here.
 */
public final class SQLiteDatabase extends SQLiteCloseable {
    private static final String TAG = "SQLiteDatabase";
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String PATH = "jdbc:sqlite:";
    private SQLBuilder sqlBuilder;
    private Connection connection;
    private Statement statement;


    SQLiteDatabase(String dbName) {
        this.openConnection(dbName);
        this.sqlBuilder = new SQLBuilder();
    }

    @Override
    protected void onAllReferencesReleased() {
        try {
            if (connection != null && statement != null) {
                this.statement.close();
                this.connection.close();
                Log.i(TAG, "All SQLiteDatabase references released!");
            }
        } catch (SQLException ex) {
            Log.e(TAG, "Couldn't close statement and connection!", ex);
        }
    }

    /**
     * Query data from the database.
     * @param table Name of the table
     * @param selection Where clause Ex: "[id]=12"
     * @param order Order by clause Ex: "[timestamp] ASC"
     * @param limit Limit clause Ex: "3"
     * @return {@link ResultSet} with data
     */
    public ResultSet query(String table, String selection, String order, String limit) {
        acquireReference();
        try {
            final String sql = sqlBuilder.createQuery(table, selection, order, limit);
            return statement.executeQuery(sql);
        } catch (SQLException ex) {
            Log.e(TAG, "Couldn't query!", ex);
        } finally {
            releaseReference();
        }
        return null;
    }

    /**
     * Queries the database by executing a plain SQL statement.
     * @param sql SQL query
     * @return {@link ResultSet}
     */
    public ResultSet rawQuery(String sql) {
        acquireReference();
        try {
            return statement.executeQuery(sql);
        } catch (SQLException ex) {
            Log.e(TAG, "Couldn't raw query!", ex);
        } finally {
            releaseReference();
        }
        return null;
    }

    /**
     * Execute a SQL statement; can be INSERT, UPDATE, or DELETE command.
     * @param sql SQL
     */
    public void execSql(String sql) {
        acquireReference();
        try {
            this.statement.executeUpdate(sql);
            this.connection.commit();
        } catch (SQLException ex) {
            Log.e(TAG, "Couldn't execute SQL!", ex);
        } finally {
            releaseReference();
        }
    }

    /**
     * Insert data into the database using the {@link ContentValues} hash map
     * implementation.
     * @param table Table name
     * @param values Values
     */
    public void insert(String table, ContentValues values) {
        acquireReference();
        try {
            final String sql = sqlBuilder.createInsert(table, values);
            this.statement.executeUpdate(sql);
            this.connection.commit();
        } catch (SQLException ex) {
            Log.e(TAG, "Couldn't insert!", ex);
        } finally {
            releaseReference();
        }
    }

    /**
     * Updates existing data in the database using the {@link ContentValues} hash map
     * implementation.
     * @param table Table name
     * @param values Values
     * @param selection Where clause (EX: "[first]='myName'")
     */
    public void update(String table, ContentValues values, String selection) {
        acquireReference();
        try {
            final String sql = sqlBuilder.createUpdate(table, values, selection);
            this.statement.executeUpdate(sql);
            this.connection.commit();
        } catch (SQLException ex) {
            Log.e(TAG, "Couldn't update!", ex);
        } finally {
            releaseReference();
        }
    }

    /**
     * Deletes an existing row in the database.
     * @param table Table name
     * @param selection Where clause (EX: "[id]='e2asf-23j4oi'")
     */
    public void delete(String table, String selection) {
        acquireReference();
        try {
            final String sql = sqlBuilder.createDelete(table, selection);
            this.statement.executeUpdate(sql);
            this.connection.commit();
        } catch (SQLException ex) {
            Log.e(TAG, "Couldn't delete!", ex);
        } finally {
            releaseReference();
        }
    }

    /**
     * Set the user version of the database.
     * @param version Version
     */
    public void setVersion(int version) {
        acquireReference();
        try {
            final String sql = "PRAGMA user_version=" + version;
            this.statement.executeUpdate(sql);
            this.connection.commit();
        } catch (SQLException ex) {
            Log.e(TAG, "Couldn't set version!", ex);
        } finally {
            releaseReference();
        }
    }

    /**
     * Get the user version of the database.
     * @return Version
     */
    public int getVersion() {
        acquireReference();
        try {
            final String sql = "PRAGMA user_version";
            ResultSet c = statement.executeQuery(sql);
            return c.getInt("user_version");
        } catch (SQLException ex) {
            Log.e(TAG, "Couldn't get version!", ex);
        } finally {
            releaseReference();
        }
        return -1;
    }

    /**
     * Opens a connection to the database.
     * @param dbName Name of the database file (don't include the file extension!)
     */
    private void openConnection(String dbName) {
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(PATH + dbName);
            this.connection.setAutoCommit(false);
            this.statement = connection.createStatement();
            acquireReference();
        } catch (Exception ex) {
            Log.e(TAG, "Coudln't open a database connection!", ex);
        }
    }
}