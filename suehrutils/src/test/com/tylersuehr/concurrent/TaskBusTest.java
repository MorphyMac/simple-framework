package com.tylersuehr.concurrent;
import static org.junit.Assert.*;
import com.tylersuehr.Log;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class TaskBusTest {
    @Test
    public void testRegister() {
        IObserver o1 = createObserver();
        IObserver o2 = createObserver();
        IObserver o3 = createObserver();
        TaskBus.register(o1);
        TaskBus.register(o2);
        TaskBus.register(o3);
        int count = TaskBus.getObserverCount();
        assertTrue(count == 3);
    }

    @Test
    public void testUnregister() {
        IObserver o = createObserver();
        TaskBus.register(o);
        int oldCount = TaskBus.getObserverCount();
        assertEquals(oldCount, 1);

        TaskBus.unregister(o);
        int count = TaskBus.getObserverCount();
        assertEquals(count, 0);
    }

    @Test
    public void testUnregisterAll() {
        testRegister();
        int oldCount = TaskBus.getObserverCount();
        TaskBus.unregisterAll();
        int count = TaskBus.getObserverCount();
        assertTrue(oldCount > count && count <= 0);
    }

    @Test
    public void testExecute() {
        IObserver o1 = createObserver();
        IObserver o2 = createObserver();
        IObserver o3 = createObserver();
        IObserver o4 = createObserver();

        TaskBus.register(o1);
        TaskBus.register(o2);
        TaskBus.register(o3);
        TaskBus.register(o4);

        TaskBus.execute(new TestTask(7));
    }

    @Test
    public void testExecuteArray() {
        IObserver o1 = createObserver();
        IObserver o2 = createObserver();

        TaskBus.register(o1);
        TaskBus.register(o2);

        TaskBus.execute(new TestTask(7), new TestTask2(3));
    }

    @Test
    public void testExecuteCollection() {
        IObserver o1 = createObserver();
        IObserver o2 = createObserver();

        TaskBus.register(o1);
        TaskBus.register(o2);

        Collection<Task> tasks = new ArrayList<>();
        tasks.add(new TestTask(324));
        tasks.add(new TestTask2(12));

        TaskBus.execute(tasks);
    }

    private IObserver createObserver() {
        return new IObserver() {
            @Override
            public void onUpdate(Object data) {
                Log.i("OBSERVER", data.toString());
            }
        };
    }
}