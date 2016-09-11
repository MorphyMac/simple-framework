package com.tylersuehr.concurrent;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public abstract class ThreadedExecutor implements Executor {
    final ArrayDeque<Runnable> tasks = new ArrayDeque<>();
    final ThreadFactory threadFactory = new LocalThreadFactory();


    public ThreadedExecutor() {}

    /**
     * Adds a runnable to the queue.
     * @param r {@link Runnable}
     */
    public synchronized void add(Runnable r) {
        this.tasks.offer(r);
    }

    /**
     * Adds a collection of runnables to the queue.
     * @param rs Collection of {@link Runnable}
     */
    public synchronized void addAll(Collection<Runnable> rs) {
        for (Runnable r : rs) {
            this.tasks.offer(r);
        }
    }

    /**
     * Gets the number of runnables in the queue.
     * @return Number of runnables
     */
    public synchronized int getPendingTasks() {
        return tasks.size();
    }
}