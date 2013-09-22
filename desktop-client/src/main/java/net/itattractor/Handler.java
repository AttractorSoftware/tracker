package net.itattractor;

import net.itattractor.forms.Error.ErrorForm;

import javax.swing.*;
import java.util.Arrays;

public class Handler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {

        JFrame frame = new JFrame("Error form");
        ErrorForm errorForm1 = new ErrorForm();
        errorForm1.getErrorMessage().setText(e.toString());
        String stackTrace = Arrays.toString(e.getStackTrace());
        errorForm1.getStackTraceMessage().setText(stackTrace);
        frame.setContentPane(errorForm1.getContentPanel());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
