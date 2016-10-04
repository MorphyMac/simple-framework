package com.tylersuehr.framework;
import java.util.UUID;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public interface IFrameManager {
    String TAG = "FRAME MANAGER";

    void startFrame(AppFrame frame);
    void finish(AppFrame frame);
    void clearBackStack(AppFrame frame);
    void saveInstance(Bundle savedInstanceState);

    AppFrame findFrameByToken(UUID token);
    int getFrameCount();
}