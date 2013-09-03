package net.itattractor.forms.tasks;

import net.itattractor.ConnectionProvider;
import net.itattractor.TaskReader;
import net.itattractor.Ticket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TasksForm {
    private JPanel contentPanel;
    private JComboBox<Object> tasksComboBox;

    private JButton startButton;
    private JButton refreshButton;
    private TasksFormActionListener actionListener;
    private ConnectionProvider connectionProvider;

    public TasksForm() throws Exception {

        connectionProvider = ConnectionProvider.getInstance();

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(actionListener != null){
                    Ticket currentTicket = new Ticket(tasksComboBox.getSelectedItem().toString());
                    actionListener.startPressed(currentTicket);
                }
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tasksComboBox.removeAllItems();
                TaskReader taskReader = null;
                try {
                    taskReader = new TaskReader();
                } catch (Exception e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                Ticket[] tiket = taskReader.getTickets();
                for (Ticket aTiket : tiket) {
                    tasksComboBox.addItem(aTiket);
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

    private void createUIComponents() throws Exception {
        TaskReader taskReader = new TaskReader();
        tasksComboBox = new JComboBox<Object>(taskReader.getTickets());
    }
}
