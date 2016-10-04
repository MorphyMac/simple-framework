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
    private static volatile FrameManager instance;
    private Stack<Pair<UUID, AppFrame>> backStack = new Stack<>();
    Bundle savedInstanceState = null;


    private FrameManager() {}
    public static synchronized FrameManager getInstance() {
        if (instance == null) {
            instance = new FrameManager();
        }
        return instance;
    }

    /**
     * Shows the given frame and adds it to the stack of frames.
     * @param frame {@link AppFrame}
     */
    @Override
    public void startFrame(AppFrame frame) {
        // Show frame, generate a token, and add it to stack
        frame.frameToken = UUID.randomUUID();
        frame.showFrame(savedInstanceState);
        this.backStack.push(new Pair<>(frame.frameToken, frame));

        Log.i(TAG, "Started new frame. Frames open: " + backStack.size());
    }

    /**
     * Hides the given frame and removes it from the stack.
     * @param frame The frame
     */
    @Override
    public void finish(AppFrame frame) {
        Pair<UUID, AppFrame> result = new Pair<>(frame.frameToken, frame);

        int position = backStack.search(result);
        if (position == -1)
            throw new RuntimeException("Frame already finished!");

        frame.setVisible(false);
        backStack.removeElement(result);

        checkForExit();

        Log.i(TAG, "Frame finished. Frames open: " + backStack.size());
    }

    /**
     * Clears the back stack of frames and starts the given frame.
     * @param frame {@link AppFrame}
     */
    @Override
    public void clearBackStack(AppFrame frame) {
        // Remove all frames from back stack
        while (!backStack.empty()) {
            AppFrame old = backStack.pop().getValue();
            old.setVisible(false);
        }

        // Start the new frame
        frame.frameToken = UUID.randomUUID();
        frame.showFrame(savedInstanceState);
        this.backStack.push(new Pair<>(frame.frameToken, frame));

        Log.i(TAG, "Stack cleared. Frames open: " + backStack.size());
    }

    @Override
    public AppFrame findFrameByToken(UUID frameToken) {
        Object[] args;

        synchronized (this) {
            args = backStack.toArray();
        }

        for (int i = args.length - 1; i >= 0; i--) {
            Pair<UUID, AppFrame> result = ((Pair<UUID, AppFrame>)args[i]);
            if (result.getKey() == frameToken) {
                return result.getValue();
            }
        }

        return null;
    }

    @Override
    public int getFrameCount() {
        return backStack.size();
    }

    @Override
    public void saveInstance(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
    }

    private void checkForExit() {
        if (backStack.size() <= 0) {
            System.exit(0);
        }
    }
}