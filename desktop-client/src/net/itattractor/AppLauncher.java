package net.itattractor;

import net.itattractor.forms.*;

import javax.swing.*;

public class AppLauncher {
    private JFrame loginFrame;
    private LoginForm loginForm;
    private JFrame recordFrame;
    private JFrame tasksFrame;
    private RecordForm recordForm;

    public void start() {
        loginFrame = new JFrame("login form");

        loginForm = new LoginForm();
        loginForm.setActionListener(new LoginFormActionListenerImpl());
        loginFrame.add(loginForm.getContentPanel());
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginFrame.setSize(320, 240);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
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
                    loginFrame.setVisible(false);
                    tasksFrame = new JFrame("tasks form");
                    TasksForm tasksForm = new TasksForm(provider);
                    tasksForm.setActionListener(new TasksFormActionListenerImpl());

                    tasksFrame.add(tasksForm.getContentPanel());
                    tasksFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    tasksFrame.setSize(500, 200);
                    tasksFrame.setVisible(true);
                    new Thread(new ScreenShot(provider)).start();

                } else
                    showDialog("Неверный пароль или логин.");
            }
        }


        private void showDialog(String message) {
            JOptionPane.showMessageDialog(loginFrame, message, "login form message dialog", JOptionPane.WARNING_MESSAGE);
        }

        @Override
        public void cancelPressed() {
            System.exit(0);
        }
    }

    private class TasksFormActionListenerImpl implements TasksFormActionListener {
        @Override
        public void startPressed(String ticketId, String ticketSummary) {
            tasksFrame.setVisible(false);
            recordFrame = new JFrame("record form");
            recordForm = new RecordForm(ticketId, ticketSummary);
            recordForm.setActionListener(new RecordFormActionListenerImpl());

            recordFrame.add(recordForm.getContentPanel());
            recordFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            recordFrame.setSize(500, 300);
            recordFrame.setVisible(true);
        }
    }

    private class RecordFormActionListenerImpl implements RecordFormActionListener {
        private String lastComment;
        private LogWriter logWriter;
        private CommentSender commentSender;

        @Override
        public void okPressed() {
            JTextArea descriptionTextArea = recordForm.getDescriptionTextArea();
            if (!lastComment.equals(descriptionTextArea.getText()))
            {
                logWriter.saveDescription(descriptionTextArea.getText());
                commentSender.sendComment(taskID[recordForm.getTasksComboBox().getSelectedIndex()], descriptionTextArea.getText());
            }
            pause();
            lastComment = descriptionTextArea.getText();
        }

        private void pause() {
            try {
                recordFrame.setVisible(false);
                Thread.sleep(1000 * (Integer) recordForm.getPeriodTimeSpinner().getValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                recordFrame.setVisible(true);
            }
        }

        @Override
        public void switchPressed() {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
