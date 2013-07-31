package test.java.itattractor;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TaskReaderTest {
    @Test
    public void testTaskListIsNotEmpty() throws Exception {
        ConnectionProvider provider = new ConnectionProvider("http://tracker-trac.demo.esdp.it-attractor.net/", "beknazar", "beknazar31");
        Downloader downloader = new Downloader(provider);
        TaskReader taskReader = new TaskReader(downloader);
        List<String> tasks = taskReader.readTasks();
        Assert.assertFalse(tasks.isEmpty());
    }
}
