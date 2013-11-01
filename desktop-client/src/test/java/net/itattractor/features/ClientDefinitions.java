package net.itattractor.features;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Тогда;
import net.itattractor.features.helper.Driver;
import org.junit.Assert;
import org.uispec4j.Window;
import org.uispec4j.assertion.Assertion;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Date;

public class ClientDefinitions {

    private Window loginWindow;
    private Window tasksWindow;
    private Window recordWindow;
    private String ticketId;
    private String ticketSummary;


    @И("^выбираю первую в списке задачу$")
    public void выбираю_первую_в_списке_задачу() throws Throwable {
        tasksWindow = Driver.getClientInstance().getTasksWindow();
        String selectedTicket = tasksWindow.getComboBox().getAwtComponent().getSelectedItem().toString();
        tasksWindow.getComboBox().select(selectedTicket);
        ticketId = selectedTicket.substring(1, selectedTicket.indexOf(':'));
        CommonData.latestTicketId = ticketId;
        ticketSummary = selectedTicket.substring(selectedTicket.indexOf(':') + 2);
        tasksWindow.getButton("start").click();
    }

    @И("^пишу \"([^\"]*)\" и начинаю отслеживание$")
    public void пишу_и_начинаю_отслеживание(String comment) throws Throwable {
        CommonData.comment = comment;
        recordWindow = Driver.getClientInstance().getRecordWindow();
        recordWindow.getInputTextBox("descriptionTextArea").setText(comment);
        recordWindow.getButton("okButton").click();
    }

    @И("^кликаю мышью \"([^\"]*)\" раз и нажимаю клавишу 1 \"([^\"]*)\" раз$")
    public void кликаю_мышью_раз_и_нажимаю_клавишу_раз(Integer clickCount, Integer pressCount) throws Throwable {
        Robot robot = new Robot();

        for (int i = 0; i < clickCount; i++) {
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        }

        for (int j = 0; j < pressCount; j++) {

            robot.keyPress(KeyEvent.VK_1);
            robot.keyRelease(KeyEvent.VK_1);

        }
    }

    @И("^жду \"([^\"]*)\" секунд$")
    public void жду_секунд(Integer seconds) throws Throwable {
        Date start = new Date();
        Date end = new Date();

        while (end.getTime() - start.getTime() < seconds * 1000) {
            end = new Date();
        }
    }

    @Тогда("^в домашней директории в файле \"([^\"]*)\" в последней записи вижу \"([^\"]*)\"$")
    public void в_домашней_директории_в_файле_в_последней_записи_вижу(String fileName, String comment) throws Throwable {

        File file = new File(System.getProperty("user.home") + "/" + fileName);

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.parse(file);

            Node tasks = document.getFirstChild();
            Node lastChild = tasks.getLastChild();

            String ticketId = lastChild.getAttributes().getNamedItem("id").getNodeValue();
            String ticketSummary = lastChild.getAttributes().getNamedItem("name").getNodeValue();
            Node description = lastChild.getChildNodes().item(1);
            String textContent = description.getTextContent();

            Assert.assertEquals(this.ticketId, ticketId);
            Assert.assertEquals(this.ticketSummary, ticketSummary);
            Assert.assertEquals(comment, textContent);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @И("^нажимаю кнопку обновить список тикетов$")
    public void нажимаю_кнопку_обновить_список_тикетов() throws Throwable {

        Driver.getClientInstance().getTasksWindow().getButton("Refresh").click();

    }

    @Тогда("^не вижу в списке последний добавленный тикет$")
    public void не_вижу_в_списке_последний_добавленный_тикет() throws Throwable {

        Assertion contains = Driver.getClientInstance().getTasksWindow().getComboBox().contains(
                CommonData.latestTicketId + ": " + CommonData.latestTicketSummary);
        Assert.assertEquals(false, contains.isTrue());
    }

    @Тогда("^вижу в списке последний добавленный тикет$")
    public void вижу_в_списке_последний_добавленный_тикет() throws Throwable {
        Assertion contains = Driver.getClientInstance().getTasksWindow().getComboBox().contains(
                CommonData.latestTicketId + ": " + CommonData.latestTicketSummary);
        Assert.assertEquals(true, contains.isTrue());
    }

    @И("^работаю над последним созданым тикетом$")
    public void работаю_над_последним_созданым_тикетом() throws Throwable {
        выбираю_последний_созданный_тикет();
        пишу_и_начинаю_отслеживание("Автоматически созданный комментарий");
        кликаю_мышью_раз_и_нажимаю_клавишу_раз(13, 15);
        жду_секунд(11);
    }

    @И("^выбираю последний созданный тикет$")
    public void выбираю_последний_созданный_тикет() throws Throwable {

        tasksWindow = Driver.getClientInstance().getTasksWindow();
        tasksWindow.getComboBox().select(CommonData.latestTicketId + ": " + CommonData.latestTicketSummary);
        tasksWindow.getButton("start").click();

    }
}
