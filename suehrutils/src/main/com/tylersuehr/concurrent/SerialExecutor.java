package com.tylersuehr.concurrent;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This will run a queue of {@link Runnable} on a single new thread serially (one by one).
 */
public final class SerialExecutor extends ThreadedExecutor {
    @Override
    public synchronized void execute(final Runnable command) {
        threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                // Add the initial runnable to the queue
                if (command != null) {
                    queue.offer(command);
                }

                // Instantiate a new runnable and try to poll the queue
                // and execute the task.
                Runnable task;
                while ((task = queue.poll()) != null) {
                    task.run();
                }
            }
        }).start();
    }
}