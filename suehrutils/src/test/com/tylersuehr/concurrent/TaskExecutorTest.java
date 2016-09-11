package com.tylersuehr.concurrent;
import com.tylersuehr.Log;
import org.junit.Test;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class TaskExecutorTest {
    @Test
    public void testSingleExecute() {
        TaskExecutor executor = new TaskExecutor();
        executor.execute(new TestTask(1));
    }

    @Test
    public void testMultipleExecute() {
        TaskExecutor executor = new TaskExecutor();
        executor.add(new TestTask(1));
        executor.add(new TestTask(2));
        executor.add(new TestTask(3));
        executor.add(new TestTask(4));
        executor.add(new TestTask(5));
        executor.add(new TestTask(6));
        executor.add(new TestTask(7));
        executor.execute();
    }

    @Test
    public void testSingleCallback() {
        TaskExecutor executor = new TaskExecutor();
        executor.setCallback(create());
        executor.execute(new TestTask(1));
    }

    @Test
    public void testMultipleCallback() {
        TaskExecutor executor = new TaskExecutor();
        executor.setCallback(create());
        executor.add(new TestTask(1));
        executor.add(new TestTask(2));
        executor.add(new TestTask2(3));
        executor.add(new TestTask(4));
        executor.execute();
    }

    private ITaskCallback create() {
        return new ITaskCallback() {
            @Override
            public void onPreExecute() {}

            @Override
            public void onPostExecute(Object data) {
                Log.i("TASK CALLBACK", data.toString());
            }
        };
    }
}