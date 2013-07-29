package net.itattractor.forms;

import net.itattractor.ConnectionProvider;
import net.itattractor.Downloader;
import net.itattractor.TaskReader;
import net.itattractor.Ticket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TasksForm {
    private JPanel contentPanel;
    private JComboBox tasksComboBox;
    private JButton startButton;
    private TasksFormActionListener actionListener;
    private String[] taskList;
    private ConnectionProvider connectionProvider;

    public TasksForm(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(actionListener != null){
                    Ticket currentTicket = new Ticket(tasksComboBox.getSelectedItem().toString());
                    actionListener.startPressed(currentTicket);
                }
            }
        });
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setActionListener(TasksFormActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void createUIComponents() {
        TaskReader taskReader = new TaskReader(new Downloader(connectionProvider));
        tasksComboBox = new JComboBox<Object>(taskReader.getTasksList());
    }


    public void setConnectionProvider(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }
}
