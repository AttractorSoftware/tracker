package net.itattractor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class LogWriter {
    private Ticket currentTicket;
    private Document doc;
    private File file;

    public LogWriter(Ticket ticket) {
        this.currentTicket = ticket;
        doc = null;
        file = new File(System.getProperty("user.home") + "/tracker.xml");
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            if (file.exists()) {
                try {
                    doc = docBuilder.parse(file);
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                doc = docBuilder.newDocument();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        if (doc != null && !file.exists()) {
            Element tasks = doc.createElement("Tasks");
            doc.appendChild(tasks);
            doXML(file, doc);
        }
    }

    public void saveStart() {
        if (doc != null) {
            Node tasks = doc.getFirstChild();
            Element task = doc.createElement("Task");
            Element dateStart = doc.createElement("DateStart");
            task.setAttribute("id", Integer.toString(currentTicket.getTicketId()));
            task.setAttribute("name", currentTicket.getTicketSummary());
            dateStart.setAttribute("value", new Date().toString());
            task.appendChild(dateStart);
            tasks.appendChild(task);
            doXML(file, doc);
        }
    }

    public void saveDescription(String comment) {
        if (doc != null) {
            Node tasks = doc.getFirstChild();
            Node task = tasks.getLastChild();
            Element recordQuery = doc.createElement("RecordQuery");
            Element dateTime = doc.createElement("DateTime");
            Element description = doc.createElement("Description");
            Text descriptionText = doc.createTextNode(comment);
            dateTime.setAttribute("value", new Date().toString());
            description.appendChild(descriptionText);
            recordQuery.appendChild(dateTime);
            recordQuery.appendChild(description);
            task.appendChild(recordQuery);
            doXML(file, doc);
        }
    }

    public void close() {
        if (doc != null) {
            Node tasks = doc.getFirstChild();
            Node task = tasks.getLastChild();
            Element dateEnd = doc.createElement("DateEnd");
            dateEnd.setAttribute("value", new Date().toString());
            task.appendChild(dateEnd);
            doXML(file, doc);
        }
    }

    public void doXML(File filename, Document document) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(filename);
            transformer.transform(source, result);
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
