package com.tylersuehr.concurrent;
import org.junit.Test;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class ParallelExecutorTest {
    @Test
    public void testSingleExecute() {
        ParallelExecutor executor = new ParallelExecutor();
        executor.execute(new TestRunnable("Test awesome!"));
    }

    @Test
    public void testMultipleExecute() {
        ParallelExecutor executor = new ParallelExecutor();
        executor.execute(new TestRunnable("Runnable 1"));
        executor.execute(new TestRunnable("Runnable 2"));
        executor.execute(new TestRunnable("Runnable 3"));
        executor.execute(new TestRunnable("Runnable 4"));
        executor.execute(new TestRunnable("Runnable 5"));
        executor.execute();
    }
}