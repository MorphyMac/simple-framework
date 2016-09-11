package com.tylersuehr.concurrent;
import java.util.ArrayDeque;
import java.util.Collection;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * Implementation of {@link ITaskExecutor} that executes tasks serially.
 */
public final class TaskExecutor implements ITaskExecutor {
    private ArrayDeque<Task> tasks = new ArrayDeque<>();


    @Override
    public void execute() {
        execute(null);
    }

    @Override
    public void execute(Task task) {
        if (task != null) {
            this.tasks.offer(task);
        }
        Task active;
        while ((active = tasks.poll()) != null) {
            active.execute();
        }
    }

    /**
     * Adds a task to the queue.
     * @param task {@link Task}
     */
    public void add(Task task) {
        this.tasks.offer(task);
    }

    /**
     * Adds a collection of tasks to the queue.
     * @param tasks Collection of {@link Task}
     */
    public void addAll(Collection<Task> tasks) {
        for (Task t : tasks) {
            this.tasks.offer(t);
        }
    }
}