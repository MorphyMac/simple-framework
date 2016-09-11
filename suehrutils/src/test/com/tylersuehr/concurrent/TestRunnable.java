package com.tylersuehr.concurrent;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
class TestRunnable implements Runnable {
    private String text;


    TestRunnable(String text) {
        this.text = text;
    }

    @Override
    public void run() {
        System.out.println(text);
    }
}