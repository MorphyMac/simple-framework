package com.tylersuehr.concurrent;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * Defines methods for task callbacks.
 */
public interface ITaskObserver {
    void onPreExecute();
    void onPostExecute(Object data);
}