package com.tylersuehr.concurrent;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
class TestTask extends Task<Boolean> {
    private int count;


    public TestTask(int count) {
        this.count = count;
    }

    @Override
    protected Boolean doInBackground() {
        String tag = "Tag " + count + ": ";
        int count = 0;
        for (int i = 0; i < 10; i++) {
            count += i;
            tag += "_" + count;
        }
        System.out.println(tag);
        return true;
    }
}