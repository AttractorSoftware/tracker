package net.itattractor.forms.login;

import net.itattractor.Config;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
                try {
                    actionListener.submitPressed();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
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
        urlField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        actionListener.submitPressed();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        actionListener.submitPressed();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        actionListener.submitPressed();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        submitButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        actionListener.submitPressed();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        cancelButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        actionListener.submitPressed();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
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

    private void createUIComponents() {
        urlField = new JTextField(Config.getValue("url"));
        usernameField = new JTextField(Config.getValue("username"));
    }
}
