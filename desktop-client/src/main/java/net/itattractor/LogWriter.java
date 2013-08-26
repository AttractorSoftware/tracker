package net.itattractor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class LogWriter {
    private Ticket currentTicket;
    private String homeDirectory;

    public LogWriter(Ticket ticket) {
        this.currentTicket = ticket;
        homeDirectory = System.getProperty("user.home");
        saveStart();
    }

    private void saveStart()
    {
        try {
            FileWriter fileWriter = new FileWriter(homeDirectory + "/tracker.xml", true);
            fileWriter.write("<Task id=\"" + currentTicket.getTicketId() + "\" name=\"" + currentTicket.getTicketSummary() + "\">\n   <DateStart value=\"" + new Date() + "\" />\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDescription(String description)
    {
        try {
            FileWriter fileWriter = new FileWriter(homeDirectory + "/tracker.xml", true);
            fileWriter.write("   <RecordQuery>\n");
            fileWriter.write("      <DateTime value=\"" + new Date() + "\" />\n      <Description>" + description + "</Description>\n");
            fileWriter.write("   </RecordQuery>\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close()
    {
        try {
            FileWriter fileWriter = new FileWriter(homeDirectory + "/tracker.xml", true);
            fileWriter.write("   <DateEnd value=\"" + new Date() + "\" />\n");
            fileWriter.write("</Task>\n\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
