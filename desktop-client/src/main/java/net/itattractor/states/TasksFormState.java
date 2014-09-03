package net.itattractor.states;

import net.itattractor.HotKeyRegister;
import net.itattractor.forms.tasks.TasksForm;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TasksFormState implements State {
    private JFrame frame;
    private TasksForm form;
    private HotKeyRegister hotKeyRegister;

    public TasksFormState() {
        frame = new JFrame("tasks form");
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                hotKeyRegister.deregister();
                System.exit(0);
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 200);
    }

    @Override
    public void show() {
        form.fillComboBox();
        frame.add(form.getContentPanel());
        frame.setVisible(true);
    }

    public void setForm(TasksForm form) {
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

    public void setHotKeyRegister(HotKeyRegister hotKeyRegister) {
        this.hotKeyRegister = hotKeyRegister;
    }
}
