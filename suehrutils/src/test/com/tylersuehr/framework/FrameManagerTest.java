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
        FrameManager manager = FrameManager.getInstance();

        AppFrame result = new AppFrame("Test", manager);

        manager.startFrame(result);

        Assert.assertTrue(manager.getFrameCount() > 0);
    }

    @Test
    public void testStopFrame() {
        FrameManager manager = FrameManager.getInstance();

        AppFrame result = new AppFrame("Test", manager);
        manager.startFrame(result);

        Assert.assertTrue(manager.getFrameCount() > 0);

        manager.finish(result);
        Assert.assertTrue(manager.getFrameCount() <= 0);
    }

    @Test
    public void testClearBackStack() {
        FrameManager manager = FrameManager.getInstance();
        manager.startFrame(new AppFrame("test1", manager));
        manager.startFrame(new AppFrame("test2", manager));
        manager.startFrame(new AppFrame("test3", manager));
        manager.startFrame(new AppFrame("test4", manager));

        Assert.assertTrue(manager.getFrameCount() == 4);

        manager.clearBackStack(new AppFrame("test5", manager));
        Assert.assertTrue(manager.getFrameCount() == 1);
    }

    @Test
    public void testFindFrameByToken() {
        FrameManager manager = FrameManager.getInstance();

        AppFrame result = new AppFrame("findTest", manager);
        manager.startFrame(new AppFrame("test1", manager));
        manager.startFrame(new AppFrame("test2", manager));
        manager.startFrame(result);
        manager.startFrame(new AppFrame("test3", manager));
        manager.startFrame(new AppFrame("test4", manager));

        AppFrame found = manager.findFrameByToken(result.getFrameToken());
        Assert.assertEquals(result, found);
    }

    @Test
    public void testSavedInstanceState() {
        FrameManager manager = FrameManager.getInstance();

        AppFrame result = new AppFrame("test", manager);

        Bundle bundle = new Bundle();
        bundle.putBoolean("testBool", true);

        result.saveInstance(bundle);

        Assert.assertTrue(manager.savedInstanceState.getBoolean("testBool"));
    }
}