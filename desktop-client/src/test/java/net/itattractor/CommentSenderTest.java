package net.itattractor;

import org.junit.Assert;
import org.junit.Test;

public class CommentSenderTest {
    @Test
    public void testSendComment() {
        CommentSender commentSender = new CommentSender(new ConnectionProvider("http://tracker-trac.demo.esdp.it-attractor.net/", "beknazar", "beknazar31"));
        Assert.assertTrue(commentSender.sendComment(4, "kdjlksjdak"));
    }
}
