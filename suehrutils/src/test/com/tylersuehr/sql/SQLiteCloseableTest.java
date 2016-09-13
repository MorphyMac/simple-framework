package com.tylersuehr.sql;
import static org.junit.Assert.*;
import com.tylersuehr.Log;
import com.tylersuehr.concurrent.LocalThreadFactory;
import org.junit.Test;
import java.util.concurrent.ThreadFactory;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class SQLiteCloseableTest {
    @Test
    public void testReferences() {
        SQLiteCloseable closeable = create();
        closeable.acquireReference();
        closeable.acquireReference();
        assertTrue(closeable.refs.get() == 3);

        closeable.releaseReference();
        closeable.releaseReference();
        assertTrue(closeable.refs.get() == 1);

        closeable.releaseReference();
    }

    @Test
    public void testReferencesThreaded() throws InterruptedException {
        final ThreadFactory threadFactory = new LocalThreadFactory();
        SQLiteCloseable closeable = create();
        threadFactory.newThread(createRunnable(closeable)).start();

        // Do 'work'
        Thread.sleep(800);

        closeable.releaseReference();
    }

    private Runnable createRunnable(final SQLiteCloseable closeable) {
        return new Runnable() {
            @Override
            public void run() {
                closeable.acquireReference();
                int count = 0;
                for (int i = 0; i < 50; i++) {
                    count = count * i;
                }
                closeable.releaseReference();
            }
        };
    }

    private SQLiteCloseable create() {
        return new SQLiteCloseable() {
            @Override
            protected void onAllReferencesReleased() {
                Log.i("SQL ClOSEABLE", "All refs released!");
            }
        };
    }
}