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
    private ConnectionProvider connectionProvider;

    public ScreenshotSender(ConnectionProvider connectionProvider){
        this.connectionProvider = connectionProvider;
    }

    public boolean sendScreenshot(File file) throws UnsupportedEncodingException {
        HttpGet httpGet = new HttpGet(connectionProvider.getHost() + "/report/");
        DefaultHttpClient httpClient = connectionProvider.getHttpClient();
        HttpResponse response;

        try{
            response = httpClient.execute(httpGet);
            EntityUtils.consume(response.getEntity());
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }

        List<Cookie> cookieList =  httpClient.getCookieStore().getCookies();

        HttpPost httpPost = new HttpPost(connectionProvider.getHost() + TRACKER_URL_PART);
        ContentBody body = new FileBody(file, "image/jpeg");

        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        entity.addPart("screenshot", body);
        entity.addPart("__FORM_TOKEN", new StringBody(getToken(cookieList)));
        entity.addPart("username", new StringBody(connectionProvider.getUsername()));
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

    public String getToken(List<Cookie> cookies)
    {
        for (Cookie cookie : cookies)
        {
            if (cookie.getName().equals("trac_form_token"))
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

        System.out.println(stringBuffer);
    }
}
