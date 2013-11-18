package net.itattractor.controller;

import net.itattractor.ConnectionProvider;
import net.itattractor.forms.login.LoginForm;
import net.itattractor.forms.login.LoginFormActionListener;
import net.itattractor.manager.WindowManager;

import javax.swing.*;

public class LoginFormController implements LoginFormActionListener {
    private LoginForm loginForm;
    private ConnectionProvider provider;
    private JFrame loginFrame;
    private WindowManager manager;

    public LoginFormController(LoginForm loginForm, WindowManager manager) {
        this.loginForm = loginForm;
        this.manager = manager;
        this.loginForm.setActionListener(this);
    }

    @Override
    public void submitPressed() throws Exception {
        String url = loginForm.getUrlField().getText();
        String username = loginForm.getUsernameField().getText();
        String password = new String(loginForm.getPasswordField().getPassword());

        if (url.equals("") || username.equals("") || password.equals("")) {
            showDialog("Wrong username or password. Try again!");
        } else {

            ConnectionProvider.createInstance(url, username, password);

            provider = ConnectionProvider.getInstance();

            if (provider.isAuthenticated()) {
                manager.hide();
                manager.setState(manager.getTasksFormState());
                manager.show();
            } else
                showDialog("Wrong username or password. Try again!");
        }
    }

    private void showDialog(String message) {
        JOptionPane.showMessageDialog(manager.getFrame(), message, "login form message dialog", JOptionPane.WARNING_MESSAGE);
    }


    @Override
    public void cancelPressed() {
        System.exit(0);
    }

    public void start() {
        manager.show();
    }
}
