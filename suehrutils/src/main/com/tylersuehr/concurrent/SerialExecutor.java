package com.tylersuehr.concurrent;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * An implementation of {@link QueueExecutor} that executes a queue of {@link Runnable}s
 * one by one on a single new thread.
 */
public final class SerialExecutor extends QueueExecutor {
    @Override
    public void execute(final Runnable command) {
        threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                // Offer the given command to queue if not null
                if (command != null) {
                    tasks.offer(command);
                }

                // Start executing the tasks one by one
                Runnable active;
                while ((active = tasks.poll()) != null) {
                    active.run();
                    taskCount++;
                }
            }
        }).start();
    }
}