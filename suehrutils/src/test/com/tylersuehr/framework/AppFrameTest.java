package com.tylersuehr.framework;
import org.junit.Assert;
import org.junit.Test;
import java.util.UUID;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class AppFrameTest {
    @Test
    public void testFrameToken() {
        UUID token = UUID.randomUUID();

        AppFrame result = new AppFrame();
        result.setFrameToken(token);

        Assert.assertTrue(token == result.getFrameToken());
    }

    @Test
    public void testFrameManager() {
        IFrameManager manager = FrameManager.getInstance();

        AppFrame result = new AppFrame();
        result.setFrameManager(manager);

        Assert.assertTrue(manager == result.getFrameManager());
    }

    @Test
    public void testFinish() {
        IFrameManager manager = FrameManager.getInstance();

        AppFrame result = new AppFrame();
        AppFrame test = new AppFrame();
        manager.startFrame(test);
        manager.startFrame(result);

        test.finish();
        Assert.assertTrue(manager.getFrameCount() == 1);
    }
}