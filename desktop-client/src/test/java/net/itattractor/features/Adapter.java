package net.itattractor.features;

import net.itattractor.AppLauncher;
import net.itattractor.config.TestConfigProvider;
import net.itattractor.screenshot.TimerTaskImpl;
import org.uispec4j.Window;

public class Adapter {

    private AppLauncher appLauncher;

    public Adapter(){
        appLauncher = new AppLauncher();
        appLauncher.setConfig(new TestConfigProvider());
        appLauncher.init();
    }

    public Window getMainWindow() {
        return new Window(appLauncher.getMainFrame());
    }

    public TimerTaskImpl getTimerTask() {
        return appLauncher.getTimerTask();
    }
}
