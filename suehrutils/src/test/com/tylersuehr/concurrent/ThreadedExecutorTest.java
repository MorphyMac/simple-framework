package com.tylersuehr.concurrent;
import static org.junit.Assert.*;
import org.junit.Test;
/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class ThreadedExecutorTest {
    @Test
    public void testAddRunnables() {
        ThreadedExecutor executor = createExecutor();
        executor.add(createRunnable());
        executor.add(createRunnable());
        executor.add(createRunnable());
        executor.add(createRunnable());
        assertTrue(executor.getPendingTasks() == 4);
    }

    private ThreadedExecutor createExecutor() {
        return new ThreadedExecutor() {
            @Override
            public void execute(Runnable command) {}
        };
    }

    private Runnable createRunnable() {
        return new Runnable() {
            @Override
            public void run() {}
        };
    }
}