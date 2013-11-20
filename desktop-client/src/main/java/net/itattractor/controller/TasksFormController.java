package net.itattractor.controller;

import net.itattractor.*;
import net.itattractor.forms.tasks.TasksFormActionListener;
import net.itattractor.manager.WindowManager;
import net.itattractor.screenshot.Creator;
import net.itattractor.screenshot.Sender;
import net.itattractor.screenshot.TimerTaskImpl;

import java.util.Timer;

public class TasksFormController implements TasksFormActionListener {
    private final WindowManager manager;
    private LogWriter logWriter;
    private TimerTaskImpl timerTask;

    private TimeProvider timeProvider;

    public TasksFormController(WindowManager manager) {
        this.manager = manager;
    }

    @Override
    public void startPressed(Ticket ticket) {
        logWriter = new LogWriter(ticket);
        logWriter.saveStart();
        EventCounter.ActivateEvent();
        timerTask = new TimerTaskImpl();
        timerTask.setTimeProvider(timeProvider);

        Creator creator = new Creator(ticket);
        creator.setTimeProvider(timeProvider);
        Sender sender = new Sender();

        timerTask.addCommand(1, creator);
        timerTask.addCommand(2, sender);

        Timer timer = new Timer();
        timer.schedule(timerTask, 0, Integer.parseInt(Config.getValue("screenshotCheckCreatePeriod")));

        manager.hide();
        manager.getRecordFormState().setTicket(ticket);
        manager.setState(manager.getRecordFormState());
        manager.show();
    }


    public TimerTaskImpl getTimerTask() {
        return timerTask;
    }

    public void setTimeProvider(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

}
