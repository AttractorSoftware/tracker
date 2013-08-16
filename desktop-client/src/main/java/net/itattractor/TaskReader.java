package net.itattractor;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class TaskReader {
    private ConnectionProvider connectionProvider;
    private HttpGet login;
    private HttpGet httpGet;
    private static final String loginUrlPart = "/login/";
    private static final String activeTicketsUrl = "/tracker/tickets/";
    private Document document;

    public TaskReader(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        document = null;
        login = new HttpGet(connectionProvider.getHost() + loginUrlPart);
        httpGet = new HttpGet(connectionProvider.getHost() + activeTicketsUrl + connectionProvider.getUsername());
        readTasks();
    }

    private void readTasks() {
        try {
            DefaultHttpClient httpClient = connectionProvider.getHttpClient();
            httpClient.execute(login);
            login.releaseConnection();
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream content = response.getEntity().getContent();
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                document = builder.parse(content);
            }
            /*
             ветвление else можно сделать при выполнении тикета об обработках исключений.
             после выполнения тикета, удалить комментарий!
            */
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Ticket[] getTickets() {
        NodeList nodeList = document.getElementsByTagName("ticket");
        Ticket tickets[] = new Ticket[nodeList.getLength()];
        for (int i = 0; i < tickets.length; i++) {
            Element element = (Element)nodeList.item(i);
            String id = element.getAttribute("id");
            String summary = element.getAttribute("summary");
            tickets[i] = new Ticket(Integer.parseInt(id), summary);
        }
        return tickets;
    }
}
