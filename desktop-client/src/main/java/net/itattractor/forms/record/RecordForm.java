package net.itattractor.forms.record;

import net.itattractor.Ticket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecordForm {
    private Ticket currentTicket;
    private JPanel contentPanel;
    private JLabel currentTaskLabel;
    private JTextArea descriptionTextArea;
    private JSpinner periodTimeSpinner;
    private JButton okButton;
    private JButton switchTaskButton;
    private JTextPane currentTaskPane;
    private RecordFormActionListener actionListener;

    public RecordForm(final Ticket currentTicket) {
        this.currentTicket = currentTicket;

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(actionListener != null) {
                    try {
                        actionListener.okPressed(currentTicket);
                    } catch (Exception e1) {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
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

    public JSpinner getPeriodTimeSpinner() {
        return periodTimeSpinner;
    }

    private void createUIComponents() {
        currentTaskPane = new JTextPane();
        currentTaskPane.setText(currentTicket.toString());
        periodTimeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 60, 1));
    }
}
