package net.itattractor.features.helper;

import net.itattractor.features.Adapter;
import net.itattractor.screenshot.Timer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Driver {
    private  static WebDriver serverInstance;

    public static Adapter getClientInstance() throws Exception{
        if (clientInstance == null) {
            clientInstance = new Adapter();
        }
        return clientInstance;
    }

    private static Adapter clientInstance;

    public static WebDriver getServerInstance() throws Exception {
        if(serverInstance == null){
            serverInstance = new FirefoxDriver();
        }
        return serverInstance;
    }

    public static void closeServerInstance() {
        if (serverInstance != null) {
            serverInstance.close();
            serverInstance = null;
        }
    }

    public static void reset() {
       if(clientInstance != null) {
           Timer screenshotTimer = (Timer) clientInstance.getScreenshotTimer();
           if(screenshotTimer != null){
               screenshotTimer.setFinished(true);
           }
           clientInstance = null;
       }
    }
}
