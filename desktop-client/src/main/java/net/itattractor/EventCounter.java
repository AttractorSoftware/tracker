package net.itattractor;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class EventCounter implements NativeKeyListener {
    public static int keyCounter=0;
    public static int mouseCounter=0;

    public void nativeKeyPressed(NativeKeyEvent e) {
        keyCounter++;
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    public static void reset() {
        keyCounter = 0;
        mouseCounter = 0;
    }


    public static class GlobalMouseListenerExample implements NativeMouseInputListener {
        public void nativeMouseClicked(NativeMouseEvent e) {
            mouseCounter++;
        }

        public void nativeMousePressed(NativeMouseEvent e) {
        }

        public void nativeMouseReleased(NativeMouseEvent e) {
        }

        public void nativeMouseMoved(NativeMouseEvent e) {
        }

        public void nativeMouseDragged(NativeMouseEvent e) {
        }


    }
    public static void ActivateEvent() {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            ex.printStackTrace();
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
        //Construct the example object.
        GlobalMouseListenerExample example = new GlobalMouseListenerExample();
        //Construct the example object and initialze native hook.
        GlobalScreen.getInstance().addNativeKeyListener(new EventCounter());
        //Add the appropriate listeners for the example object.
        GlobalScreen.getInstance().addNativeMouseListener(example);
        GlobalScreen.getInstance().addNativeMouseMotionListener(example);
    }
}