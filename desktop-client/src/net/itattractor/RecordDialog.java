package net.itattractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.FileReader;
import java.util.*;
import java.util.List;

public class RecordDialog implements ActionListener {
    private JFrame trackerFrame;
    private JFrame startFrame;
    private JTextArea descTextArea;
    private JLabel frequencyLabel;
    private JLabel currentTaskLabel;
    private JLabel chooseTaskLabel;
    private JButton goButton;
    private JButton beginButton;
    private JButton endButton;
    private JComboBox tasksComboBox;
    private JComboBox tasksComboBox2;
    private JSpinner frequencySpinner;
    private SpinnerNumberModel spinnerNumberModel;
    private List<String> tasks;
    int hours[] = {0, 0, 0};
    long begin_time, end_time;
    String user = "Beknazar";
    int selectedTask;

    RecordDialog() {
        loadData();
        loadWorkedHours();
        initializeElements();
    }

    public void loadData() {
        TaskReader taskReader = new TaskReader("tasks.txt");
        tasks = taskReader.readTasks();
    }

    private void loadWorkedHours() {
        try {
            FileReader fileReader = new FileReader("hours.txt");
            int a;
            int i = 0;
            int j = 0;
            while ((a = fileReader.read()) != -1) {
                if ((char) a == '\n') {
                    i++;
                    j = 0;
                } else {
                    hours[i] = hours[i] * j + a - 48;
                    j = 10;
                }
            }
        } catch (IOException e) {
            System.out.println("Owibka! Net takogo faila.");
        }
    }

    public void initializeElements() {
        descTextArea = new JTextArea(10, 20);
        descTextArea.setLineWrap(true);
        descTextArea.setWrapStyleWord(true);
        frequencyLabel = new JLabel("Укажите период записи действий:");
        currentTaskLabel = new JLabel("Ваш текущий таск:");
        chooseTaskLabel = new JLabel("Выберите таск:");
        tasksComboBox = new JComboBox(tasks.toArray());
        tasksComboBox2 = new JComboBox(tasks.toArray());
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
        startFrame.setSize(200, 200);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.add(chooseTaskLabel);
        startFrame.add(tasksComboBox2);
        startFrame.add(beginButton);
        trackerFrame = new JFrame("Tracker");
        trackerFrame.setLayout(new FlowLayout());
        trackerFrame.setSize(300, 400);
        trackerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trackerFrame.add(currentTaskLabel);
        trackerFrame.add(tasksComboBox);
        trackerFrame.add(descTextArea);
        trackerFrame.add(frequencyLabel);
        trackerFrame.add(frequencySpinner);
        trackerFrame.add(goButton);
        trackerFrame.add(endButton);
        startFrame.setVisible(true);
    }

    public void Pause() {
        try {
            trackerFrame.setVisible(false);
            Thread.sleep(1000 * (Integer) frequencySpinner.getValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            trackerFrame.setVisible(true);
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Begin")) {
            begin_time = new Date().getTime();
            selectedTask = tasksComboBox2.getSelectedIndex();
            startFrame.setVisible(false);
            tasksComboBox.setSelectedIndex(selectedTask);
            tasksComboBox.setEnabled(false);
            trackerFrame.setVisible(true);
        }
        if (ae.getActionCommand().equals("Go!")) {
            new Save(tasksComboBox.getSelectedItem().toString(), user, descTextArea.getText());
            descTextArea.setText("");
            Pause();
        }
        if (ae.getActionCommand().equals("End")) {
            end_time = new Date().getTime();
            hours[tasksComboBox.getSelectedIndex()] += end_time - begin_time;
            try {
                int hour, min, sec;
                FileWriter fileWriter = new FileWriter("/home/esdp/Hours.log");
                for (int i = 0; i < 3; i++) {
                    hour = hours[i] / 3600000;
                    min = (hours[i] - hour * 3600000) / 60000;
                    sec = (hours[i] - hour * 3600000 - min * 60000) / 1000;
                    String for_hour = hour + "", for_min = min + "", for_sec = sec + "";
                    if (hour < 10) {
                        for_hour = "0" + for_hour;
                    }
                    if (min < 10) {
                        for_min = "0" + for_min;
                    }
                    if (sec < 10) {
                        for_sec = "0" + for_sec;
                    }

                    fileWriter.write(for_hour + ":" + for_min + ":" + for_sec + "\n");
                }
                fileWriter.close();
            } catch (IOException e) {

            }
            trackerFrame.setVisible(false);
            startFrame.setVisible(true);
        }
    }
}
