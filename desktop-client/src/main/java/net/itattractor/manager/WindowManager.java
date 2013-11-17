package net.itattractor.manager;

import net.itattractor.states.LoginFormState;
import net.itattractor.states.RecordFormState;
import net.itattractor.states.State;
import net.itattractor.states.TasksFormState;

public class WindowManager {
    private LoginFormState loginWindowState;
    private State state;
    private TasksFormState tasksWindowState;
    private RecordFormState recordWindowState;

    public void show() {
        state.show();
    }

    public State getLoginWindowState() {
        return loginWindowState;
    }

    public State getTaskWindowState() {
        return tasksWindowState;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setLoginWindowState(LoginFormState loginWindowState) {
        this.loginWindowState = loginWindowState;
    }

    public void init() {
        state = loginWindowState;
    }

    public void setTasksWindowState(TasksFormState tasksWindowState) {
        this.tasksWindowState = tasksWindowState;
    }

    public State getRecordWindowState() {
        return recordWindowState;
    }
}
