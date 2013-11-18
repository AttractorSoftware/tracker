package net.itattractor.states;

import net.itattractor.forms.record.RecordForm;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RecordFormState implements State {
    private JFrame frame;
    private RecordForm form;

    @Override

    public void show() {
        frame = new JFrame("record");
        frame.add(form.getContentPanel());
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
            }
        });

    }

    public void setForm(RecordForm form) {
        this.form = form;
    }

    @Override
    public JFrame getFrame() {
        return frame;
    }

    @Override
    public void hide() {
        frame.setVisible(false);
    }
}
