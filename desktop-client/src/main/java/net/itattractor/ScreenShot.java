package net.itattractor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class ScreenShot extends Thread {
    private final ScreenshotSender screenshotSender;
    private String homeDirectory;
    private Ticket currentTicket;
    private TimeProvider timeProvider;

    public ScreenShot(ConnectionProvider connectionProvider) {
        homeDirectory = System.getProperty("user.home");
        screenshotSender = new ScreenshotSender();
    }

    public boolean shouldSend(long epoch) {

        Calendar sentDate = Calendar.getInstance();
        sentDate.setTimeInMillis(epoch);
        int minutes = sentDate.get(Calendar.MINUTE);
        return ((minutes % 10) == 0) && (EventCounter.keyCounter >= 10) && (EventCounter.mouseCounter >= 10);
    }

    public void screenShot() {
        screenshotSender.setTimeProvider(timeProvider);
        screenshotSender.setTicket(currentTicket);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        try {

            if (shouldSend(timeProvider.getTimeInMilliseconds())) {
                BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

                File screenshot = new File(homeDirectory + Config.getValue("screenshotDirectory") + timeProvider.getDate() + "." + Config.getValue("screenshotExtension"));
                ImageIO.write(screenShot, Config.getValue("screenshotExtension"), screenshot);
                screenshotSender.sendScreenshot(screenshot);
                EventCounter.keyCounter = 0;
                EventCounter.mouseCounter = 0;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
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

    public void setCurrentTicket(Ticket currentTicket) {
        this.currentTicket = currentTicket;
    }

    public void setTimeProvider(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }
}
