package com.tylersuehr.framework;
import org.junit.Assert;
import org.junit.Test;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 */
public class AppDialogTest {
    @Test
    public void testShowDialog() {
        IFrameManager manager = FrameManager.getInstance();

        AppFrame frame = new AppFrame();
        manager.startFrame(frame);

        AppDialog dialog = new AppDialog(frame);
        dialog.showDialog();

        Assert.assertTrue(dialog.isVisible());
        dialog.setVisible(false);
        frame.finish();
    }
}