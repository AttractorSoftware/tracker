package net.itattractor;

import org.junit.Assert;
import org.junit.Test;

public class ConnectionProviderTest {
    @Test
    public void testIsAuthenticated() throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProvider("http://localhost:8000/myproject", "bolot", "123");
        Assert.assertTrue(connectionProvider.isAuthenticated());
    }
}
