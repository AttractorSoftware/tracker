package net.itattractor.forms.record;

import net.itattractor.Ticket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RecordForm {
    private Ticket ticket;

    private JPanel contentPanel;
    private JLabel currentTaskLabel;
    private JTextArea descriptionTextArea;
    private JSpinner periodTimeSpinner;
    private JButton okButton;
    private JButton switchTaskButton;
    private JTextPane currentTaskPane;
    private RecordFormActionListener actionListener;

    public RecordForm() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actionListener != null) {
                    actionListener.okPressed(ticket);
                }
            }
        });
        switchTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actionListener != null) {
                    actionListener.switchPressed();
                }
            }
        });

        currentTaskPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getModifiers() == KeyEvent.CTRL_MASK && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        actionListener.okPressed(ticket);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        descriptionTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getModifiers() == KeyEvent.CTRL_MASK && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        actionListener.okPressed(ticket);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        periodTimeSpinner.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getModifiers() == KeyEvent.CTRL_MASK && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        actionListener.okPressed(ticket);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        okButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getModifiers() == KeyEvent.CTRL_MASK && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        actionListener.okPressed(ticket);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        switchTaskButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getModifiers() == KeyEvent.CTRL_MASK && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        actionListener.okPressed(ticket);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public RecordForm(Ticket ticket) {
        //To change body of created methods use File | Settings | File Templates.
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
        periodTimeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 60, 1));
    }

    public void setTicketText() {
        currentTaskPane.setText(ticket.toString());
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
