package net.itattractor;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: esdp
 * Date: 7/10/13
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return lines;
    }
}
