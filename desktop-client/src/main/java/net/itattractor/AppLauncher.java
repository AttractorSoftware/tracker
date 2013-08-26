package net.itattractor;

import net.itattractor.forms.login.LoginForm;
import net.itattractor.forms.login.LoginFormActionListener;
import net.itattractor.forms.record.RecordForm;
import net.itattractor.forms.record.RecordFormActionListener;
import net.itattractor.forms.tasks.TasksForm;
import net.itattractor.forms.tasks.TasksFormActionListener;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AppLauncher {
    private JFrame loginFrame;

    private LoginForm loginForm;
    private JFrame recordFrame;
    private JFrame tasksFrame;
    private RecordForm recordForm;
    private ConnectionProvider provider;
    private LogWriter logWriter;
    private TasksForm tasksForm;


    public void start() {
        Config.init();

        loginFrame = new JFrame("login form");

        loginForm = new LoginForm();
        loginForm.setActionListener(new LoginFormActionListenerImpl());
        loginFrame.add(loginForm.getContentPanel());
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginFrame.setSize(320, 240);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    public JFrame getLoginFrame() {
        return loginFrame;
    }

    public JFrame getTasksFrame() {
        return tasksFrame;
    }

    public JFrame getRecordFrame() {
        return recordFrame;
    }

    public class LoginFormActionListenerImpl implements LoginFormActionListener {
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
                    tasksForm = new TasksForm(provider);
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
        public void startPressed(Ticket ticket) {
            tasksFrame.setVisible(false);
            recordFrame = new JFrame("record form");
            recordForm = new RecordForm(ticket);
            recordForm.setActionListener(new RecordFormActionListenerImpl());

            logWriter = new LogWriter(ticket);
            recordFrame.add(recordForm.getContentPanel());
            recordFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            recordFrame.setSize(500, 300);
            recordFrame.setVisible(true);
            recordFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    logWriter.close();
                    recordFrame.setVisible(false);
                    tasksFrame.setVisible(true);
                }
            });
        }
    }

    private class RecordFormActionListenerImpl implements RecordFormActionListener {
        private String lastComment = "";
        private CommentSender commentSender;

        private RecordFormActionListenerImpl() {
            this.commentSender = new CommentSender(provider);
        }

        @Override
        public void okPressed(Ticket currentTicket) {
            JTextArea descriptionTextArea = recordForm.getDescriptionTextArea();
            if (!lastComment.equals(descriptionTextArea.getText()))
            {
                logWriter.saveDescription(descriptionTextArea.getText());
                commentSender.sendComment(currentTicket.getTicketId(), descriptionTextArea.getText());
            }
            pause();
            lastComment = descriptionTextArea.getText();
        }

        private void pause() {
            int timer = Integer.parseInt(Config.getValue("remindAgainInMinutes"));
            try {
                recordFrame.setVisible(false);
                Thread.sleep(timer * (Integer) recordForm.getPeriodTimeSpinner().getValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                recordFrame.setVisible(true);
            }
        }

        @Override
        public void switchPressed() {
            logWriter.close();
            recordFrame.setVisible(false);
            tasksFrame.setVisible(true);
        }
    }
}
