package net.itattractor.forms;

import javax.swing.*;

public class RecordFormUsageExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("record form");
        RecordForm recordForm = new RecordForm();
        frame.add(recordForm.getContentPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}
