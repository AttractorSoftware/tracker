package net.itattractor;

import net.itattractor.forms.LoginForm;
import net.itattractor.forms.LoginFormActionListener;

import javax.swing.*;
import java.io.IOException;

public class AppLauncher {
    private JFrame frame;
    private LoginForm loginForm;
    private Authentication authentication;

    public void start() {
        frame = new JFrame("login form");

        loginForm = new LoginForm();
        loginForm.setActionListener(new MyLoginFormActionListener());
        frame.add(loginForm.getContentPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(320, 240);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private class MyLoginFormActionListener implements LoginFormActionListener {

        @Override
        public void submitPressed() {
            String url = loginForm.getUrlField().getText();
            String username = loginForm.getUsernameField().getText();
            String password = new String(loginForm.getPasswordField().getPassword());

            if (url.equals("") || username.equals("") || password.equals("")) {
                showDialog("Wrong username or password. Try again!");
            } else {

                authentication = new Authentication(url, username, password);
                int responseCode = 0;
                try {
                    responseCode = authentication.authenticate();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                if (responseCode == ResponseStatus.OK) {
                    frame.setVisible(false);
                    new DialogRecorder(url, username, password);

                    new Thread(new ScreenShot()).start();

                } else if (responseCode == ResponseStatus.UNAUTHORIZED) {
                    showDialog("Неверный пароль или логин.");
                } else {
                    showDialog("Неверный url.");
                }
            }
        }

        private void showDialog(String message) {
            JOptionPane.showMessageDialog(frame, message, "login form message dialog", JOptionPane.WARNING_MESSAGE);
        }

        @Override
        public void cancelPressed() {
            System.exit(0);
        }
    }
}
