package net.itattractor;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import sun.misc.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Downloader {
    public static final String QUERY_PART = "&status=accepted&status=assigned&&status=inProgress&format=csv&col=id&col=summary&order=id";
    private final String fileName = "query.csv";
    private String username;
    private String password;
    private String queryUrl;
    private CredentialsProvider credentialsProvider;
    private HttpGet httpGet;
    private DefaultHttpClient httpClient;

    public Downloader(String url, String username, String password) {
        this.username = username;
        this.password = password;
        this.queryUrl = trim(url, '/') + "/query?owner=" + username + QUERY_PART;

    }

    public String downloadFromUrl() {
        httpClient = new DefaultHttpClient();
        credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        httpClient.setCredentialsProvider(credentialsProvider);
        httpGet = new HttpGet(queryUrl);

        HttpResponse response;
        try {
            response = httpClient.execute(httpGet);
            InputStream content = response.getEntity().getContent();
            FileOutputStream outputStream = new FileOutputStream(fileName);
            outputStream.write(IOUtils.readFully(content, -1, false));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    private static String trim(String str, char ch) {
        String destination = str;
        if (str.lastIndexOf(ch) > 0)
        {
             destination = str.substring(0, str.length() - 1);
        }
        return destination;
    }
}
