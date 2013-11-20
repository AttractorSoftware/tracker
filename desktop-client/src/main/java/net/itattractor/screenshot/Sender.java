package net.itattractor.screenshot;

import net.itattractor.Config;
import net.itattractor.ConnectionProvider;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Sender implements Command {

    private static final String TRACKER_URL_PART = "/tracker";
    private static final String LOGIN_URL_PART = "/login/";
    ConnectionProvider connectionProvider;

    @Override
    public void execute() {

        Screenshot screenshot = Queue.getLatest();

        if(
          screenshot.getMouseEventCount() > Integer.parseInt(Config.getValue("mouseEventCountForSendScreenshot")) &&
          screenshot.getKeyboardEventCount() > Integer.parseInt(Config.getValue("keyEventCountForSendScreenshot"))
         ) {
            try {
                connectionProvider = ConnectionProvider.getInstance();
                HttpGet httpGet = new HttpGet(connectionProvider.getHost() + LOGIN_URL_PART);
                DefaultHttpClient httpClient = connectionProvider.getHttpClient();
                HttpResponse response;

                response = httpClient.execute(httpGet);
                logger(response);
                EntityUtils.consume(response.getEntity());

                List<Cookie> cookieList =  httpClient.getCookieStore().getCookies();

                HttpPost httpPost = new HttpPost(connectionProvider.getHost() + TRACKER_URL_PART);
                ContentBody body = new FileBody(screenshot.getFileBody(), Config.getValue("sendingScreenshotExtension"));
                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                entity.addPart("screenshot", body);
                entity.addPart("__FORM_TOKEN", new StringBody(getToken(cookieList)));
                entity.addPart("username", new StringBody(connectionProvider.getUsername()));
                entity.addPart("action", new StringBody("addScreenshot"));
                entity.addPart("ticket_id", new StringBody(String.valueOf(screenshot.getTicketId())));
                entity.addPart("interval", new StringBody(Config.getValue("screenshotPeriod")));
                entity.addPart("time", new StringBody(screenshot.getTime()));
                entity.addPart("mouse_event_count", new StringBody(Integer.toString(screenshot.getMouseEventCount())));
                entity.addPart("keyboard_event_count", new StringBody(Integer.toString(screenshot.getKeyboardEventCount())));
                httpPost.setEntity(entity);
                httpClient.execute(httpPost);
                System.out.println(screenshot.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
}
