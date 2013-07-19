/**
 * Created with IntelliJ IDEA.
 * User: esdp
 * Date: 7/19/13
 * Time: 3:09 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class Request {

    public void postMethod(String sUrl, DefaultHttpClient oHttpClient) throws IOException
    {
        HttpPost oHttpPost = new HttpPost(sUrl);
        List<NameValuePair> aFormParams = new ArrayList<NameValuePair>();
        aFormParams.add(new BasicNameValuePair("param1", "test1"));
        aFormParams.add(new BasicNameValuePair("param2", "test2"));
        oHttpPost.setEntity(new UrlEncodedFormEntity(aFormParams, HTTP.UTF_8));

        HttpResponse oResponse = oHttpClient.execute(oHttpPost);
        HttpEntity oEntity = oResponse.getEntity();
        System.out.println("Response from the post method: " + oResponse.getStatusLine());
    }

    public void getMethod(String sUrl, DefaultHttpClient oHttpClient) throws IOException
    {
        HttpGet oHttpGet = new HttpGet(sUrl);
        HttpResponse oResponse = oHttpClient.execute(oHttpGet);
        HttpEntity entity = oResponse.getEntity();
        System.out.println("Response from the get method " + oResponse.getStatusLine());
    }
    public DefaultHttpClient getHttpClient()
    {
        DefaultHttpClient oDefaultHttpClient = new DefaultHttpClient();
        return oDefaultHttpClient;
    }
    public static void main(String args[]) throws IOException
    {
        Request oRequest = new Request();
        DefaultHttpClient oDefaultHttpClient = oRequest.getHttpClient();
        oRequest.getMethod("http://tracker.esdp.it-attractor.net?param1=test1&param2=test2", oDefaultHttpClient);
        oRequest.postMethod("http://tracker.esdp.it-attractor.net", oDefaultHttpClient);

    }
}
