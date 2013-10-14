package net.itattractor.features;

import cucumber.api.java.ru.И;
import cucumber.runtime.PendingException;
import net.itattractor.features.helper.Driver;
import org.uispec4j.Window;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Date;

public class ClientDefinitions {

    private Window loginWindow;
    private Window tasksWindow;
    private Window recordWindow;
    private String ticketId;
    private String ticketSummary;

    @И("^Выбираю первую в списке задачу$")
    public void Выбираю_первую_в_списке_задачу() throws Throwable {
        tasksWindow = Driver.getClientInstance().getTasksWindow();
        String selectedTicket = tasksWindow.getComboBox().getAwtComponent().getSelectedItem().toString();
        tasksWindow.getComboBox().select(selectedTicket);
        ticketId = selectedTicket.substring(1, selectedTicket.indexOf(':'));
        ticketSummary = selectedTicket.substring(selectedTicket.indexOf(':') + 2);
        tasksWindow.getButton("start").click();
    }

    @И("^Пишу \"([^\"]*)\" и начинаю отслеживание$")
    public void Пишу_и_начинаю_отслеживание(String comment) throws Throwable {
        recordWindow = Driver.getClientInstance().getRecordWindow();
        recordWindow.getInputTextBox("Label").setText(comment);
        recordWindow.getButton("ok").click();
    }

    @И("^Кликаю мышью \"([^\"]*)\" раз и нажимаю клавишу 1 \"([^\"]*)\" раз$")
    public void Кликаю_мышью_раз_и_нажимаю_клавишу_раз(Integer clickCount, Integer pressCount) throws Throwable {
        Robot robot = new Robot();

        for(int i = 0; i < clickCount; i++){
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        }

        for(int j =0; j < pressCount; j++) {

            robot.keyPress(KeyEvent.VK_1);
            robot.keyRelease(KeyEvent.VK_1);

        }
    }

    @И("^Жду \"([^\"]*)\" секунд$")
    public void Жду_секунд(Integer seconds) throws Throwable {
        Date start = new Date();
        Date end = new Date();

        while(end.getTime() - start.getTime() < seconds * 1000){
            end = new Date();
        }
    }
}
