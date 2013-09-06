package net.itattractor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Date;

public class LogWriterTest {

    private File file;
    private LogWriter logWriter;
    private int expectedTicketId;
    private String expectedTicketSummary;

    @Before
    public void init() {
        expectedTicketId = 1;
        expectedTicketSummary = "test";
        logWriter = new LogWriter(new Ticket(expectedTicketId, expectedTicketSummary));
        file = new File(System.getProperty("user.home") + "/tracker.xml");

    }

    @Test
    public void testSaveStart() throws Exception {
        logWriter.saveStart();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document document = docBuilder.parse(file);
        Node tasks = document.getFirstChild();
        Node task = tasks.getLastChild();
        String actualFirstNodeNameOfTask = task.getFirstChild().getNodeName();
        String expectedFirstNodeNameOfTask = "DateStart";
        String actualTicketId = task.getAttributes().getNamedItem("id").getNodeValue();
        String actualTicketSummary = task.getAttributes().getNamedItem("name").getNodeValue();
        Assert.assertEquals(expectedFirstNodeNameOfTask, actualFirstNodeNameOfTask);
        Assert.assertEquals(expectedTicketId, Integer.parseInt(actualTicketId));
        Assert.assertEquals(expectedTicketSummary, actualTicketSummary);
    }

    @Test
    public void testSaveDescription() throws Exception {
        String expectedComment = new Date().toString();
        logWriter.saveDescription(expectedComment);
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document document = docBuilder.parse(file);
        Node tasks = document.getFirstChild();
        Node task = tasks.getLastChild();
        String actualLastNodeNameOfTask = task.getLastChild().getNodeName();
        String expectedLastNodeNameOfTask = "RecordQuery";
        String actualComment = task.getLastChild().getTextContent();
        Assert.assertEquals(expectedLastNodeNameOfTask, actualLastNodeNameOfTask);
        Assert.assertEquals(expectedComment, actualComment);
    }

    @Test
    public void testClose() throws Exception {
        logWriter.close();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document document = docBuilder.parse(file);
        Node tasks = document.getFirstChild();
        Node task = tasks.getLastChild();
        String actualLastNodeNameOfTask = task.getLastChild().getNodeName();
        String expectedLastNodeNameOfTask = "DateEnd";
        Assert.assertEquals(expectedLastNodeNameOfTask, actualLastNodeNameOfTask);
    }

    @Test
    public void testDoXML() throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("People");
        doc.appendChild(rootElement);
        Element man = doc.createElement("Man");
        rootElement.appendChild(man);
        String expectedFirstChildTextContext = "Beknazar";
        Element firstname = doc.createElement("firstname");
        firstname.appendChild(doc.createTextNode(expectedFirstChildTextContext));
        man.appendChild(firstname);
        Element lastname = doc.createElement("lastname");
        String expectedSecondChildTextContext = "Esenbek uulu";
        lastname.appendChild(doc.createTextNode(expectedSecondChildTextContext));
        man.appendChild(lastname);
        File file = new File(System.getProperty("user.home") + "/test.xml");
        logWriter.doXML(file, doc);
        DocumentBuilderFactory documentBuilderFactoryFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilderBuilder = documentBuilderFactoryFactory.newDocumentBuilder();
        Document document = documentBuilderBuilder.parse(file);
        Node people = document.getFirstChild();
        Node newMan = people.getLastChild();
        String actualFirstName = newMan.getFirstChild().getTextContent();
        String actualLastName = newMan.getLastChild().getTextContent();
        Assert.assertEquals(expectedFirstChildTextContext, actualFirstName);
        Assert.assertEquals(expectedSecondChildTextContext, actualLastName);
    }
}
