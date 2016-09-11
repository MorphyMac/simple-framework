package com.tylersuehr.concurrent;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public final class SingleExecutor implements Executor {
    private final ThreadFactory threadFactory = new LocalThreadFactory();


    @Override
    public void execute(Runnable command) {
        threadFactory.newThread(command).start();
    }
}