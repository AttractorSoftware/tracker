package net.itattractor.screenshot;

import net.itattractor.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Creator implements Command {

    private Ticket ticket;
    private TimeProvider timeProvider;

    public Creator(Ticket ticket) {
        this.setTicket(ticket);
    }

    @Override
    public void execute() {

        Robot robot = null;

        try {
            robot = new Robot();
        } catch (AWTException e) { e.printStackTrace(); }

        try {

            BufferedImage screenshotImage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            String screenshotFileName = timeProvider.getDate() + "." + Config.getValue("screenshotExtension");
            String screenshotFilePath = System.getProperty("user.home") + Config.getValue("screenshotDirectory") + screenshotFileName;
            File screenshotFile = new File(screenshotFilePath);
            ImageIO.write(screenshotImage, Config.getValue("screenshotExtension"), screenshotFile);

            Screenshot screenshot = new Screenshot();
            screenshot.setTicketId(this.ticket.getTicketId());
            screenshot.setFileBody(screenshotFile);
            screenshot.setFileName(screenshotFileName);
            screenshot.setTime(Long.toString(timeProvider.getTimeInMilliseconds()));
            screenshot.setKeyboardEventCount(EventCounter.keyCounter);
            screenshot.setMouseEventCount(EventCounter.mouseCounter);

            Queue.append(screenshot);

            EventCounter.keyCounter = 0;
            EventCounter.mouseCounter = 0;

        } catch (IOException e) { e.printStackTrace(); }

    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void setTimeProvider(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }
}
