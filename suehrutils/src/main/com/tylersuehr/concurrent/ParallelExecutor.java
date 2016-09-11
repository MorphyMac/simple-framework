package com.tylersuehr.concurrent;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This will run a queue of {@link Runnable} in parallel on multiple threads.
 */
public final class ParallelExecutor extends ThreadedExecutor {
    @Override
    public void execute(Runnable command) {
        // Add the initial runnable to the queue
        if (command != null) {
            queue.offer(command);
        }

        // Instantiate a new runnable and try to poll the queue
        // and execute the task.
        Runnable task;
        while ((task = queue.poll()) != null) {
            threadFactory.newThread(task).start();
        }
    }
}