package com.tylersuehr.concurrent;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This will run a single {@link Runnable} on a single new thread.
 */
public final class SingleExecutor extends ThreadedExecutor {
    @Override
    public void execute(Runnable command) {
        threadFactory.newThread(command).start();
    }
}