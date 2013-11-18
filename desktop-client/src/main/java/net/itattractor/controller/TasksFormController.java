package net.itattractor.controller;

import net.itattractor.*;
import net.itattractor.forms.tasks.TasksForm;
import net.itattractor.forms.tasks.TasksFormActionListener;
import net.itattractor.manager.WindowManager;
import net.itattractor.screenshot.*;
import net.itattractor.screenshot.Timer;

public class TasksFormController implements TasksFormActionListener {
    private final TasksForm tasksForm;
    private final WindowManager manager;
    private LogWriter logWriter;
    private Timer screenshotTimer;
    private TimeProvider timeProvider;

    public TasksFormController(TasksForm tasksForm, WindowManager manager) {
        this.tasksForm = tasksForm;
        this.manager = manager;
        this.tasksForm.setActionListener(this);
    }


    @Override
    public void startPressed(Ticket ticket) {
        logWriter = new LogWriter(ticket);
        logWriter.saveStart();
        EventCounter.ActivateEvent();
        screenshotTimer = new net.itattractor.screenshot.Timer();
        if (!Boolean.parseBoolean(Config.getValue("testMode"))) {
            timeProvider = new SystemTimeProvider();
            Config.setValue("screenshotPeriod", "60000");
        } else {
            timeProvider = new FakeTimeProvider();
            Config.setValue("screenshotPeriod", "10000");
        }
        screenshotTimer.setTimeProvider(timeProvider);

        Creator creator = new Creator(ticket);
        creator.setTimeProvider(timeProvider);
        Sender sender = new Sender();

        screenshotTimer.addCommand(1, creator);
        screenshotTimer.addCommand(2, sender);
        new Thread(screenshotTimer).start();

        manager.hide();
        manager.getRecordFormState().setTicket(ticket);
        manager.setState(manager.getRecordFormState());
        manager.show();
    }

    public Timer getScreenshotTimer() {
        return screenshotTimer;
    }

}
