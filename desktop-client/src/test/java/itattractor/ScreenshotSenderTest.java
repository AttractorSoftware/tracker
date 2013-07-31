package test.java.itattractor;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: esdp
 * Date: 7/30/13
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScreenshotSenderTest {
    @Test
    public void testSendScreenshot() throws Exception {
        ConnectionProvider provider = new ConnectionProvider("http://localhost:8080/mytrac", "admin", "123123");
        ScreenshotSender screenshotSender = new ScreenshotSender(provider);
        Assert.assertTrue(screenshotSender.sendScreenshot(new File("screenshot.png")));
    }

    @Test
    public void testGetToken() throws Exception {

    }
}
