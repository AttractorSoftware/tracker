package net.itattractor.states;

import net.itattractor.forms.tasks.TasksForm;

import javax.swing.*;

public class TasksFormState implements State {
    private JFrame frame;
    private TasksForm form;

    public TasksFormState() {
        frame = new JFrame("tasks form");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

}
