package com.tylersuehr.sql;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
final class SQLBuilder {
    // INSERT INTO [table] ([col1],[col2],[col3],[col4]) VALUES ('test', 'test2', 123, 12.123);
    String createInsert(String table, ContentValues values) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append("[").append(table).append("] (");

        int i = 0;
        for (String col : values.getKeys()) {
            sb.append((i > 0) ? "," : "");
            sb.append("[").append(col).append("]");
            i++;
        }
        sb.append(") VALUES (");

        i = 0;
        for (Object o : values.getValues()) {
            sb.append((i > 0) ? "," : "");
            if (o instanceof String) {
                sb.append("'").append(o).append("'");
            } else {
                sb.append(o);
            }
            i++;
        }
        sb.append(");");
        return sb.toString();
    }

    // UPDATE [table] SET [col1] = 'test' WHERE [col2] = 3;
    String createUpdate(String table, ContentValues values, String selection) {
        StringBuilder sb = new StringBuilder(120);
        sb.append("UPDATE ");
        sb.append("[").append(table).append("]");
        sb.append(" SET ");

        int i = 0;
        for (String col : values.getKeys()) {
            sb.append((i > 0) ? "," : "");
            sb.append("[").append(col).append("]");
            sb.append("=");

            if (values.get(col) instanceof String) {
                sb.append("'").append(values.get(col)).append("'");
            } else {
                sb.append(values.get(col));
            }
            i++;
        }

        if (selection != null) {
            sb.append(" WHERE ");
            sb.append(selection);
        }

        sb.append(";");
        return sb.toString();
    }

    // DELETE FROM [table] WHERE [col1] = 23;
    String createDelete(String table, String selection) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append("[").append(table).append("]");

        if (selection != null) {
            sb.append(" WHERE ");
            sb.append(selection);
        }

        sb.append(";");
        return sb.toString();
    }
}