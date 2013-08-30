package net.itattractor;

import org.junit.Assert;
import org.junit.Test;

public class CommentSenderTest {
    @Test
    public void testSendComment() {
        Config.init();
        CommentSender commentSender = new CommentSender(new ConnectionProvider("http://tracker-trac.demo.esdp.it-attractor.net/", "demo", "123"));
        Assert.assertTrue(commentSender.sendComment(4, "test7 test2 test2"));
    }
}
