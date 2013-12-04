package net.itattractor;

import net.itattractor.config.TestConfigProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommentSenderTest {
    @Before
    public void setUp() throws Exception {
        ConnectionProvider.createInstance("http://127.0.0.1:8000/trac-env", "tester", "tester");
    }

    @Test
    public void testSendComment() throws Exception {
        WorkLogSender workLogSender = new WorkLogSender();
        workLogSender.setConfig(new TestConfigProvider());
        TimeProvider timeProvider=new FakeTimeProvider();
        workLogSender.setTimeProvider(timeProvider);
        Assert.assertTrue(workLogSender.sendWorkLog(4, "test87 test2 test2"));
    }
}
