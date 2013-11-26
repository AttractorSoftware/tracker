package net.itattractor.screenshot;

import net.itattractor.Config;
import net.itattractor.EventCounter;
import net.itattractor.Ticket;
import net.itattractor.TimeProvider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Creator implements Command {

    private Ticket ticket;
    private TimeProvider timeProvider;

    public Creator(Ticket ticket) {
        this.setTicket(ticket);
    }

    @Override
    public void execute() {
        Queue.append(createScreenshot());
        EventCounter.reset();
    }

    private Screenshot createScreenshot() {
        Screenshot screenshot = null;
        try {
            Robot robot = new Robot();
            BufferedImage screenshotImage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            String screenshotFileName = screenShotFileName();
            String screenshotFilePath = screenShotPath(screenshotFileName);
            File screenshotFile = new File(screenshotFilePath);
            ImageIO.write(screenshotImage, Config.getValue("screenshotExtension"), screenshotFile);

            screenshot = new Screenshot();
            screenshot.setTicketId(this.ticket.getTicketId());
            screenshot.setFileBody(screenshotFile);
            screenshot.setFileName(screenshotFileName);
            screenshot.setTime(Long.toString(timeProvider.getTimeInMilliseconds()));
            screenshot.setKeyboardEventCount(EventCounter.keyCounter);
            screenshot.setMouseEventCount(EventCounter.mouseCounter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenshot;
    }

    private String screenShotPath(String screenshotFileName) {
        return System.getProperty("user.home") + Config.getValue("screenshotDirectory") + screenshotFileName;
    }

    private String screenShotFileName() {
        return timeProvider.getDate() + "." + Config.getValue("screenshotExtension");
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void setTimeProvider(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }
}
