package net.itattractor;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.List;

public class ScreenshotSender {

    private static final String TRACKER_URL_PART = "/tracker";
    private static final String LOGIN_URL_PART = "/login/";
    private ConnectionProvider connectionProvider;
    private Ticket currentTicket;

    private TimeProvider timeProvider;

    public boolean sendScreenshot(File file) throws Exception {

        connectionProvider = ConnectionProvider.getInstance();
        HttpGet httpGet = new HttpGet(connectionProvider.getHost() + LOGIN_URL_PART);
        DefaultHttpClient httpClient = connectionProvider.getHttpClient();
        HttpResponse response;

        try{
            response = httpClient.execute(httpGet);
            logger(response);
            EntityUtils.consume(response.getEntity());
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }

        List<Cookie> cookieList =  httpClient.getCookieStore().getCookies();

        HttpPost httpPost = new HttpPost(connectionProvider.getHost() + TRACKER_URL_PART);
        ContentBody body = new FileBody(file, Config.getValue("sendingScreenshotExtension"));
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        entity.addPart("screenshot", body);
        entity.addPart("__FORM_TOKEN", new StringBody(getToken(cookieList)));
        entity.addPart("username", new StringBody(connectionProvider.getUsername()));
        entity.addPart("action", new StringBody("addScreenshot"));
        entity.addPart("ticket_id", new StringBody(String.valueOf(currentTicket.getTicketId())));
        entity.addPart("interval", new StringBody(Config.getValue("screenshotPeriod")));
        entity.addPart("time", new StringBody(Long.toString(timeProvider.getTimeInMilliseconds())));
        entity.addPart("mouse_event_count", new StringBody(Integer.toString(EventCounter.mouseCounter)));
        entity.addPart("keyboard_event_count", new StringBody(Integer.toString(EventCounter.keyCounter)));
        httpPost.setEntity(entity);
        try{
            httpClient.execute(httpPost);
        }
        catch(Exception e){
            e.printStackTrace();
            return  false;
        }

        return true;
    }

    private String getToken(List<Cookie> cookies)
    {
        for (Cookie cookie : cookies)
        {
            if (cookie.getName().equals(Config.getValue("tracFormToken")))
            {
                return cookie.getValue();
            }
        }
        return null;
    }

    private static void logger(HttpResponse httpResponse) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        StringBuffer stringBuffer = new StringBuffer();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append("\n" + line);
        }

    }

    public void setTicket(Ticket ticket) {
        this.currentTicket = ticket;
    }

    public void setTimeProvider(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }
}
