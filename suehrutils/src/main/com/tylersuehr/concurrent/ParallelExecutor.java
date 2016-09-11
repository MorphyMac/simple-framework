package com.tylersuehr.concurrent;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * An implementation of {@link QueueExecutor} that executes a queue of {@link Runnable}s
 * instantly each on separate threads.
 */
public final class ParallelExecutor extends QueueExecutor {
    @Override
    public void execute(Runnable command) {
        // Offer the given command to queue if not null
        if (command != null) {
            tasks.offer(command);
        }

        // Run each runnable on a new thread
        Runnable active;
        while ((active = tasks.poll()) != null) {
            threadFactory.newThread(active).start();
        }
    }
}