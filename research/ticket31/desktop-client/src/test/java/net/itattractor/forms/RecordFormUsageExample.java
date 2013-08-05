package net.itattractor.forms;

import net.itattractor.Ticket;
import net.itattractor.forms.record.RecordForm;

import javax.swing.*;

public class RecordFormUsageExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("record form");
        RecordForm recordForm = new RecordForm(new Ticket(4, "lkdslakdsla"));
        frame.add(recordForm.getContentPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}
