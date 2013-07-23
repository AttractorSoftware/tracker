package net.itattractor;

import org.junit.Assert;
import org.junit.Test;

public class AuthenticationTest {
    @Test
    public void testSuccessfullyAuthentication() throws Exception {
        Authentication authentication = new Authentication("http://tracker-trac.demo.esdp.it-attractor.net/", "beknazar", "beknazar31");
        int actual = authentication.authenticate();
        int expected = 200;
        Assert.assertEquals(expected, actual);
    }
}
