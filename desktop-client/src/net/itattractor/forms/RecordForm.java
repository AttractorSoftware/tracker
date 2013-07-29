package net.itattractor.forms;

import com.sun.org.apache.xerces.internal.xs.datatypes.XSDouble;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecordForm {
    private String ticketId;
    private String ticketSummary;
    private JPanel contentPanel;
    private JLabel currentTaskLabel;
    private JLabel taskViewerLabel;
    private JTextArea descriptionTextArea;
    private JSpinner periodTimeSpinner;
    private JButton okButton;
    private JButton switchTaskButton;
    private RecordFormActionListener actionListener;
    private JComboBox tasksComboBox;

    public RecordForm(String ticketId, String ticketSummary) {
        this.ticketId = ticketId;
        this.ticketSummary = ticketSummary;

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(actionListener != null) {
                    actionListener.okPressed();
                }
            }
        });
        switchTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(actionListener != null){
                    actionListener.switchPressed();
                }
            }
        });
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setActionListener(RecordFormActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public JTextArea getDescriptionTextArea() {
        return descriptionTextArea;
    }

    public JComboBox getTasksComboBox() {
        return tasksComboBox;
    }

    public JSpinner getPeriodTimeSpinner() {
        return periodTimeSpinner;
    }

    private void createUIComponents() {
        taskViewerLabel = new JLabel("#" + ticketId + ": " + ticketSummary);
    }
}
