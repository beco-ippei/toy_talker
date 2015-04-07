package net.beco_ippei.toytalker;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ippei on 15/03/20.
 */
public class JsonTest
{
    public static void main(String[] args) {
        JsonTest test = new JsonTest();

        try {
            System.out.println("content:" + test._request());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public HttpResponse _request() throws IOException {
        InputStream inputStream = null;
        String result = null;
        System.out.println("------ before create http");
        try {
//            ConnectivityManager connMgr = (ConnectivityManager)
//                    getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//            if (networkInfo != null && networkInfo.isConnected()) {
//                // fetch data
//            } else {
//                // display error
//            }

//            DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
            DefaultHttpClient httpclient = new DefaultHttpClient();

//            String api = "http://api.openweathermap.org/data/2.5/weather";
//            String API_KEY = "29b3ce5fb327f1cadcb607025d21adb8";
//            String place = "Kyoto,jp";

//            String url = api + "?q=" + place + "&APPID=" + API_KEY;

            String url = "http://sample.beco-ippei.net/ok.html";

            System.out.println("url:" + url);
            HttpGet http = new HttpGet(url);
//            http.setHeader("Content-type", "application/json");

            return httpclient.execute(http);
//
//
//            HttpResponse response = httpclient.execute(http);
////            HttpEntity entity = response.getEntity();
//
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            response.getEntity().writeTo(outputStream);
//
//            System.out.println("------ before return from '_request()'");
//
//            return outputStream.toString();
//            parse(outputStream.toString());

//            inputStream = entity.getContent();
            // json is UTF-8 by default
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(inputStream, "UTF-8"), 8);
//            StringBuilder sb = new StringBuilder();
//
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if(inputStream != null) inputStream.close();
            } catch (Exception squish) {}
        }
        return null;
    }
}
