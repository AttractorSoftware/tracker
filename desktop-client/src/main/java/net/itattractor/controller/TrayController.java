package net.itattractor.controller;

import net.itattractor.HotKeyRegister;
import net.itattractor.forms.tray.TrayActionListener;
import net.itattractor.forms.tray.Tray;
import net.itattractor.manager.WindowManager;

public class TrayController implements TrayActionListener {
    private WindowManager manager;
    private HotKeyRegister hotKeyRegister;

    public TrayController(WindowManager manager, Tray tray) {
        this.manager = manager;
        tray.setActionListener(this);
    }

    @Override
    public void doubleClicked() {
        manager.show();
    }

    @Override
    public void openPressed() {
        manager.show();

    }

    @Override
    public void exitPressed() {
        hotKeyRegister.deregister();
        System.exit(0);
    }

    public void setHotKeyRegister(HotKeyRegister hotKeyRegister) {
        this.hotKeyRegister = hotKeyRegister;
    }
}
