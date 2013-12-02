package net.itattractor.activity;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class MouseActivityListener implements NativeMouseInputListener {
    private int mouseCounter;

    public MouseActivityListener() {
        this.mouseCounter = 0;
    }

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

    public int getMouseCounter() {
        return mouseCounter;
    }


}
