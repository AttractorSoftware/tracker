package net.itattractor.states;

import net.itattractor.forms.record.RecordForm;
import net.itattractor.forms.tasks.TasksForm;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RecordFormState implements State {
    private JFrame recordFrame;
    private RecordForm recordForm;

    @Override

    public void show() {
        recordFrame = new JFrame("record");
        recordFrame.add(recordForm.getContentPanel());
        recordFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        recordFrame.setSize(500, 300);
        recordFrame.setVisible(true);

        recordFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                recordFrame.setVisible(false);
            }
        });

    }

    public void setRecordForm(RecordForm recordForm) {
        this.recordForm = recordForm;
    }
}
