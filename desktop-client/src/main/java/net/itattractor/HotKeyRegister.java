package net.itattractor;

import jxgrabkey.HotkeyConflictException;
import jxgrabkey.HotkeyListener;
import jxgrabkey.JXGrabKey;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;


public class HotKeyRegister {
    private static final int MY_HOTKEY_INDEX = 10;
    private HotkeyListener hotkeyListener;

    public void register(HotkeyListener hotkeyListener) {
        try {
            System.load(new File("lib/libJXGrabKey.so").getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.hotkeyListener = hotkeyListener;

        try{
            int key = KeyEvent.VK_ENTER | KeyEvent.VK_QUOTE;
            int mask = KeyEvent.CTRL_DOWN_MASK;

            JXGrabKey.getInstance().registerAwtHotkey(MY_HOTKEY_INDEX, mask, key);
        }catch(HotkeyConflictException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
            JXGrabKey.getInstance().cleanUp(); //Automatically unregisters Hotkeys and Listenerss
            //
            return;
        }

        //Add HotkeyListener
        JXGrabKey.getInstance().addHotkeyListener(this.hotkeyListener);
    }

    public void deregister(){
        JXGrabKey.getInstance().unregisterHotKey(MY_HOTKEY_INDEX); //Optional
        JXGrabKey.getInstance().removeHotkeyListener(this.hotkeyListener); //Optional
        JXGrabKey.getInstance().cleanUp();
    }
}
