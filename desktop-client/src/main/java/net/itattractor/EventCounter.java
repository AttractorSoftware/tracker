package net.itattractor;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class EventCounter implements NativeKeyListener {
    static int keyCounter=0;
    static int mouseCounter=0;

    public void nativeKeyPressed(NativeKeyEvent e) {
        keyCounter++;
        //System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        //    if (e.getKeyCode() == NativeKeyEvent.VK_ESCAPE) {
        //       GlobalScreen.unregisterNativeHook();
        //    }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        //  System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        ;
    }


    public static class GlobalMouseListenerExample implements NativeMouseInputListener {
        public void nativeMouseClicked(NativeMouseEvent e) {
             mouseCounter++;
            //System.out.println("Mosue Clicked: " + e.getClickCount());
        }

        public void nativeMousePressed(NativeMouseEvent e) {
            //  System.out.println("Mosue Pressed: " + e.getButton());
            // no code here
        }

        public void nativeMouseReleased(NativeMouseEvent e) {
            //  System.out.println("Mosue Released: " + e.getButton());
            // no code here
        }

        public void nativeMouseMoved(NativeMouseEvent e) {
            // no code here
        }

        public void nativeMouseDragged(NativeMouseEvent e) {
            //   System.out.println("Mosue Dragged: " + e.getX() + ", " + e.getY());
            // no code here
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