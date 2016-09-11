package com.tylersuehr.concurrent;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
class TestTask2 extends Task<Boolean> {
    @Override
    protected Boolean doInBackground() {
        String tag = "Tag: ";
        int count = 0;
        for (int i = 0; i < 10; i++) {
            count += i*2;
            tag += "_" + count;
        }
        System.out.println(tag);
        return false;
    }
}