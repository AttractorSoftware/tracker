package net.itattractor.forms.Error;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorForm {
    private JTextArea stackTraceMessage;
    private JPanel contentPanel;
    private JButton okButton;
    private JTextArea errorMessage;
    private JTextArea mailText;
    private JScrollPane scrollPane;


    public ErrorForm() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }


    public JTextArea getStackTraceMessage() {
        return stackTraceMessage;

    }


    public JPanel getContentPanel() {
        return contentPanel;
    }

    public JTextArea getErrorMessage() {
        return errorMessage;
    }
}
