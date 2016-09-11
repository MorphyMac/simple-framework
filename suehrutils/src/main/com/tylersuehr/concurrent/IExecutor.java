package com.tylersuehr.concurrent;
import java.util.concurrent.Executor;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * A generic implementation of {@link Executor} that allows an execute method
 * that doesn't take any parameters.
 */
public interface IExecutor extends Executor {
    void execute();
}