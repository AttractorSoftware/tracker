package net.itattractor;

import net.itattractor.controller.LoginFormController;
import net.itattractor.controller.RecordFormController;
import net.itattractor.controller.TasksFormController;
import net.itattractor.controller.TrayController;
import net.itattractor.forms.login.LoginForm;
import net.itattractor.forms.record.RecordForm;
import net.itattractor.forms.tasks.TasksForm;
import net.itattractor.forms.tray.Tray;
import net.itattractor.manager.WindowManager;
import net.itattractor.screenshot.TimerTaskImpl;
import net.itattractor.states.LoginFormState;
import net.itattractor.states.RecordFormState;
import net.itattractor.states.TasksFormState;

import javax.swing.*;

public class AppLauncher {
    private WindowManager manager;
    private TasksFormController tasksFormController;

    public void init(){
        manager = new WindowManager();

        LoginForm  loginForm = new LoginForm();
        LoginFormState loginFormState = new LoginFormState();
        loginFormState.setForm(loginForm);
        manager.setLoginFormState(loginFormState);

        TasksFormState tasksFormState = new TasksFormState();
        TasksForm tasksForm = new TasksForm();
        tasksFormState.setForm(tasksForm);
        manager.setTasksFormState(tasksFormState);

        RecordFormState recordFormState = new RecordFormState();
        RecordForm recordForm = new RecordForm();
        recordFormState.setForm(recordForm);
        manager.setRecordFormState(recordFormState);
        recordFormState.setManager(manager);

        manager.init();

        Tray tray = new Tray();
        new TrayController(manager, tray);

        LoginFormController loginFormController = new LoginFormController(loginForm, manager);
        tasksFormController = new TasksFormController(manager);
        EventCounter eventCounter = new EventCounter();
        tasksFormController.setEventCounter(eventCounter);
        tasksForm.setActionListener(tasksFormController);
        TimeProvider timeProvider = createTimeProvider();
        tasksFormController.setTimeProvider(timeProvider);

        RecordFormController recordFormController = new RecordFormController(recordForm, manager);
        WorkLogSender workLogSender = new WorkLogSender();
        workLogSender.setTimeProvider(timeProvider);
        recordFormController.setWorkLogSender(workLogSender);
        loginFormController.start();
    }

    private TimeProvider createTimeProvider() {
        return Boolean.parseBoolean(Config.getValue("testMode")) ?  new FakeTimeProvider() : new SystemTimeProvider();
    }

    public JFrame getMainFrame() {
        return manager.getFrame();
    }

    public TimerTaskImpl getTimerTask() {
        return tasksFormController.getTimerTask();
    }
}
