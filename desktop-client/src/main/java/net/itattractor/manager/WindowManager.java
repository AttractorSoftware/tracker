package net.itattractor.manager;

import net.itattractor.states.LoginFormState;
import net.itattractor.states.RecordFormState;
import net.itattractor.states.State;
import net.itattractor.states.TasksFormState;

import javax.swing.*;

public class WindowManager {
    private LoginFormState loginWindowState;
    private State state;
    private TasksFormState tasksWindowState;
    private RecordFormState recordWindowState;

    public void show() {
        state.show();
    }

    public void init() {
        state = loginWindowState;
    }

    public State getLoginWindowState() {
        return loginWindowState;
    }

    public State getTasksWindowState() {
        return tasksWindowState;
    }

    public State getRecordWindowState() {
        return recordWindowState;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setLoginWindowState(LoginFormState loginWindowState) {
        this.loginWindowState = loginWindowState;
    }

    public void setTasksWindowState(TasksFormState tasksWindowState) {
        this.tasksWindowState = tasksWindowState;
    }

    public JFrame getFrame() {
        return state.getFrame();
    }

    public void hide() {
        state.hide();
    }
}
