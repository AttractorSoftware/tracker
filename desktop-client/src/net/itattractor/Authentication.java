package net.itattractor;

import sun.misc.BASE64Encoder;

import java.net.HttpURLConnection;
import java.net.URL;

public class Authentication {

    public int auth(String url, String username, String password){
        int responseCode = 0;

        try {
            URL oUrl = new URL(url);
            HttpURLConnection oConnect = (HttpURLConnection) oUrl.openConnection();
            oConnect.setRequestMethod("GET");
            oConnect.setDoInput(true);
            byte[] byEncodedPassword = (username + ":" + password).getBytes();
            BASE64Encoder byEncoder = new BASE64Encoder();
            oConnect.setRequestProperty("Authorization", "Basic " + byEncoder.encode(byEncodedPassword));
            responseCode = oConnect.getResponseCode();
            oConnect.disconnect();
        } catch (Exception e) {
            return responseCode;
        } finally {
            return responseCode;
        }
    }
}
