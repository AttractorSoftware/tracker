package net.itattractor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommentSenderTest {
    @Before
    public void setUp() throws Exception {
        Config.init();
        ConnectionProvider.createInstance("http://tracker-trac.demo.esdp.it-attractor.net/", "demo", "123");
    }

    @Test
    public void testSendComment() throws Exception {
        CommentSender commentSender = new CommentSender();

        Assert.assertTrue(commentSender.sendComment(4, "test87 test2 test2"));
    }
}
