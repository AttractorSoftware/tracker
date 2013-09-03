package net.itattractor;

import org.junit.Assert;
import org.junit.Test;

public class ConnectionProviderTest {
    @Test
    public void testIsAuthenticated() throws Exception {
        ConnectionProvider.createInstance("http://tracker-trac.demo.esdp.it-attractor.net/", "demo", "123");
        Assert.assertTrue(ConnectionProvider.getInstance().isAuthenticated());
    }
}
