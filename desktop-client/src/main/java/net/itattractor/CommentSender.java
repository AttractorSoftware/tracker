package net.itattractor;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CommentSender {
    private static final String LOGIN_URL_PART = "/login/";
    private static final String TRACKER_URL_PART = "/tracker";
    private ConnectionProvider connectionProvider;

    public CommentSender() {
    }

    public boolean sendComment(int ticketId, String comment) {

        connectionProvider = ConnectionProvider.getInstance();

        HttpGet httpGet = new HttpGet(connectionProvider.getHost() + LOGIN_URL_PART);
        DefaultHttpClient httpClient = connectionProvider.getHttpClient();

        try {
            httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        httpGet.releaseConnection();

        List<Cookie> cookies = httpClient.getCookieStore().getCookies();

        HttpPost httpPost = new HttpPost(connectionProvider.getHost() + TRACKER_URL_PART);
        List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
        formParameters.add(new BasicNameValuePair("__FORM_TOKEN", getToken(cookies)));
        formParameters.add(new BasicNameValuePair("comment", comment));
        formParameters.add(new BasicNameValuePair("author", this.connectionProvider.getUsername()));
        formParameters.add(new BasicNameValuePair("action", "addComment"));
        formParameters.add(new BasicNameValuePair("ticketId", Integer.toString(ticketId)));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(formParameters, "UTF-8"));
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

    private static String getToken(List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Config.getValue("tracFormToken"))) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
