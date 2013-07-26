package net.itattractor;

import net.itattractor.forms.LoginForm;
import net.itattractor.forms.LoginFormActionListener;

import javax.swing.*;

public class AppLauncher {
    private JFrame frame;
    private LoginForm loginForm;

    public void start() {
        frame = new JFrame("login form");

        loginForm = new LoginForm();
        loginForm.setActionListener(new LoginFormActionListenerImpl());
        frame.add(loginForm.getContentPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(320, 240);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private class LoginFormActionListenerImpl implements LoginFormActionListener {

        private ConnectionProvider provider;

        @Override
        public void submitPressed() {
            String url = loginForm.getUrlField().getText();
            String username = loginForm.getUsernameField().getText();
            String password = new String(loginForm.getPasswordField().getPassword());

            if (url.equals("") || username.equals("") || password.equals("")) {
                showDialog("Wrong username or password. Try again!");
            } else {
                provider = new ConnectionProvider(url, username, password);

                if (provider.isAuthenticated()) {
                    frame.setVisible(false);
                    new DialogRecorder(provider);
                    new Thread(new ScreenShot()).start();
                } else
                    showDialog("Неверный пароль или логин.");
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
