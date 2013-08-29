package net.itattractor.forms.login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm {
    private JPanel contentPanel;

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JButton submitButton;
    private JButton cancelButton;
    private JTextField urlField;

    private LoginFormActionListener actionListener;

    public LoginForm() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actionListener != null){
                    actionListener.submitPressed();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actionListener != null) {
                    actionListener.cancelPressed();
                }
            }
        });
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
    public void setActionListener(LoginFormActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public JTextField getUrlField() {
        return urlField;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }
}
