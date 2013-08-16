package net.itattractor;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TaskReaderTest {
    @Test
    public void testTaskListIsNotEmpty() throws Exception {
        ConnectionProvider provider = new ConnectionProvider("http://tracker-trac.demo.esdp.it-attractor.net/", "beknazar", "beknazar31");
        TaskReader taskReader = new TaskReader(provider);
        /*
        тесты появятся после выполнения тикета об обработках исключений
        */
    }
}
