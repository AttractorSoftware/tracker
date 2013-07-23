package net.itattractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DialogRecorder implements ActionListener {
    private JFrame trackerFrame;
    private JFrame startFrame;
    private JTextArea descTextArea;
    private JLabel frequencyLabel;
    private JLabel currentTaskLabel;
    private JLabel chooseTaskLabel;
    private JButton goButton;
    private JButton beginButton;
    private JButton endButton;
    private JComboBox<Object> tasksComboBox;
    private JSpinner frequencySpinner;
    private SpinnerNumberModel spinnerNumberModel;
    private List<String> tasksInFile;
    private LogWriter logWriter;
    private String[] taskList;
    private int[] taskID;
    private String url;
    private String username;
    private String password;
    private String lastComment = "";

    private CommentSender commentSender;

    DialogRecorder(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        new Downloader(url, username, password);
        loadData();
        initializeElements();
    }

    public void loadData() {
        TaskReader taskReader = new TaskReader("query.csv");
        tasksInFile = taskReader.readTasks();
        taskList = new String[tasksInFile.size() - 1];
        taskID = new int[tasksInFile.size() - 1];
        for (int i = 1; i < tasksInFile.size(); i++)
        {
            taskID[i - 1] = Integer.parseInt(tasksInFile.get(i).substring(0, tasksInFile.get(i).indexOf(',')));
            taskList[i - 1] = tasksInFile.get(i).substring(tasksInFile.get(i).indexOf(',') + 1);
        }
        File file = new File("query.csv");
        file.delete();
    }

    public void initializeElements() {
        commentSender = new CommentSender();
        logWriter = new LogWriter();
        descTextArea = new JTextArea(10, 50);
        descTextArea.setLineWrap(true);
        descTextArea.setWrapStyleWord(true);
        frequencyLabel = new JLabel("Укажите период записи действий:");
        currentTaskLabel = new JLabel("Ваш текущий таск: ");
        chooseTaskLabel = new JLabel("Выберите таск:");
        tasksComboBox = new JComboBox<Object>(taskList);
        tasksComboBox.setPreferredSize(new Dimension(500, 25));
        goButton = new JButton("Go!");
        goButton.addActionListener(this);
        beginButton = new JButton("Begin");
        beginButton.addActionListener(this);
        endButton = new JButton("End");
        endButton.addActionListener(this);
        spinnerNumberModel = new SpinnerNumberModel(1, 1, 60, 1);
        frequencySpinner = new JSpinner(spinnerNumberModel);
        startFrame = new JFrame("Choose your task");
        startFrame.setLayout(new FlowLayout());
        startFrame.setSize(550, 200);
        startFrame.add(chooseTaskLabel);
        startFrame.add(tasksComboBox);
        startFrame.add(beginButton);
        startFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        trackerFrame = new JFrame("Tracker");
        trackerFrame.setLayout(new FlowLayout());
        trackerFrame.setSize(600, 400);
        trackerFrame.add(currentTaskLabel);
        trackerFrame.add(descTextArea);
        trackerFrame.add(frequencyLabel);
        trackerFrame.add(frequencySpinner);
        trackerFrame.add(goButton);
        trackerFrame.add(endButton);
        trackerFrame.addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosing(WindowEvent e) {
                        logWriter.saveDescription(descTextArea.getText());
                        logWriter.saveEnd();
                        descTextArea.setText("");
                        frequencySpinner.setValue(1);
                        startFrame.setVisible(true);
                    }

                }
        );
        startFrame.setVisible(true);
    }

    public void pause() {
        try {
            trackerFrame.setVisible(false);
            Thread.sleep(1000 * (Integer) frequencySpinner.getValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            trackerFrame.setVisible(true);
        }
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("Begin")) {
            logWriter.saveStart(taskID[tasksComboBox.getSelectedIndex()], taskList[tasksComboBox.getSelectedIndex()]);
            currentTaskLabel.setText("Ваш текущий таск: " + tasksComboBox.getSelectedItem());
            startFrame.setVisible(false);
            trackerFrame.setVisible(true);
        }
        if (event.getActionCommand().equals("Go!")) {

            if (!lastComment.equals(descTextArea.getText()))
            {
                logWriter.saveDescription(descTextArea.getText());
                try {
                    commentSender.paramSetter(url, username, password, taskID[tasksComboBox.getSelectedIndex()], descTextArea.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(taskID[tasksComboBox.getSelectedIndex()]);
            }
            pause();
            lastComment = descTextArea.getText();
        }
        if (event.getActionCommand().equals("End")) {
            logWriter.saveEnd();
            descTextArea.setText("");
            frequencySpinner.setValue(1);
            trackerFrame.setVisible(false);
            startFrame.setVisible(true);
        }
    }
}
