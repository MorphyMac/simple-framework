package com.tylersuehr.storage;
import com.tylersuehr.Log;
import com.tylersuehr.security.ICryptography;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This utility allows us to store persistent com.tylersuehr.data in a
 * key-value pair (JSON) and store it locally on the disk.
 */
final class SharedPreferences implements ISharedPreferences {
    private static final String TAG = "PREFS";
    private final Object LOCK = new Object();
    private ICryptography crypt;
    private JSONParser parser;
    private JSONObject json;
    private String path;


    SharedPreferences(String path) {
        this(path, null);
    }

    SharedPreferences(String path, ICryptography crypt) {
        this.path = path;
        this.path += ".json";
        this.crypt = crypt;
        this.parser = new JSONParser();
        this.json = new JSONObject();
        readJsonFromFile();
    }

    @Override
    public String getString(String pref) {
        return (String)json.get(pref);
    }

    @Override
    public int getInt(String pref, int def) {
        Object object = json.get(pref);
        return (object == null ? def : (int)object);
    }

    @Override
    public long getLong(String pref, long def) {
        Object object = json.get(pref);
        return (object == null ? def : (long)object);
    }

    @Override
    public float getFloat(String pref, float def) {
        Object object = json.get(pref);
        return (object == null ? def : (float)object);
    }

    @Override
    public double getDouble(String pref, double def) {
        Object object = json.get(pref);
        return (object == null ? def : (double)object);
    }

    @Override
    public boolean getBoolean(String pref, boolean def) {
        Object object = json.get(pref);
        return (object == null ? def : (boolean)object);
    }

    @Override
    public IEditor edit() {
        return new Editor();
    }

    /**
     * Saves the contents of the JSON object to the file
     * as a string.
     */
    private void saveJsonToFile() {
        try {
            File file = new File(path);
            if (file.exists()) {
                synchronized (LOCK) {
                    String data = json.toJSONString();

                    // Check if we should encrypt the com.tylersuehr.data
                    if (crypt != null) {
                        data = crypt.encrypt(data);
                    }

                    // Write json to file
                    PrintWriter pr = new PrintWriter(file);
                    pr.print(data);
                    pr.flush();
                    pr.close();
                }
                Log.i(TAG, "JSON saved to file!");
            } else {
                Log.i(TAG, "File doesn't exist... could not save!");
            }
        } catch (Exception ex) {
            Log.e(TAG, "Couldn't save JSON to file!", ex);
        }
    }

    /**
     * Reads the contents of a file and parses it into JSON. If the file
     * doesn't exist, it will create a new one.
     */
    private void readJsonFromFile() {
        try {
            File file = new File(path);
            if (file.exists()) {
                // Read the file contents as a string
                InputStream is = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String temp;

                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                }
                is.close();

                // Parse the file contents into a JSON object
                String strJson = sb.toString();
                if (strJson.length() > 0) {
                    // Check if we should decrypt the com.tylersuehr.data
                    if (crypt != null) {
                        strJson = crypt.decrypt(strJson);
                    }

                    json = (JSONObject)parser.parse(strJson);
                    Log.i(TAG, "Successfully read json!");
                } else {
                    Log.i(TAG, "File empty!");
                }
            } else {
                // Create a new file and read it again
                createNewJsonFile(file);
                readJsonFromFile();
            }
        } catch (IOException|ParseException|NullPointerException ex) {
            Log.e(TAG, "Couldn't read preferences!", ex);
        }
    }

    /**
     * Creates a new blank file.
     * @param file New file
     */
    private void createNewJsonFile(File file) throws IOException {
        synchronized (LOCK) {
            PrintWriter pr = new PrintWriter(file);
            pr.print("");
            pr.flush();
            pr.close();
        }
        Log.i(TAG, "Created new file: " + file.getPath());
    }


    // This allows us to add com.tylersuehr.data without corrupting the file if some exception or
    // error is thrown. Apply() will run on a new thread.
    private class Editor implements IEditor {
        private JSONObject clone;


        Editor() {
            this.clone = new JSONObject(json);
        }

        @Override
        public Editor put(String pref, String value) {
            this.clone.put(pref, value);
            return this;
        }

        @Override
        public IEditor put(String pref, int value) {
            this.clone.put(pref, value);
            return this;
        }

        @Override
        public IEditor put(String pref, long value) {
            this.clone.put(pref, value);
            return this;
        }

        @Override
        public IEditor put(String pref, float value) {
            this.clone.put(pref, value);
            return this;
        }

        @Override
        public IEditor put(String pref, double value) {
            this.clone.put(pref, value);
            return this;
        }

        @Override
        public IEditor put(String pref, boolean value) {
            this.clone.put(pref, value);
            return this;
        }

        @Override
        public void commit() {
            json = new JSONObject(clone);
            saveJsonToFile();
        }

        @Override
        public void apply() {
            json = new JSONObject(clone);
            new Thread(SharedPreferences.this::saveJsonToFile).start();
        }
    }
}