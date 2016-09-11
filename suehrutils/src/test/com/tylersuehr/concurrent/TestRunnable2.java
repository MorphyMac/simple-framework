package com.tylersuehr.concurrent;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
class TestRunnable2 implements Runnable {
    private String text;


    TestRunnable2(String text) {
        this.text = text;
    }

    @Override
    public void run() {
        int count = 0;
        for (int i = 0; i < 100; i++) {
            count = count + i;
            text += "|" + String.valueOf(count);
        }
        System.out.println(text);
    }
}