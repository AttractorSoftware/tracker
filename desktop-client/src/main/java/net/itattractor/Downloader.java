package net.itattractor;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import sun.misc.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Downloader {
    public static final String QUERY_PART = "&status=accepted&status=assigned&&status=inProgress&format=csv&col=id&col=summary&order=id";
    private final String fileName = "query.csv";
    private String queryUrl;
    private HttpGet httpGet;
    private ConnectionProvider connectionProvider;

    public Downloader(ConnectionProvider provider) {
        this.queryUrl = provider.getHost() + "/query?owner=" + provider.getUsername() + QUERY_PART;
        System.out.println(this.queryUrl);
        this.connectionProvider = provider;
        downloadFromUrl();
    }

    public String downloadFromUrl() {
        httpGet = new HttpGet(queryUrl);

        HttpResponse response;
        try {
            DefaultHttpClient httpClient1 = connectionProvider.getHttpClient();
            response = httpClient1.execute(httpGet);
            InputStream content = response.getEntity().getContent();
            FileOutputStream outputStream = new FileOutputStream(fileName);
            outputStream.write(IOUtils.readFully(content, -1, false));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
