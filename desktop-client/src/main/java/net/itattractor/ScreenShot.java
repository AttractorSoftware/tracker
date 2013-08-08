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
    private final int timeInMilisec = 10000;
    private static final String SCREENSHOT_EXTENSION = "jpg";
    private static final String SCREENSHOT_DIR = "/screen/";

    public ScreenShot(ConnectionProvider connectionProvider) {
        homeDirectory = System.getProperty("user.home");
        screenshotSender = new ScreenshotSender(connectionProvider);
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

            File screenshot = new File(homeDirectory + SCREENSHOT_DIR + new Date().toString() + "." + SCREENSHOT_EXTENSION);
            ImageIO.write(screenShot, SCREENSHOT_EXTENSION, screenshot);
            screenshotSender.sendScreenshot(screenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        File dir = new File(homeDirectory + SCREENSHOT_DIR);
        dir.mkdirs();
        while (true) {
            try {
                Thread.sleep(timeInMilisec);
                this.screenShot();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
