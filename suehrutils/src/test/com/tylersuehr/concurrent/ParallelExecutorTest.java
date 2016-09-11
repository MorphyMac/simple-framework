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
        executor.execute(new TestRunnable("Test Single"));
    }

    @Test
    public void testMultipleExecute() {
        // NOTE: In the log, these SHOULD be out of order... that means they have executed in parallel
        ParallelExecutor executor = new ParallelExecutor();
        executor.execute(new TestRunnable("Mult 1"));
        executor.execute(new TestRunnable2("Mult 2"));
        executor.execute(new TestRunnable("Mult 3"));
        executor.execute(new TestRunnable("Mult 4"));
        executor.execute(new TestRunnable("Mult 5"));
        executor.execute(new TestRunnable("Mult 6"));
        executor.execute(new TestRunnable("Mult 7"));
        executor.execute(new TestRunnable("Mult 8"));
        executor.execute();
    }

    @Test
    public void testMultipleExecute2() {
        // NOTE: In the log, these SHOULD be out of order... that means they have executed in parallel
        ParallelExecutor executor = new ParallelExecutor();
        executor.execute(new TestRunnable("Mult 1"));
        executor.execute(new TestRunnable2("Mult 2"));
        executor.execute(new TestRunnable("Mult 3"));
        executor.execute(new TestRunnable("Mult 4"));
        executor.execute(new TestRunnable("Mult 5"));
        executor.execute(new TestRunnable("Mult 6"));
        executor.execute(new TestRunnable2("Mult 7"));
        executor.execute(new TestRunnable("Mult 8"));
    }
}