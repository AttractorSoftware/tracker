package net.itattractor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Save {
    public Save(String task, String user, String description) {
        try {
            FileWriter fileWriter = new FileWriter("/home/esdp/tracker.log", true);
            fileWriter.write(task + "  |  " + user + "  |  " + new Date() + "  |  " + description + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Fail dostupen tolko dlya chteniya");
        }
    }
}
