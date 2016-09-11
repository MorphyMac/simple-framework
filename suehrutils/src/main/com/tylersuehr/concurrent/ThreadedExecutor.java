package com.tylersuehr.concurrent;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.concurrent.ThreadFactory;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public abstract class ThreadedExecutor implements IExecutor {
    final ArrayDeque<Runnable> queue = new ArrayDeque<>();
    final ThreadFactory threadFactory = new LocalThreadFactory();


    @Override
    public void execute() {
        execute(null);
    }

    /**
     * Adds a runnable to the queue.
     * @param r {@link Runnable}
     */
    public synchronized void add(Runnable r) {
        this.queue.offer(r);
    }

    /**
     * Adds a collection of runnables to the queue.
     * @param rs Collection of {@link Runnable}
     */
    public synchronized void addAll(Collection<Runnable> rs) {
        for (Runnable r : rs) {
            this.queue.offer(r);
        }
    }
}