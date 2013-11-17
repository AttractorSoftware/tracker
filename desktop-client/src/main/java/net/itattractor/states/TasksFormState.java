package net.itattractor.states;

import net.itattractor.forms.tasks.TasksForm;

import javax.swing.*;

public class TasksFormState implements State {
    private TasksForm tasksForm;
    private JFrame tasksFrame;

    @Override
    public void show() {
        tasksFrame = new JFrame("tasks form");
        tasksFrame.add(tasksForm.getContentPanel());
        tasksFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tasksFrame.setSize(500, 200);
        tasksFrame.setVisible(true);
    }

    public void setTasksForm(TasksForm tasksForm) {
        this.tasksForm = tasksForm;
    }
}
