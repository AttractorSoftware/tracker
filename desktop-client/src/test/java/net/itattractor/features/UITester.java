package net.itattractor.features;

import sun.awt.AppContext;
import sun.awt.SunToolkit;

import javax.swing.*;
import java.awt.*;
import java.lang.ref.WeakReference;
import java.util.Vector;

public class UITester {

    private static void doGuiUpdate( final Runnable runnable ) {
        SwingUtilities.invokeLater(runnable);
        waitForEventsFinish();
    }

    public static void waitForEventsFinish() {
        ((SunToolkit) Toolkit.getDefaultToolkit()).realSync();
    }

    public static void removeAllWindowsFromAwtAppContext() {
        doGuiUpdate(new Runnable() {
            public void run() {
                Vector<WeakReference<Window>> windowList = (Vector<WeakReference<Window>>) AppContext.getAppContext().get(Window.class);
                if (windowList != null) {
                    for (WeakReference<Window> wrw : windowList) {
                        wrw.get().setVisible(false);
                    }
                    windowList.clear();
                }
            }
        });
    }
}

