package net.itattractor.features;

import net.itattractor.AppLauncher;
import net.itattractor.Config;
import net.itattractor.screenshot.TimerTaskImpl;
import org.uispec4j.UISpecAdapter;
import org.uispec4j.Window;

public class Adapter implements UISpecAdapter {

    private AppLauncher appLauncher;

    public Adapter(){
        appLauncher = new AppLauncher();
        Config.setValue("testMode","true");
        appLauncher.init();
    }

    @Override
    public Window getMainWindow() {
        return new Window(appLauncher.getMainFrame());
    }

    public TimerTaskImpl getTimerTask() {
        return appLauncher.getTimerTask();
    }
}
