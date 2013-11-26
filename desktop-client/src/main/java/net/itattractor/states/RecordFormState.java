package net.itattractor.states;

import net.itattractor.Ticket;
import net.itattractor.forms.record.RecordForm;
import net.itattractor.manager.WindowManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RecordFormState implements State {
    private JFrame frame;
    private RecordForm form;
    private Ticket ticket;
    private WindowManager manager;

    public RecordFormState() {
        frame = new JFrame("record");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 300);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                manager.hide();
                manager.setState(manager.getTasksFormState());
                manager.show();
            }
        });
    }

    @Override
    public void show() {
        form.setTicket(ticket);
        form.setTicketText();
        frame.add(form.getContentPanel());
        frame.setVisible(true);
    }

    @Override
    public void hide() {
        frame.setVisible(false);
    }

    public void setManager(WindowManager windowManager) {
        this.manager = windowManager;
    }

    public void setForm(RecordForm form) {
        this.form = form;
    }

    @Override
    public JFrame getFrame() {
        return frame;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
