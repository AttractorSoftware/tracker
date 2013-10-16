package net.itattractor.features;

import net.itattractor.AppLauncher;
import org.uispec4j.UISpecAdapter;
import org.uispec4j.Window;

import java.awt.*;

public class Adapter implements UISpecAdapter {

    private AppLauncher appLauncher;

    public Adapter(){
        appLauncher = new AppLauncher();
        appLauncher.start();
    }

    @Override
    public Window getMainWindow() {
        return new Window(appLauncher.getLoginFrame());
    }

    public Window getTasksWindow(){
        return new Window(appLauncher.getTasksFrame());
    }

    public Window getRecordWindow(){
        return new Window(appLauncher.getRecordFrame());
    }

    public TrayIcon getTrayIcon() {
        return appLauncher.getTrayIcon();
    }
}
