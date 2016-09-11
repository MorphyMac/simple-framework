package com.tylersuehr.concurrent;
import com.tylersuehr.Log;
import org.junit.Test;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class TaskExecutorTest {
    private static final String TAG = "TASK EXECUTOR TASK";

    @Test
    public void testSingleTaskExecute() {
        TaskExecutor executor = new TaskExecutor();
        executor.execute(new TestTask());
    }

    @Test
    public void testMultipleTaskExecute() {
        Log.i(TAG, "Started...");
        TaskExecutor executor = new TaskExecutor();
        for (int i = 0; i < 20; i++) {
            executor.add(new TestTask());
        }
        executor.execute();
        Log.i(TAG, "Finished...");
    }
}