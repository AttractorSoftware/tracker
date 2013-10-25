package net.itattractor.forms.tasks;

import net.itattractor.ConnectionProvider;
import net.itattractor.TaskReader;
import net.itattractor.Ticket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
                    e1.printStackTrace();
                }
                Ticket[] tiket = taskReader.getTickets();
                for (Ticket aTiket : tiket) {
                    tasksComboBox.addItem(aTiket);
                }
            }
        });

        tasksComboBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        Ticket currentTicket = new Ticket(tasksComboBox.getSelectedItem().toString());
                        actionListener.startPressed(currentTicket);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        refreshButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        Ticket currentTicket = new Ticket(tasksComboBox.getSelectedItem().toString());
                        actionListener.startPressed(currentTicket);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        startButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        Ticket currentTicket = new Ticket(tasksComboBox.getSelectedItem().toString());
                        actionListener.startPressed(currentTicket);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
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
