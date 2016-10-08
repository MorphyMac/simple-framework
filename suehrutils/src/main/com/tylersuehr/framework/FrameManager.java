package com.tylersuehr.framework;
import com.tylersuehr.Log;
import javafx.util.Pair;
import java.util.Stack;
import java.util.UUID;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public final class FrameManager implements IFrameManager {
    private static final String TAG = "FRAME MANAGER";
    private static volatile FrameManager instance;
    private Stack<Pair<UUID, AppFrame>> backStack = new Stack<>();


    private FrameManager() {}
    public static synchronized FrameManager getInstance() {
        if (instance == null) {
            instance = new FrameManager();
        }
        return instance;
    }

    @Override
    public void startFrame(AppFrame frame) {
        // Create unique token for frame
        UUID token = UUID.randomUUID();

        // Set imperative frame data and show it
        frame.setFrameManager(this);
        frame.setFrameToken(token);
        frame.showFrame();

        // Put the frame into the backstack
        backStack.push(new Pair<>(token, frame));

        Log.i(TAG, "Frame started: " + token + " Frames Open: " + backStack.size());
    }

    @Override
    public void finish(AppFrame frame) {
        // Get the pair using the frame token and frame
        Pair<UUID, AppFrame> result = new Pair<>(frame.getFrameToken(), frame);

        // Search for that pair in the back stack
        int position = backStack.search(result);
        if (position == -1) {
            throw new RuntimeException("Frame already finished!");
        }

        // Close the frame and remove it from the back stack
        frame.setVisible(false);
        frame.setFrameToken(null);
        frame.setFrameManager(null);
        frame.dispose();
        backStack.removeElement(result);
        checkExit();

        Log.i(TAG, "Frame finished: " + frame.getFrameToken() + " Frames Open: " + backStack.size());
    }

    @Override
    public void clearStack(AppFrame frame) {
        // Remove all frames from back stack
        while (!backStack.empty()) {
            AppFrame old = backStack.pop().getValue();
            old.setVisible(false);
            old.setFrameToken(null);
            old.setFrameManager(null);
            old.dispose();
        }

        // Start the new frame
        UUID token = UUID.randomUUID();
        frame.setFrameToken(token);
        frame.showFrame();
        this.backStack.push(new Pair<>(token, frame));

        Log.i(TAG, "Stack cleared. Frames Open: " + backStack.size());
    }

    @Override
    public AppFrame findFrameByToken(UUID token) {
        Object[] args;

        synchronized (this) {
            args = backStack.toArray();
        }

        for (int i = args.length - 1; i >= 0; i--) {
            Pair<UUID, AppFrame> result = (Pair<UUID, AppFrame>)args[i];
            if (result.getKey() == token) {
                return result.getValue();
            }
        }
        return null;
    }

    @Override
    public int getFrameCount() {
        return backStack.size();
    }

    private void checkExit() {
        if (backStack.size() <= 0) {
            System.exit(0);
        }
    }
}