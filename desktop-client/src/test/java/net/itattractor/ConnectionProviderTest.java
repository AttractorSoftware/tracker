package net.itattractor;

import org.apache.http.auth.AuthScope;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConnectionProviderTest {

    private String host;
    private String username;
    private String password;

    @Before
    public void init() {
        host = "http://tracker-trac.demo.esdp.it-attractor.net/";
        username = "demo";
        password = "123";
        ConnectionProvider.createInstance(host, username, password);
    }

    @Test
    public void testIsAuthenticated() throws Exception {
        Assert.assertTrue(ConnectionProvider.getInstance().isAuthenticated());
    }

    @Test
    public void testGetHost() throws Exception {
        Assert.assertEquals(host, ConnectionProvider.getInstance().getHost());
    }

    @Test
    public void testGetUsername() throws Exception {
        Assert.assertEquals(username, ConnectionProvider.getInstance().getUsername());
    }

    @Test
    public void testGetHttpClient() throws Exception {
        DefaultHttpClient httpClient = ConnectionProvider.getInstance().getHttpClient();
        CredentialsProvider credentialsProvider = httpClient.getCredentialsProvider();
        String actualPassword = credentialsProvider.getCredentials(AuthScope.ANY).getPassword();
        Assert.assertEquals(password, actualPassword);
    }
}
