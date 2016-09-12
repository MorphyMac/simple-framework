package com.tylersuehr.concurrent;
import java.util.Collection;
import java.util.Vector;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This will allow us to execute tasks very easily an affords us the wonderful Observer pattern.
 *
 * We can register/unregister observers as we please and execute any task, array of tasks, or
 * collection tasks and receive called backs with the data.
 */
public final class TaskBus {
    private static final Vector<IObserver> OBSERVERS = new Vector<>();
    private static final ITaskCallback CALLBACK = new TaskObserver();
    private static final TaskExecutor EXECUTOR = new TaskExecutor(CALLBACK);


    public TaskBus() {}

    /**
     * Executes a single task.
     * @param task {@link Task}
     */
    public static void execute(Task task) {
        EXECUTOR.execute(task);
    }

    /**
     * Adds an array of tasks to the executor and executes them.
     * @param tasks Array of {@link Task}
     */
    public static void execute(Task... tasks) {
        for (Task t : tasks) {
            EXECUTOR.add(t);
        }
        EXECUTOR.execute();
    }

    /**
     * Adds a collection of tasks to the executor and executes them.
     * @param tasks Collection of {@link Task}
     */
    public static void execute(Collection<Task> tasks) {
        EXECUTOR.addAll(tasks);
        EXECUTOR.execute();
    }

    /**
     * Adds an observer to our vector.
     * @param observer {@link IObserver}
     */
    public static synchronized void register(IObserver observer) {
        if (OBSERVERS.contains(observer)) {
            throw new IllegalArgumentException("Observer already registered!");
        }
        OBSERVERS.addElement(observer);
    }

    /**
     * Removes an observer from our vector.
     * @param observer {@link IObserver}
     */
    public static synchronized void unregister(IObserver observer) {
        OBSERVERS.removeElement(observer);
    }

    /**
     * Removes all observers from our vector.
     */
    public static synchronized void unregisterAll() {
        OBSERVERS.removeAllElements();
    }

    /**
     * Gets the number of observers from our vector.
     * @return Number of observers
     */
    public static synchronized int getObserverCount() {
        return OBSERVERS.size();
    }

    /**
     * Notifies all observers registered to watch task updates.
     * @param data Object
     */
    private static void notifyObservers(Object data) {
        Object[] array;
        synchronized (TaskBus.class) {
            array = OBSERVERS.toArray();
        }
        for (int i = array.length - 1; i >= 0; i--) {
            ((IObserver)array[i]).onUpdate(data);
        }
    }


    // This is a simple implementation of ITaskCallback that will notify observers
    // when it is called.
    private static class TaskObserver implements ITaskCallback {
        @Override
        public void onPreExecute() {}

        @Override
        public void onPostExecute(Object data) {
            TaskBus.notifyObservers(data);
        }
    }
}