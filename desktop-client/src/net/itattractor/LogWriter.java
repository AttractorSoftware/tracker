package net.itattractor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class LogWriter {
    private String homeDirectory;
    public LogWriter()
    {
        homeDirectory = System.getProperty("user.home");
    }
    public void saveStart(String task)
    {
        try {
            FileWriter fileWriter = new FileWriter(homeDirectory + "/tracker.xml", true);
            fileWriter.write("<Task name=\"" + task + "\">\n   <DateStart value=\"" + new Date() + "\" />\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Fail dostupen tolko dlya chteniya");
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
            System.out.println("Fail dostupen tolko dlya chteniya");
        }
    }

    public void saveEnd()
    {
        try {
            FileWriter fileWriter = new FileWriter(homeDirectory + "/tracker.xml", true);
            fileWriter.write("   <DateEnd value=\"" + new Date() + "\" />\n");
            fileWriter.write("</Task>\n\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Fail dostupen tolko dlya chteniya");
        }
    }
}
