package net.itattractor;

import net.itattractor.config.ProductionConfigProvider;

public class Main {
    public static void main(String args[])
    {
        AppLauncher appLauncher = new AppLauncher();
        appLauncher.setConfig(new ProductionConfigProvider());
        appLauncher.init();

        Thread.setDefaultUncaughtExceptionHandler(new Handler());

    }
}
