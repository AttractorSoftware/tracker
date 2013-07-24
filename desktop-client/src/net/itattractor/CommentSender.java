package net.itattractor;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommentSender {
    private static String view_time = "";

    public void paramSetter(String url, String username, String password, int id, String comment) throws IOException {

        String query;
        if (url.lastIndexOf('/') > 0)
        {
            query = "ticket/";
        }
        else
        {
            query = "/ticket/";
        }
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url+query+id);
        BasicHeader authHeader = new BasicHeader("Authorization", "Basic " + encodedPassword(username, password));
        httpGet.addHeader(authHeader);
        HttpResponse response = defaultHttpClient.execute(httpGet);
        List<Cookie> cookies = defaultHttpClient.getCookieStore().getCookies();
        String token = null;
        if(!cookies.isEmpty()){
            for (int i = 0; i < cookies.size(); i++) {
                token = cookies.get(i).toString().substring(43, 67);
            }
        }
        getViewTime(response);

        HttpPost httpPost = new HttpPost("http://tracker-trac.demo.esdp.it-attractor.net/ticket/" + id + "#trac-add-comment");
        httpPost.addHeader(authHeader);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("__FORM_TOKEN", token));
        formparams.add(new BasicNameValuePair("comment", comment));
        formparams.add(new BasicNameValuePair("action", "accept"));
        formparams.add(new BasicNameValuePair("submit", "Submit changes"));
        formparams.add(new BasicNameValuePair("view_time", view_time));
        httpPost.setEntity(new UrlEncodedFormEntity(formparams));
        defaultHttpClient.execute(httpPost);

    }

    private static String encodedPassword(String username, String password) {
        byte[] encodedPassword = (username + ":" + password).getBytes();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(encodedPassword);
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
