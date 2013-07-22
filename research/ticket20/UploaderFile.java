/**
 * Created with IntelliJ IDEA.
 * User: esdp
 * Date: 7/22/13
 * Time: 3:37 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class UploaderFile {

    private String _executeRequest(HttpRequestBase oRequestBase){

        String sResponse = "" ;

        InputStream oResponseStream = null ;
        HttpClient oClient = new DefaultHttpClient () ;

        try{
            HttpResponse oResponse = oClient.execute(oRequestBase);
            if (oResponse != null){
                HttpEntity oResponseEntity = oResponse.getEntity();

                if (oResponseEntity != null){

                    oResponseStream = oResponseEntity.getContent();
                    if (oResponseStream != null){

                        BufferedReader oBufferedReader = new BufferedReader (new InputStreamReader (oResponseStream)) ;
                        String sResponseLine = oBufferedReader.readLine();

                        String sTempResponse = "";

                        while (sResponseLine != null){

                            sTempResponse = sTempResponse + sResponseLine + System.getProperty("line.separator");
                            sResponseLine = oBufferedReader.readLine();

                        }

                        oBufferedReader.close();

                        if (sTempResponse.length() > 0){
                            sResponse = sTempResponse;
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException oExc) {

            oExc.printStackTrace();

        } catch (ClientProtocolException oExc) {

            oExc.printStackTrace();

        } catch (IllegalStateException oExc) {

            oExc.printStackTrace();

        } catch (IOException oExc) {

            oExc.printStackTrace();

        }finally{

            if (oResponseStream != null){

                try {

                    oResponseStream.close() ;

                } catch (IOException oExc) {

                    oExc.printStackTrace();

                }
            }
        }

        oClient.getConnectionManager().shutdown() ;

        return sResponse ;
    }

    public String executeMultiPartRequest(String sUrlString, File oFile, String sFileName, String sFileDescription) {

        HttpPost oPostRequest = new HttpPost (sUrlString);

        try{

            MultipartEntity oMultiPartEntity = new MultipartEntity();

            oMultiPartEntity.addPart("fileDescription", new StringBody(sFileDescription != null ? sFileDescription : ""));
            oMultiPartEntity.addPart("fileName", new StringBody(sFileName != null ? sFileName : oFile.getName()));

            FileBody oFileBody = new FileBody(oFile, "application/octet-stream");
            oMultiPartEntity.addPart("attachment", oFileBody);

            oPostRequest.setEntity(oMultiPartEntity);

        }catch (UnsupportedEncodingException oExc){

            oExc.printStackTrace();

        }

        return this._executeRequest (oPostRequest) ;
    }

    public static void main(String args[]){

        UploaderFile oFileUpload = new UploaderFile();
        File oFile = new File ("testFile.test") ;

        String sResponse = oFileUpload.executeMultiPartRequest("http://tracker.esdp.it-attractor.net", oFile, oFile.getName(), "File Upload test testFile.test description") ;
        System.out.println("Response : " + sResponse);
    }
}
