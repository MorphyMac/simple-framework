package com.tylersuehr.concurrent;
import java.util.ArrayDeque;
import java.util.Collection;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * An implementation of {@link ITaskExecutor} that allows you to add {@link Task}s to a queue
 * an execute them. Also stores a count of executed tasks.
 */
public class TaskExecutor implements ITaskExecutor {
    private final ArrayDeque<Task> tasks = new ArrayDeque<>(); // Queue for our pending tasks
    private boolean first = true; // Determines if this is the first execution
    private int tasksCount = 0; // Count for tasks run
    private ITaskCallback callback; // Callback for task methods
    private Task pending = null; // The task waiting for execution


    public TaskExecutor() {}

    public TaskExecutor(ITaskCallback callback) {
        this.callback = callback;
    }

    @Override
    public void execute() {
        execute(null);
    }

    @Override
    public void execute(Task task) {
        if (task != null && first) {
            this.tasks.offer(task);
        }
        first = false;

        if (pending == null) {
            executeNext();
        } else {
            pending.execute();
            tasksCount++;
            executeNext();
        }
    }

    public void setCallback(ITaskCallback callback) {
        this.callback = callback;
    }

    private void executeNext() {
        if ((pending = tasks.poll()) != null) {
            if (callback != null) {
                pending.setCallback(callback);
            }
            execute(pending);
        }
    }

    /**
     * Adds a task to the queue.
     * @param task {@link Task}
     */
    public synchronized void add(Task task) {
        this.tasks.offer(task);
    }

    /**
     * Adds a collection of tasks to the queue.
     * @param tasks Collection of {@link Task}
     */
    public synchronized void addAll(Collection<Task> tasks) {
        for (Task t : tasks) {
            this.tasks.offer(t);
        }
    }

    /**
     * Gets the number of pending tasks.
     * @return Tasks pending
     */
    public synchronized int getPendingCount() {
        return tasks.size();
    }

    /**
     * Gets the number of run tasks.
     * @return Tasks run
     */
    public synchronized int getCompletedCount() {
        return tasksCount;
    }
}