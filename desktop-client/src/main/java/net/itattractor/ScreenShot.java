package net.itattractor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.*;
import java.util.Calendar;
import java.util.Date;

public class ScreenShot extends Thread {
    private final ScreenshotSender screenshotSender;
    private String homeDirectory;
    private Ticket currentTicket;

    public ScreenShot(ConnectionProvider connectionProvider) {
        homeDirectory = System.getProperty("user.home");
        screenshotSender = new ScreenshotSender();
    }

    public static boolean shouldSend(long epoch) {

        Calendar sentDate = Calendar.getInstance();
        sentDate.setTimeInMillis(epoch);
        int minutes = sentDate.get(Calendar.MINUTE);
        if (minutes%10==0 && EventCounter.keyCounter>=10 && EventCounter.mouseCounter>=10)
            return true;
        else
            return false;
    }

    public void screenShot() {
        screenshotSender.setTicket(currentTicket);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        try {
            if (shouldSend(System.currentTimeMillis()))
            {
            File screenshot = new File(homeDirectory + Config.getValue("screenshotDirectory") + new Date().toString() + "." + Config.getValue("screenshotExtension"));
            ImageIO.write(screenShot, Config.getValue("screenshotExtension"), screenshot);
                screenshotSender.sendScreenshot(screenshot);
                EventCounter.keyCounter=0;
                EventCounter.mouseCounter=0;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
    public void setCurrentTicket(Ticket currentTicket) {
        this.currentTicket = currentTicket;
    }
}
