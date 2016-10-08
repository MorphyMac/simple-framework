package com.tylersuehr.framework;
import com.tylersuehr.Log;
import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.UUID;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This implementation of {@link JFrame} provides callbacks pertaining to its lifecycle
 * and a {@link WindowListener} to detect the changes the actual window to call necessary
 * lifecycle callbacks.
 */
public class AppFrame extends JFrame implements WindowListener {
    protected static final String TAG = "FRAME";
    private boolean running = false;
    private IFrameManager frameManager;
    private UUID frameToken;


    public AppFrame() {
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
    public void windowClosing(WindowEvent e) {
        // Called when user clicks the exit button
        onDestroy();
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // Called every time the window is viewed
        if (running) {
            onResume();
        }
        this.running = true;
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // Called every time the window minimized or unfocused
        if (running) {
            onPause();
        }
    }

    /**
     * Called when the frame is being created before the frame is shown
     * on the screen. We don't want classes outside this package to call
     * this except for subclasses.
     */
    protected void onCreate() {
        Log.i(TAG, "onCreate()");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    /**
     * Called right after frame is shown on the screen. We don't want classes
     * outside this package to call this except for subclasses.
     */
    protected void onStart() {
        Log.i(TAG, "onStart()");
    }

    /**
     * Called when window loses focus or is minimized. We don't want classes
     * outside this package to call this except for subclasses.
     */
    protected void onPause() {
        Log.i(TAG, "onPause()");
    }

    /**
     * Called when window regains focus. We don't want classes outside this package
     * to call this except for subclasses.
     */
    protected void onResume() {
        Log.i(TAG, "onResume()");
    }

    /**
     * Called when window is closing. We don't want classes outside this package to
     * call this except for subclasses.
     *
     * This is abstract because we our JFrame doesn't use the default closing
     * operation. Therefore we must do something when frame is destroyed.
     */
    protected void onDestroy() {
        finish();
    }

    /**
     * Uses the frame manager to remove this frame from the back stack and
     * dispose of it.
     */
    public void finish() {
        this.frameManager.finish(this);
    }

    void setFrameManager(IFrameManager fm) {
        this.frameManager = fm;
    }

    public IFrameManager getFrameManager() {
        return frameManager;
    }

    void setFrameToken(UUID token) {
        this.frameToken = token;
    }

    public UUID getFrameToken() {
        return frameToken;
    }

    /**
     * Shows the JFrame on the screen but does so calling the appropriate
     * callbacks.
     */
    void showFrame() {
        onCreate();
        setVisible(true);
        onStart();
    }
}