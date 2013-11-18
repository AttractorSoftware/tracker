package net.itattractor.states;

import net.itattractor.manager.WindowManager;

import javax.swing.*;

public interface State {
    void show();

    JFrame getFrame();

    void hide();

    void setManager(WindowManager windowManager);
}
