package net.itattractor;

public class Main {
    public static void main(String args[])
    {
        AppLauncher appLauncher = new AppLauncher();
        appLauncher.start();
        Handler a = new Handler();

        Thread.setDefaultUncaughtExceptionHandler(new Handler());
    }
}
