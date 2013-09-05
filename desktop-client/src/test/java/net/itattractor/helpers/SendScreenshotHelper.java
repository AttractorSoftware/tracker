package net.itattractor.helpers;

import net.itattractor.Config;
import net.itattractor.ConnectionProvider;
import net.itattractor.ScreenshotSender;
import org.junit.Assert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

public class SendScreenshotHelper {

    public void sendScreenshot(String host, String username, String password) throws Exception {
        Config.init();
        ConnectionProvider.createInstance("http://tracker-trac.demo.esdp.it-attractor.net/", "demo", "123");
        ScreenshotSender screenshotSender = new ScreenshotSender();
        BufferedImage screenShot = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        File screenshot = new File(System.getProperty("user.home") + Config.getValue("screenshotDirectory") + new Date().toString() + "." + Config.getValue("screenshotExtension"));
        ImageIO.write(screenShot, Config.getValue("screenshotExtension"), screenshot);
        screenshotSender.sendScreenshot(screenshot);


        Assert.assertTrue(screenshotSender.sendScreenshot(screenshot));
    }
}
