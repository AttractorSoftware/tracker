package net.itattractor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommentSenderTest {
    @Before
    public void setUp() throws Exception {
        Config.init();
        ConnectionProvider.createInstance("http://127.0.0.1:8000/trac-env", "tester", "tester");
    }

    @Test
    public void testSendComment() throws Exception {
        CommentSender commentSender = new CommentSender();

        Assert.assertTrue(commentSender.sendComment(4, "test87 test2 test2"));
    }
}
