package com.tylersuehr.sql;
import java.io.Closeable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
abstract class SQLiteCloseable implements Closeable {
    final AtomicInteger refs = new AtomicInteger(0);


    /**
     * Called when all references are released. We only want to
     * actually close a connection when all references are gone for
     * concurrency reasons.
     */
    protected abstract void onAllReferencesReleased();

    protected synchronized void acquireReference() {
        refs.getAndIncrement();
    }

    protected synchronized void releaseReference() {
        int count = refs.decrementAndGet();
        if (count < 0) {
            throw new IllegalStateException("No references exist!");
        } else if (count == 0) {
            onAllReferencesReleased();
        }
    }

    protected synchronized boolean hasReferences() {
        return refs.get() > 0;
    }

    @Override
    public void close() {
        releaseReference();
    }
}