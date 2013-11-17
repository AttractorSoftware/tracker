package net.itattractor.controller;

import net.itattractor.Ticket;
import net.itattractor.forms.record.RecordForm;
import net.itattractor.forms.record.RecordFormActionListener;
import net.itattractor.manager.WindowManager;

public class RecordFormController implements RecordFormActionListener {
    private final RecordForm recordForm;
    private final WindowManager manager;

    public RecordFormController(RecordForm recordForm, WindowManager manager) {
        this.recordForm = recordForm;
        this.manager = manager;
        this.recordForm.setActionListener(this);
    }

    @Override
    public void okPressed(Ticket ticket) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void switchPressed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
