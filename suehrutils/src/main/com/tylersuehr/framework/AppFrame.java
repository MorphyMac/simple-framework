package com.tylersuehr.framework;
import com.tylersuehr.Log;
import java.util.UUID;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class AppFrame extends LifeCycleFrame {
    private IFrameManager frameManager;
    UUID frameToken;


    public AppFrame(String title, IFrameManager manager) {
        super(title);
        this.frameManager = manager;
    }

    @Override
    protected void onDestroy() {
        finish();
        Log.i(TAG, "onDestroy()");
    }

    public IFrameManager getFrameManager() {
        return frameManager;
    }

    public UUID getFrameToken() {
        return frameToken;
    }

    public void finish() {
        this.frameManager.finish(this);
    }

    protected void saveInstance(Bundle savedInstanceState) {
        this.frameManager.saveInstance(savedInstanceState);
    }
}