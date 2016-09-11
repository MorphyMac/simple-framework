package com.tylersuehr.concurrent;
import org.junit.Test;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class SerialExecutorTest {
    @Test
    public void testSingleExecute() {
        SerialExecutor executor = new SerialExecutor();
        executor.execute(new TestRunnable("Test Awesome!"));
    }

    @Test
    public void testMultipleExecute() {
        SerialExecutor executor = new SerialExecutor();
        executor.add(new TestRunnable("Runnable 1"));
        executor.add(new TestRunnable("Runnable 2"));
        executor.add(new TestRunnable("Runnable 3"));
        executor.add(new TestRunnable("Runnable 4"));
        executor.add(new TestRunnable("Runnable 5"));
        executor.execute();
    }
}