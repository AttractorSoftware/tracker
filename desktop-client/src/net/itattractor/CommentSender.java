package net.itattractor;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CommentSender {
    private static final String TICKET_URL_PART = "/ticket/";
    private ConnectionProvider connectionProvider;

    public CommentSender(ConnectionProvider provider) {
        this.connectionProvider = provider;
    }

    public boolean sendComment(int ticketId, String comment) {
        HttpGet httpGet = new HttpGet(connectionProvider.getHost() + TICKET_URL_PART + ticketId);
        DefaultHttpClient httpClient = connectionProvider.getHttpClient();
        HttpResponse response;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        String view_time;
        try {
            view_time = getViewTime(response);
            httpGet.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        List<Cookie> cookies = httpClient.getCookieStore().getCookies();

        HttpPost httpPost = new HttpPost(connectionProvider.getHost() + TICKET_URL_PART + ticketId);
        List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
        formParameters.add(new BasicNameValuePair("__FORM_TOKEN", getToken(cookies)));
        formParameters.add(new BasicNameValuePair("comment", comment));
        formParameters.add(new BasicNameValuePair("action", "accept"));
        formParameters.add(new BasicNameValuePair("submit", "Submit changes"));
        formParameters.add(new BasicNameValuePair("view_time", view_time));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(formParameters));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        try {
            httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static String getViewTime(HttpResponse httpResponse) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse. getEntity().getContent()));
        String line1;
        String subLine = "<input type=\"hidden\" name=\"view_time\" value=\"";
        while ((line1 = bufferedReader.readLine()) != null) {
            if (line1.indexOf(subLine) > 0)
            {
                return line1.substring(line1.indexOf(subLine) + subLine.length(), line1.indexOf(subLine) + subLine.length() + 16);
            }
        }
        return null;
    }

    public static String getToken(List<Cookie> cookies)
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

}
