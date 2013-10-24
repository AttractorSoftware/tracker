package net.itattractor;

public class Main {
    public static void main(String args[])
    {
        AppLauncher appLauncher = new AppLauncher();
        appLauncher.start();

        Thread.setDefaultUncaughtExceptionHandler(new Handler());
    }
}