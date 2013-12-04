package net.itattractor.controller;

import net.itattractor.Config;
import net.itattractor.LogWriter;
import net.itattractor.Ticket;
import net.itattractor.WorkLogSender;
import net.itattractor.forms.record.RecordForm;
import net.itattractor.forms.record.RecordFormActionListener;
import net.itattractor.manager.WindowManager;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class RecordFormController implements RecordFormActionListener {
    private final RecordForm recordForm;
    private final WindowManager manager;
    private String lastComment = "";
    private WorkLogSender workLogSender;
    private LogWriter logWriter;
    private Timer timer;

    public RecordFormController(RecordForm recordForm, WindowManager manager) {
        this.recordForm = recordForm;
        this.manager = manager;
        this.recordForm.setActionListener(this);
    }

    @Override
    public void okPressed(Ticket currentTicket) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if(logWriter == null)
            logWriter = new LogWriter(currentTicket);
        JTextArea descriptionTextArea = recordForm.getDescriptionTextArea();
        if (hasDescriptionChanged(descriptionTextArea))
        {
            logWriter.saveDescription(descriptionTextArea.getText());
            workLogSender.sendWorkLog(currentTicket.getTicketId(), descriptionTextArea.getText());
        }
        pause();
        lastComment = descriptionTextArea.getText();
    }

    private boolean hasDescriptionChanged(JTextArea descriptionTextArea) {
        return !lastComment.equals(descriptionTextArea.getText());
    }

    private void pause() {
        int remindAgainIn = Integer.parseInt(Config.getValue("remindAgainInMinutes"));
        manager.hide();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                manager.show();
            }
        }, remindAgainIn * (Integer) recordForm.getPeriodTimeSpinner().getValue());
    }

    @Override
    public void switchPressed() {
        logWriter.close();
        manager.hide();
        manager.setState(manager.getTasksFormState());
        manager.show();
    }

    public void setWorkLogSender(WorkLogSender workLogSender) {
        this.workLogSender = workLogSender;
    }
}
