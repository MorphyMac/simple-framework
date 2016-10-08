package com.tylersuehr.framework;
import org.junit.Assert;
import org.junit.Test;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class FrameManagerTest {
    @Test
    public void testStartFrame() {
        IFrameManager frameManager = FrameManager.getInstance();

        AppFrame frame = new AppFrame();
        frameManager.startFrame(frame);

        Assert.assertTrue(frameManager.getFrameCount() == 1);
    }

    @Test
    public void testFinishFrame() {
        IFrameManager frameManager = FrameManager.getInstance();

        AppFrame frame = new AppFrame();
        AppFrame frame2 = new AppFrame();
        AppFrame frame3 = new AppFrame();
        frameManager.startFrame(frame);
        frameManager.startFrame(frame2);
        frameManager.startFrame(frame3);

        Assert.assertTrue(frameManager.getFrameCount() == 3);
        frameManager.finish(frame2);
        frameManager.finish(frame);

        Assert.assertTrue(frameManager.getFrameCount() == 1);
        frameManager.finish(frame3);

        Assert.assertTrue(frameManager.getFrameCount() == 0);
    }

    @Test
    public void testClearStack() {
        IFrameManager frameManager = FrameManager.getInstance();

        AppFrame frame = new AppFrame();
        AppFrame frame2 = new AppFrame();
        AppFrame frame3 = new AppFrame();
        frameManager.startFrame(frame);
        frameManager.startFrame(frame2);
        frameManager.startFrame(frame3);

        Assert.assertTrue(frameManager.getFrameCount() == 3);

        AppFrame frame4 = new AppFrame();
        frameManager.clearStack(frame4);

        Assert.assertTrue(frameManager.getFrameCount() == 1);
    }

    @Test
    public void testFindFrameByToken() {
        IFrameManager frameManager = FrameManager.getInstance();

        AppFrame frame = new AppFrame();
        AppFrame frame2 = new AppFrame();
        AppFrame frame3 = new AppFrame();
        frameManager.startFrame(frame);
        frameManager.startFrame(frame2);
        frameManager.startFrame(frame3);

        Assert.assertTrue(frameManager.getFrameCount() == 3);

        AppFrame found = frameManager.findFrameByToken(frame2.getFrameToken());
        Assert.assertEquals(found, frame2);
    }
}