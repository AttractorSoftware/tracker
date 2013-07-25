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
    private static String view_time = "";
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
        List<Cookie> cookies = httpClient.getCookieStore().getCookies();
        String token = null;
        if(!cookies.isEmpty()){
            for (Cookie cooky : cookies) {
                token = cooky.toString().substring(43, 67);
            }
        }
        try {
            getViewTime(response);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }

        HttpPost httpPost = new HttpPost(connectionProvider.getHost() + TICKET_URL_PART + ticketId);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("__FORM_TOKEN", token));
        formparams.add(new BasicNameValuePair("comment", comment));
        formparams.add(new BasicNameValuePair("action", "accept"));
        formparams.add(new BasicNameValuePair("submit", "Submit changes"));
        formparams.add(new BasicNameValuePair("view_time", view_time));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(formparams));
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

    private static void getViewTime(org.apache.http.HttpResponse httpResponse) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse. getEntity().getContent()));
        String line1;
        String subLine = "<input type=\"hidden\" name=\"view_time\" value=\"";
        while ((line1 = bufferedReader.readLine()) != null) {
            if (line1.indexOf(subLine) > 0)
            {
                view_time = line1.substring(line1.indexOf(subLine) + subLine.length(), line1.indexOf(subLine) + subLine.length() + 16);
            }
        }
    }

}
