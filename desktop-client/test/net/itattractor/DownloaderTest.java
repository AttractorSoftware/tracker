package net.itattractor;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class DownloaderTest {

    @Test
    public void testGetFilePath() throws IOException {
        ConnectionProvider provider = new ConnectionProvider("http://tracker-trac.demo.esdp.it-attractor.net/", "beknazar", "beknazar31");
        Downloader downloader = new Downloader(provider);
        File file = new File(downloader.downloadFromUrl());
        Assert.assertTrue(file.exists());
    }

}
