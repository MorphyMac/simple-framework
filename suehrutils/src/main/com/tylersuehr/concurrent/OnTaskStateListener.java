package com.tylersuehr.concurrent;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * Provides callbacks for {@link #onPreExecute()} and {@link #onPostExecute()}
 * of the {@link Task} object.
 */
public interface OnTaskStateListener {
    void onPreExecute();
    void onPostExecute();
}