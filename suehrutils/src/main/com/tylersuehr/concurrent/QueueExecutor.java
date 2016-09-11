package com.tylersuehr.concurrent;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * An implementation of {@link Executor} that allows you to add {@link Runnable}s to a queue
 * an execute them. Also stores a count of executed runnables.
 */
public abstract class QueueExecutor implements Executor {
    final ArrayDeque<Runnable> tasks = new ArrayDeque<>();
    final ThreadFactory threadFactory = new LocalThreadFactory();
    volatile int taskCount = 0;


    public QueueExecutor() {}

    /**
     * Runs the {@link #execute(Runnable)} with null params.
     */
    public void execute() {
        execute(null);
    }

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

    /**
     * Gets the number of runnables that were run.
     * @return Number runnables executed
     */
    public synchronized int getCompletedTasks() {
        return taskCount;
    }
}