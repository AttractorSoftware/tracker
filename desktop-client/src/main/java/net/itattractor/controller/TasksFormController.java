package net.itattractor.controller;

import net.itattractor.EventCounter;
import net.itattractor.LogWriter;
import net.itattractor.Ticket;
import net.itattractor.TimeProvider;
import net.itattractor.config.Config;
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
    private EventCounter eventCounter;

    private Config config;

    public TasksFormController(WindowManager manager) {
        this.manager = manager;
    }

    @Override
    public void startPressed(Ticket ticket) {
        logWriter = new LogWriter(ticket);
        logWriter.saveStart();
        eventCounter.init();
        timerTask = new TimerTaskImpl();
        timerTask.setTimeProvider(timeProvider);

        Creator creator = new Creator(ticket);
        creator.setTimeProvider(timeProvider);
        creator.setEventCounter(eventCounter);
        creator.setConfig(config);
        Sender sender = new Sender();
        sender.setConfig(config);

        timerTask.addCommand(1, creator);
        timerTask.addCommand(2, sender);

        Timer timer = new Timer();
        timer.schedule(timerTask, 0, Integer.parseInt(config.getValue("screenshotCheckCreatePeriod")));

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

    public void setEventCounter(EventCounter eventCounter) {
        this.eventCounter = eventCounter;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

}
