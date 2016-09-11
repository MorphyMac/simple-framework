package com.tylersuehr.concurrent;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * Defines methods for an object that allows the execution of a task.
 */
public interface ITaskExecutor {
    void execute();
    void execute(Task task);
}