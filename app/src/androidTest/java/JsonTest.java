import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

public class JsonTest {

    public static void main(String[] args) {
        executeGet();
        executePost();
    }

    private static void executeGet() {
        System.out.println("===== HTTP GET Start =====");

        Client client = ClientBuilder.newBuilder().build();
        try {
            Response response =
                    client
                            .target("http://localhost:8080/get?param=value")
                            .request()
                            .get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                System.out.println(response.readEntity(String.class));
            }

            response.close();
        } finally {
            client.close();
        }

        System.out.println("===== HTTP GET End =====");
    }

    private static void executePost() {
        System.out.println("===== HTTP POST Start =====");

        StringBuilder builder = new StringBuilder();
        builder.append("POST Body");
        builder.append("\r\n");
        builder.append("Http Server!!");
        builder.append("\r\n");

        Client client = ClientBuilder.newBuilder().build();
        try {
            Response response =
                    client
                            .target("http://localhost:8080/post")
                            .request()
                            .post(Entity.text(builder.toString()));

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                System.out.println(response.readEntity(String.class));
            }

            response.close();
        } finally {
            client.close();
        }

        System.out.println("===== HTTP POST End =====");
    }
}


//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
///**
// * Created by ippei on 15/03/20.
// */
//public class JsonTest {
//    public static void main(String[] args) {
//        JsonTest test = new JsonTest();
//
//        try {
//            System.out.println(test._request());
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//
//    public String _request() throws IOException {
//        InputStream inputStream = null;
//        String result = null;
//        try {
////            DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
//            DefaultHttpClient httpclient = new DefaultHttpClient();
//
////            String api = "http://api.openweathermap.org/data/2.5/weather";
////            String API_KEY = "29b3ce5fb327f1cadcb607025d21adb8";
////            String place = "Kyoto,jp";
//
////            String url = api + "?q=" + place + "&APPID=" + API_KEY;
//
//            String url = "http://sample.beco-ippei.net/ok.html";
//
//            HttpGet http = new HttpGet(url);
//            http.setHeader("Content-type", "application/json");
//            HttpResponse response = httpclient.execute(http);
//            HttpEntity entity = response.getEntity();
//
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            response.getEntity().writeTo(outputStream);
////            parse(outputStream.toString());
//
//            inputStream = entity.getContent();
//            // json is UTF-8 by default
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(inputStream, "UTF-8"), 8);
//            StringBuilder sb = new StringBuilder();
//
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//            result = sb.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try{
//                if(inputStream != null) inputStream.close();
//            } catch (Exception squish) {}
//        }
//        return result;
//    }
//}
