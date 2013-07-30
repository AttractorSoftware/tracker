package net.itattractor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.*;
import java.util.Date;

public class ScreenShot extends Thread {
    private String homeDirectory;
    private ScreenshotSender screenshotSender;

    public ScreenShot(ConnectionProvider connectionProvider) {
        homeDirectory = System.getProperty("user.home");
        screenshotSender = new ScreenshotSender(connectionProvider);
    }

    private final int timeInMilisec = 10000;

    public void screenShot() {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        try {

            File screenshot = new File(homeDirectory + "/screen/" + new Date().toString());
            ImageIO.write(screenShot, "JPG", screenshot);
            screenshotSender.sendScreenshot(screenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        File dir = new File(homeDirectory + "/screen/");
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
