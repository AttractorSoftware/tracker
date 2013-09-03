package net.itattractor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.*;
import java.util.Date;

public class ScreenShot extends Thread {
    private final ScreenshotSender screenshotSender;
    private String homeDirectory;

    public ScreenShot(ConnectionProvider connectionProvider) {
        homeDirectory = System.getProperty("user.home");
        screenshotSender = new ScreenshotSender();
    }

    public void screenShot() {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        try {
            File screenshot = new File(homeDirectory + Config.getValue("screenshotDirectory") + new Date().toString() + "." + Config.getValue("screenshotExtension"));
            ImageIO.write(screenShot, Config.getValue("screenshotExtension"), screenshot);
            screenshotSender.sendScreenshot(screenshot);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void run() {
        File dir = new File(homeDirectory + Config.getValue("screenshotDirectory"));
        dir.mkdirs();
        while (true) {
            try {
                int screenshotPeriod = Integer.parseInt(Config.getValue("screenshotPeriod"));
                Thread.sleep(screenshotPeriod);
                this.screenShot();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
