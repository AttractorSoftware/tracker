package net.itattractor;

import net.itattractor.controller.LoginFormController;
import net.itattractor.controller.RecordFormController;
import net.itattractor.controller.TasksFormController;
import net.itattractor.forms.login.LoginForm;
import net.itattractor.forms.record.RecordForm;
import net.itattractor.forms.tasks.TasksForm;
import net.itattractor.manager.WindowManager;
import net.itattractor.screenshot.Timer;
import net.itattractor.states.LoginFormState;
import net.itattractor.states.RecordFormState;
import net.itattractor.states.TasksFormState;

import javax.swing.*;

public class AppLauncher {
    private WindowManager manager;
    private Timer screenshotTimer;
    private TasksFormController tasksFormController;

    public static void main(String[] args) {
        AppLauncher launcher2 = new AppLauncher();
        launcher2.init();

    }

    public void init(){
        Config.init();
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

        manager.init();

        LoginFormController loginFormController = new LoginFormController(loginForm, manager);
        tasksFormController = new TasksFormController(tasksForm, manager);
        if (!Boolean.parseBoolean(Config.getValue("testMode"))) {
            Config.setValue("screenshotPeriod", "60000");
            tasksFormController.setTimeProvider(new SystemTimeProvider());
        } else {
            Config.setValue("screenshotPeriod", "10000");
            tasksFormController.setTimeProvider(new FakeTimeProvider());
        }

        RecordFormController recordFormController = new RecordFormController(recordForm, manager);
        recordFormController.setCommentSender(new CommentSender());
        loginFormController.start();
    }

    public JFrame getMainFrame() {
        return manager.getFrame();
    }

    public Timer getScreenshotTimer() {
        return tasksFormController.getScreenshotTimer();
    }
}
