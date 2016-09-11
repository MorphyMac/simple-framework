package com.tylersuehr.concurrent;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * Defines methods for our task executors.
 */
public interface ITaskExecutor {
    void execute();
    void execute(Task task);
}