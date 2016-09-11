package com.tylersuehr.concurrent;
import java.util.concurrent.ThreadFactory;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * A utility that spawns new threads for us.
 */
public class LocalThreadFactory implements ThreadFactory {
    private volatile int count = 0;


    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "tylersuehr_" + count++);
    }
}