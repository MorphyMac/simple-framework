package com.tylersuehr.framework;
import java.util.UUID;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * Defines methods that we need to manage frames.
 */
public interface IFrameManager {
    String TAG = "FRAME MANAGER";

    void startFrame(AppFrame frame);
    void finish(AppFrame frame);
    void clearStack(AppFrame frame);
    AppFrame findFrameByToken(UUID token);
    int getFrameCount();
}