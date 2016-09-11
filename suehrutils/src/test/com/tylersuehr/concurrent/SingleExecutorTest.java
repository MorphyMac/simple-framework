package com.tylersuehr.concurrent;
import org.junit.Test;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class SingleExecutorTest {
    @Test
    public void testExecute() {
        SingleExecutor executor = new SingleExecutor();
        executor.execute(new TestRunnable("Finished!"));
    }
}