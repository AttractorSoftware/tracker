package net.itattractor;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class Authentication {
    private DefaultHttpClient httpClient;
    private CredentialsProvider credentialsProvider;
    private HttpGet httpGet;

    public Authentication(String url, String username, String password) {
        httpClient = new DefaultHttpClient();
        credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        httpClient.setCredentialsProvider(credentialsProvider);
        httpGet = new HttpGet(url);
    }

    public int authenticate() throws IOException {
        int statusCode;
        HttpResponse response = httpClient.execute(httpGet);
        statusCode = response.getStatusLine().getStatusCode();
        httpGet.releaseConnection();
        httpClient.getConnectionManager().shutdown();

        return statusCode;
    }
}
