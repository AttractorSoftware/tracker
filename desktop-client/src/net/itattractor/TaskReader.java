package net.itattractor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TaskReader {

    private String fileName;

    public TaskReader(String fileName) {
        this.fileName = fileName;
    }

    public List<String> readTasks(){
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(fileName), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
