package net.itattractor.states;

import javax.swing.*;

public interface State {
    void show();

    JFrame getFrame();

    void hide();
}
