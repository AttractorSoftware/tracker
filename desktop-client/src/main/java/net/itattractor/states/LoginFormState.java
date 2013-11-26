package net.itattractor.states;

import net.itattractor.forms.login.LoginForm;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class LoginFormState implements State {
    private JFrame frame;
    private LoginForm form;

    public LoginFormState() {
        frame = new JFrame("login");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(320, 240);
        frame.setLocationRelativeTo(null);
        frame.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                frame.isVisible();
            }
        });

    }

    @Override
    public void show() {
        frame.add(form.getContentPanel());
        frame.setVisible(true);
    }

    public void setForm(LoginForm form) {
        this.form = form;
    }

    @Override
    public JFrame getFrame() {
        return frame;
    }

    @Override
    public void hide() {
        frame.setVisible(false);
    }

}
