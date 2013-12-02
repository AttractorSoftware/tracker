package net.itattractor;

import net.itattractor.activity.KeyboardActivityListener;
import net.itattractor.activity.MouseActivityListener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class EventCounter {
    private MouseActivityListener mouseActivityListener;
    private KeyboardActivityListener keyboardActivityListener;

    public void reset() {
        createInstaces();
        addListeners();
    }

    public void init() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            ex.printStackTrace();
        }
        createInstaces();
        addListeners();
    }

    private void createInstaces() {
        mouseActivityListener = new MouseActivityListener();
        keyboardActivityListener = new KeyboardActivityListener();
    }

    private void addListeners() {
        GlobalScreen.getInstance().addNativeKeyListener(keyboardActivityListener);
        GlobalScreen.getInstance().addNativeMouseListener(mouseActivityListener);
        GlobalScreen.getInstance().addNativeMouseMotionListener(mouseActivityListener);
    }

    public int getMouseCounter() {
        return mouseActivityListener.getMouseCounter();
    }

    public int getKeyCounter() {
        return keyboardActivityListener.getKeycounter();
    }
}