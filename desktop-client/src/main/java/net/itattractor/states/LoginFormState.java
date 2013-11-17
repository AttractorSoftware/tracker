package net.itattractor.states;

import net.itattractor.forms.login.LoginForm;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class LoginFormState implements State {
    private JFrame loginFrame;
    private LoginForm loginForm;

    @Override
    public void show() {
        loginFrame = new JFrame("login");
        loginFrame.add(loginForm.getContentPanel());
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginFrame.setSize(320, 240);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
        loginFrame.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                loginFrame.isVisible();
            }
        });

    }

    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }
}
