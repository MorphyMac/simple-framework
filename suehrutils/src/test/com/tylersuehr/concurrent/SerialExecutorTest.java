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
        executor.execute(new TestRunnable("Single Test"));
    }

    @Test
    public void testMultipleExecute() {
        SerialExecutor executor = new SerialExecutor();
        executor.add(new TestRunnable("Multiple 1"));
        executor.add(new TestRunnable("Multiple 2"));
        executor.add(new TestRunnable("Multiple 3"));
        executor.add(new TestRunnable("Multiple 4"));
        executor.add(new TestRunnable("Multiple 5"));
        executor.execute();
    }

    @Test
    public void testMultipleExecute2() {
        SerialExecutor executor = new SerialExecutor();
        executor.add(new TestRunnable("Multiple 1"));
        executor.add(new TestRunnable("Multiple 2"));
        executor.add(new TestRunnable("Multiple 3"));
        executor.add(new TestRunnable("Multiple 4"));
        executor.add(new TestRunnable("Multiple 5"));
        executor.execute(new TestRunnable("Multiple 6"));
    }
}