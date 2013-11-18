package net.itattractor.manager;

import net.itattractor.states.LoginFormState;
import net.itattractor.states.RecordFormState;
import net.itattractor.states.State;
import net.itattractor.states.TasksFormState;

import javax.swing.*;

public class WindowManager {
    private LoginFormState loginFormState;
    private TasksFormState tasksFormState;
    private RecordFormState recordFormState;
    private State state;

    public void show() {
        state.show();
    }

    public void hide() {
        state.hide();
    }

    public void init() {
        state = loginFormState;
    }

    public LoginFormState getLoginFormState() {
        return loginFormState;
    }

    public TasksFormState getTasksFormState() {
        return tasksFormState;
    }

    public RecordFormState getRecordFormState() {
        return recordFormState;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setLoginFormState(LoginFormState loginFormState) {
        this.loginFormState = loginFormState;
        loginFormState.setManager(this);
    }

    public void setTasksFormState(TasksFormState tasksFormState) {
        this.tasksFormState = tasksFormState;
        tasksFormState.setManager(this);
    }

    public void setRecordFormState(RecordFormState recordFormState) {
        this.recordFormState = recordFormState;
        recordFormState.setManager(this);
    }

    public JFrame getFrame() {
        return state.getFrame();
    }
}
