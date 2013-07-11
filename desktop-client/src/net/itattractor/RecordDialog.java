package net.itattractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private JComboBox<Object> tasksComboBox;
    private JSpinner frequencySpinner;
    private SpinnerNumberModel spinnerNumberModel;
    private List<String> tasks;
    private Save save;

    RecordDialog() {
        loadData();
        initializeElements();
    }

    public void loadData() {
        TaskReader taskReader = new TaskReader("tasks.txt");
        tasks = taskReader.readTasks();
    }

    public void initializeElements() {
        save = new Save();
        descTextArea = new JTextArea(10, 20);
        descTextArea.setLineWrap(true);
        descTextArea.setWrapStyleWord(true);
        frequencyLabel = new JLabel("Укажите период записи действий:");
        currentTaskLabel = new JLabel("Ваш текущий таск: ");
        chooseTaskLabel = new JLabel("Выберите таск:");
        tasksComboBox = new JComboBox<Object>(tasks.toArray());
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
        startFrame.add(chooseTaskLabel);
        startFrame.add(tasksComboBox);
        startFrame.add(beginButton);
        startFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        trackerFrame = new JFrame("Tracker");
        trackerFrame.setLayout(new FlowLayout());
        trackerFrame.setSize(300, 400);
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
                        save.saveDescription(descTextArea.getText());
                        save.saveEnd();
                        descTextArea.setText("");
                        frequencySpinner.setValue(1);
                        startFrame.setVisible(true);
                    }

                }
        );
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
            save.saveStart(tasksComboBox.getSelectedItem().toString());
            currentTaskLabel.setText("Ваш текущий таск: " + tasksComboBox.getSelectedItem());
            startFrame.setVisible(false);
            trackerFrame.setVisible(true);
        }
        if (ae.getActionCommand().equals("Go!")) {
            save.saveDescription(descTextArea.getText());
            descTextArea.setText("");
            Pause();
        }
        if (ae.getActionCommand().equals("End")) {
            save.saveEnd();
            descTextArea.setText("");
            frequencySpinner.setValue(1);
            trackerFrame.setVisible(false);
            startFrame.setVisible(true);
        }
    }
}
