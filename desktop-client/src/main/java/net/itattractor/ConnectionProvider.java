package net.itattractor;

import net.itattractor.utils.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionProvider {
    private String username;
    private String password;
    private String host;
    private static final String loginUrlPart = "/login/";
    private static final String trackerUrlPart = "/tracker";

    public ConnectionProvider(String host, String username, String password) {
        this.username = username;
        this.password = password;
        this.host = StringUtils.trim(host, "/");
    }

    public DefaultHttpClient getHttpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        httpClient.setCredentialsProvider(credentialsProvider);
        return httpClient;
    }

    public String getUsername() {
        return username;
    }

    public String getHost() {
        return host;
    }
    private String getPassword(){
        return this.password;
    }

    public boolean isAuthenticated() {
        HttpGet httpGet = new HttpGet(this.getHost() + loginUrlPart);
        DefaultHttpClient httpClient = this.getHttpClient();

        try {
            httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        httpGet.releaseConnection();

        List<Cookie> cookies = httpClient.getCookieStore().getCookies();

        HttpPost httpPost = new HttpPost(this.getHost() + trackerUrlPart);
        List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
        formParameters.add(new BasicNameValuePair("__FORM_TOKEN", getToken(cookies)));
        formParameters.add(new BasicNameValuePair("action", "auth"));
        formParameters.add(new BasicNameValuePair("username", this.getUsername()));
        formParameters.add(new BasicNameValuePair("password", this.getPassword()));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(formParameters, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        try {
            StringBuffer bufferString = this.logger(httpClient.execute(httpPost));
            if ("Success".equals(bufferString.toString().trim())){
                return true;
            }
            else{
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getToken(List<Cookie> cookies)
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
    private StringBuffer logger(HttpResponse httpResponse) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        StringBuffer stringBuffer = new StringBuffer();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append("\n" + line);
        }
        return stringBuffer;

    }
}
