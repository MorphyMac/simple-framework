package com.tylersuehr.concurrent;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * Defines the method observers should have.
 */
public interface IObserver {
    void onUpdate(Object data);
}