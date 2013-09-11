package net.itattractor;

import net.itattractor.forms.login.LoginForm;
import net.itattractor.forms.login.LoginFormActionListener;
import net.itattractor.forms.record.RecordForm;
import net.itattractor.forms.record.RecordFormActionListener;
import net.itattractor.forms.tasks.TasksForm;
import net.itattractor.forms.tasks.TasksFormActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

public class AppLauncher {
    private JFrame currentFrame;
    private JFrame loginFrame;

    private LoginForm loginForm;
    private JFrame recordFrame;
    private JFrame tasksFrame;
    private RecordForm recordForm;
    private ConnectionProvider provider;
    private LogWriter logWriter;
    private TasksForm tasksForm;
    private final TrayIcon trayIcon;
    private final SystemTray tray;
    private Timer timer;

    public AppLauncher() {
        timer = null;
        PopupMenu popup = new PopupMenu();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        trayIcon = new TrayIcon(image);
        tray = SystemTray.getSystemTray();

        MenuItem openItem = new MenuItem("Open");
        MenuItem exitItem = new MenuItem("Exit");
        popup.add(openItem);
        popup.add(exitItem);
        trayIcon.setPopupMenu(popup);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (timer != null) {
                    timer.cancel();
                }
                currentFrame.setVisible(true);
            }
        });

        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (timer != null) {
                    timer.cancel();
                }
                currentFrame.setVisible(true);
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }

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
        currentFrame = loginFrame;
        loginFrame.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                loginFrame.isVisible();
            }
        });
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
                    loginFrame.setVisible(false);
                    tasksFrame = new JFrame("tasks form");
                    tasksForm = new TasksForm();
                    tasksForm.setActionListener(new TasksFormActionListenerImpl());

                    tasksFrame.add(tasksForm.getContentPanel());
                    tasksFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    tasksFrame.setSize(500, 200);
                    tasksFrame.setVisible(true);
                    currentFrame = tasksFrame;
                    new Thread(new ScreenShot(provider)).start();
                } else
                    showDialog("Wrong username or password. Try again!");
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
            logWriter.saveStart();
            recordFrame.add(recordForm.getContentPanel());
            recordFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            recordFrame.setSize(500, 300);
            recordFrame.setVisible(true);

            currentFrame = recordFrame;

            recordFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    logWriter.close();
                    recordFrame.setVisible(false);
                    tasksFrame.setVisible(true);
                    currentFrame = tasksFrame;
                }
            });
        }
    }

    private class RecordFormActionListenerImpl implements RecordFormActionListener {
        private String lastComment = "";
        private CommentSender commentSender;

        private RecordFormActionListenerImpl() {
            this.commentSender = new CommentSender();
        }

        @Override
        public void okPressed(Ticket currentTicket) throws Exception {
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
            int remindAgainIn = Integer.parseInt(Config.getValue("remindAgainInSeconds"));
            recordFrame.setVisible(false);
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    recordFrame.setVisible(true);
                }
            }, remindAgainIn * (Integer) recordForm.getPeriodTimeSpinner().getValue());
        }

        @Override
        public void switchPressed() {
            logWriter.close();
            recordFrame.setVisible(false);
            tasksFrame.setVisible(true);
            currentFrame = tasksFrame;
        }
    }
}
