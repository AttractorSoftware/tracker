package net.itattractor;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConnectionProvider {

    private String username;
    private String password;
    private String host;
    private static final String loginUrlPart = "/login/";
    private static final String authenticateUrlPart = "/authenticate/";

    private static ConnectionProvider instance;

    private ConnectionProvider(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public static void createInstance(String host, String username, String password) {

            instance = new ConnectionProvider(host, username, password);

    }

    public static ConnectionProvider getInstance() throws Exception {

        if (instance == null) {
            throw new Exception("You must create instance!");
        }
        return instance;
    }

    public String getUsername() {
        return this.username;
    }

    public String getHost() {
        return this.host;
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

        try {

            HttpGet authenticate = new HttpGet(this.getHost() + authenticateUrlPart);

            HttpResponse response = httpClient.execute(authenticate);
            StringBuffer bufferString = this.logger(response);
            return "Success".equals(bufferString.toString().trim());

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DefaultHttpClient getHttpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        httpClient.setCredentialsProvider(credentialsProvider);
        return httpClient;
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
