package net.itattractor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TaskReader {
    private Downloader downloader;
    private List<String> taskRows;

    public TaskReader(Downloader downloader) {
        this.downloader = downloader;
        readTasks();
    }

    public List<String> readTasks(){
        try {
            taskRows = Files.readAllLines(Paths.get(downloader.getFileName()), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskRows;
    }

    public Ticket[] getTasksList() {
        Ticket[] taskList = new Ticket[taskRows.size() - 1];
        for (int i = 1; i < taskRows.size(); i++)
        {
            int taskId = Integer.parseInt(taskRows.get(i).substring(0, taskRows.get(i).indexOf(',')));
            String taskSummary = taskRows.get(i).substring(taskRows.get(i).indexOf(',') + 1);
            taskList[i - 1] = new Ticket(taskId, taskSummary);
        }
        return taskList;
    }
}
