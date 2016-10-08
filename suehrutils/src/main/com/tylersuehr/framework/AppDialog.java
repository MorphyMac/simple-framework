package com.tylersuehr.framework;
import com.tylersuehr.Log;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This is a modal dialog that has methods to help manage or 'listen' to
 * its life cycle.
 */
public class AppDialog extends JDialog implements WindowListener {
    private static final String TAG = "DIALOG";


    public AppDialog(Frame owner) {
        super(owner);
        addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        onDestroy();
    }

    protected void onCreate() {
        Log.i(TAG, "onCreate()");
        setAlwaysOnTop(true);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.APPLICATION_MODAL);
    }

    protected void onStart() {
        Log.i(TAG, "onStart()");
    }

    protected void onDestroy() {
        Log.i(TAG, "onDestroy()");
    }

    public void showDialog() {
        onCreate();
        setVisible(true);
        onStart();
    }
}