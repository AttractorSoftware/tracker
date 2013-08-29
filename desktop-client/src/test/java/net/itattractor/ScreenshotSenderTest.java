package net.itattractor;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class ScreenshotSenderTest {
    @Test
    public void testSendScreenshot() throws Exception {
        Config.init();
        ConnectionProvider connectionProvider = new ConnectionProvider("http://tracker-trac.demo.esdp.it-attractor.net/", "demo", "123");
        ScreenshotSender screenshotSender = new ScreenshotSender(connectionProvider);
        File screenshot = new File("src/test/resources/Fri Aug 16 14:10:45 KGT 2013.jpg");
        Assert.assertTrue(screenshotSender.sendScreenshot(screenshot));
    }
}
