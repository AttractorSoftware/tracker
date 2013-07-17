package net.itattractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: esdp
 * Date: 7/16/13
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Login extends JFrame implements ActionListener {

    private JLabel loginLabel;

    private JLabel urlLabel;
    private JTextField urlField;

    private JLabel usernameLabel;
    private JTextField usernameField;

    private JLabel passwordLabel;
    private JTextField passwordField;

    public JButton submitButton;

    private JButton cancelButton;

    private Authentication authentication;

    private String url;
    private String username;
    private String password;


    public Login() {
        setTitle("Trac - Log In");
        initializeElements();
    }

    public void initializeElements() {

        loginLabel = new JLabel("Please login information:");
        add(loginLabel);

        urlLabel = new JLabel("URL");
        add(urlLabel);

        urlField = new JTextField("http://tracker.esdp.it-attractor.net/");
        urlField.setPreferredSize(new Dimension(280, 25));
        add(urlField);

        usernameLabel = new JLabel("User Name:");
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 25));
        add(usernameField);

        passwordLabel = new JLabel("Password:");
        add(passwordLabel);

        passwordField = new JTextField();
        passwordField.setPreferredSize(new Dimension(200, 25));
        add(passwordField);

        submitButton = new JButton("Login");

        submitButton.addActionListener(this);
        add(submitButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelListener());
        add(cancelButton);

        setResizable(false);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(340, 200));
        setVisible(true);
    }

    private void showDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Login", JOptionPane.WARNING_MESSAGE);
    }


    public void actionPerformed(ActionEvent e) {

        url = urlField.getText().trim();
        username = usernameField.getText().trim();
        password = passwordField.getText().trim();

        if (url.equals("") || username.equals("") || passwordField.getText().trim().equals("")) {
            showDialog("Wrong username or password. Try again!");
        } else {

            authentication = new Authentication();
            int responseCode = authentication.auth(url, username, password);

            if (responseCode == ResponseStatus.OK) {
                this.setVisible(false);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new DialogRecorder(url, username, password);
                    }
                });

            } else if (responseCode == ResponseStatus.UNAUTHORIZED) {
                showDialog("Неверный пароль или логин.");
                return;
            } else {
                showDialog("Неверный url.");
                return;
            }
        }
    }

}
