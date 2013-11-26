package net.itattractor;

public class Main {
    public static void main(String args[])
    {
        Config.init("production");
        AppLauncher appLauncher = new AppLauncher();
        appLauncher.init();

        Thread.setDefaultUncaughtExceptionHandler(new Handler());
    }
}