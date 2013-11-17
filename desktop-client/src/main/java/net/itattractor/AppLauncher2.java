package net.itattractor;

import net.itattractor.controller.LoginFormController;
import net.itattractor.controller.RecordFormController;
import net.itattractor.controller.TasksFormController;
import net.itattractor.forms.login.LoginForm;
import net.itattractor.forms.record.RecordForm;
import net.itattractor.forms.tasks.TasksForm;
import net.itattractor.manager.WindowManager;
import net.itattractor.states.LoginFormState;
import net.itattractor.states.RecordFormState;
import net.itattractor.states.TasksFormState;

public class AppLauncher2 {
    public static void main(String[] args) {
        AppLauncher2 launcher2 = new AppLauncher2();
        launcher2.init();

    }

    private void init(){
        LoginForm  loginForm = new LoginForm();
        WindowManager manager = new WindowManager();

        LoginFormState loginWindowState = new LoginFormState();
        loginWindowState.setLoginForm(loginForm);
        manager.setLoginWindowState(loginWindowState);

        TasksFormState tasksFormState = new TasksFormState();
        TasksForm tasksForm = new TasksForm();
        tasksFormState.setTasksForm(tasksForm);
        manager.setTasksWindowState(tasksFormState);

        RecordFormState recordFormState = new RecordFormState();
        RecordForm recordForm = new RecordForm();
        recordFormState.setRecordForm(recordForm);

        manager.init();

        LoginFormController loginFormController = new LoginFormController(loginForm, manager);
        TasksFormController tasksFormController = new TasksFormController(tasksForm, manager);
        RecordFormController recordFormController = new RecordFormController(recordForm, manager);
        loginFormController.start();
    }

}
