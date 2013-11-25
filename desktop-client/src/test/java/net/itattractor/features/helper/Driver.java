package net.itattractor.features.helper;

import net.itattractor.AppLauncher;
import net.itattractor.features.Adapter;
import net.itattractor.features.UITester;
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
           if (clientInstance.getTimerTask() != null) {
               clientInstance.getTimerTask().cancel();
           }
           UITester.removeAllWindowsFromAwtAppContext();
           AppLauncher.getTray().removeTrayIcon();
           clientInstance = null;
       }
    }
}
