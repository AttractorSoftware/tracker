package net.itattractor;

import java.io.InputStream;
import java.net.*;
import java.io.*;

import sun.misc.BASE64Encoder;

public class Downloader {
    private String url;
    private String username;
    private String password;
    private String query;
    private String localFileName;
    public Downloader(String url, String username, String password)
    {
        if (url.lastIndexOf('/') > 0)
        {
            query = "query?owner=";
        }
        else
        {
            query = "/query?owner=";
        }
        this.url = url + query + username + "&status=accepted&status=assigned&&status=inProgress&status=reopened&milestone=%D0%98%D1%82%D0%B5%D1%80%D0%B0%D1%86%D0%B8%D1%8F+2&format=csv&col=id&col=summary&order=id";
        this.username = username;
        this.password = password;
        localFileName="query.csv";
        try {
            this.downloadFromUrl(this.url, localFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void downloadFromUrl(String url, String localFilename) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;
        URL oUrl = new URL(url);
        HttpURLConnection oConnect = (HttpURLConnection) oUrl.openConnection();
        oConnect.setRequestMethod("GET");
        oConnect.setDoInput(true);
        byte[] byEncodedPassword = (this.username + ":" + this.password).getBytes();
        BASE64Encoder byEncoder = new BASE64Encoder();
        oConnect.setRequestProperty("Authorization", "Basic " + byEncoder.encode(byEncodedPassword));
        try {
            is = oConnect.getInputStream();
            fos = new FileOutputStream(localFilename);
            byte[] buffer = new byte[4096];
            int len;
            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } finally {
                if (fos != null) {
                    fos.close();
                }
            }
        }
    }
}
