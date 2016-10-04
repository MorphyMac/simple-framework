package com.tylersuehr.framework;
import org.junit.Assert;
import org.junit.Test;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class AppFrameTest {
    @Test
    public void testFrameToken() {
        FrameManager manager = FrameManager.getInstance();

        AppFrame result = new AppFrame("Test", manager);
        manager.startFrame(result);

        Assert.assertNotNull(result.getFrameToken());
    }

    @Test
    public void testFrameManager() {
        FrameManager manager = FrameManager.getInstance();

        AppFrame result = new AppFrame("Test", manager);
        manager.startFrame(result);

        Assert.assertNotNull(result.getFrameManager());
    }
}