package net.beco_ippei.toytalker;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ippei on 15/03/19.
 */
public class Talker extends AsyncTaskLoader<String>
{
    private String ctx;
    private URI uri;
    private String message;
    private String nickname;

    private static final String DOCOMO_API_KEY =
            "7878624d6953515a72505065555730763142662f694e77655047644c7074346e4264447732737a74374441";
    private static final String DOCOMO_API =
            "https://api.apigw.smt.docomo.ne.jp/dialogue/v1/dialogue?APIKEY="+DOCOMO_API_KEY;

    Talker(Context context) throws URISyntaxException {
        super(context);
        this.ctx = "context";       //TODO: とりあえず

        this.uri = new URI(DOCOMO_API);
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public void setNickname(String name) {
        this.nickname = name;
    }

    @Override
    public String loadInBackground() {
        try {
            String response = this.talk(message, nickname);

            // set response to UI
            System.out.println("RESPONSE::["+response+"]");
            return response;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private long getElapsedMinutes(Date from) {
        long diff = new Date().getTime() - from.getTime();
//        return diff / (60 * 1000), 10;
        return diff / (60 * 1000);
    }

    public String talk(String msg, String nickname) {
        try {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("utt", msg));
            params.add(new BasicNameValuePair("nickname", nickname));
            params.add(new BasicNameValuePair("context", this.ctx));

            JSONObject json = new JSONObject();
            json.put("utt", msg);
            json.put("nickname", nickname);
            json.put("context", this.ctx);

            HttpResponse response = this._request(json);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            response.getEntity().writeTo(outputStream);

            return outputStream.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private HttpResponse _request(JSONObject json)
            throws IOException, URISyntaxException {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost();

        StringEntity se = new StringEntity(json.toString(), "UTF-8");
        System.out.println("json:" + json.toString());
//        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        request.setHeader(HTTP.CONTENT_TYPE, "application/json");
        request.setEntity(se);

        request.setURI(uri);

//        request.setHeader("Content-type", "application/x-www-form-urlencoded");

        return client.execute(request);
    }

    private HttpResponse _request(List<NameValuePair> params)
            throws IOException, URISyntaxException {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost();
//        HttpGet request = new HttpGet();

//        params.add(new BasicNameValuePair("APIKEY", DOCOMO_API_KEY));

//        String paramString = URLEncodedUtils.format(params, "utf-8");

//        URI uri = new URI(DOCOMO_API + "?" + paramString);
//        System.out.println(uri.toString());

        request.setURI(uri);

//        request.setURI(this.uri);
        request.setHeader("Content-type", "application/x-www-form-urlencoded");

        // POST データの設定
        request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//        request.setEntity(new UrlEntity(params, HTTP.UTF_8));

        return client.execute(request);
    }
}
