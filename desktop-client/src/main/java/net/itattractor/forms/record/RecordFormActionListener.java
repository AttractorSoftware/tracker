package net.itattractor.forms.record;

import net.itattractor.Ticket;

public interface RecordFormActionListener {
    void okPressed(Ticket ticket) throws Exception;
    void switchPressed();
}
