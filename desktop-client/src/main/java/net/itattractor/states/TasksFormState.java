package net.itattractor.states;

import net.itattractor.forms.tasks.TasksForm;
import net.itattractor.manager.WindowManager;

import javax.swing.*;

public class TasksFormState implements State {
    private JFrame frame;
    private TasksForm form;
    private WindowManager manager;

    @Override
    public void show() {
        frame = new JFrame("tasks form");
        form.fillComboBox();
        frame.add(form.getContentPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 200);
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

    @Override
    public void setManager(WindowManager windowManager) {
        this.manager = windowManager;
    }
}
