package net.beco_ippei.toytalker;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ippei on 15/03/19.
 */
public class Talker extends AsyncTaskLoader<JSONObject>
{
    private String ctx;
    private URI uri;
    private String message;
    private String nickname;

    private static final String TALK_API = "http://talk.beco-ippei.net";

    Talker(Context context) throws URISyntaxException {
        super(context);
        this.ctx = "context";       //TODO: とりあえず

        this.uri = new URI(TALK_API);
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public void setNickname(String name) {
        this.nickname = name;
    }

    @Override
    public JSONObject loadInBackground() {
        try {
            System.out.println("TALK START::["+message+"]");

            JSONObject response = this.talk(message, nickname);

            // set response to UI
            System.out.println("RESPONSE::["+response.toString()+"]");
            return response;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject talk(String msg, String nickname) {
        try {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("msg", msg));
            params.add(new BasicNameValuePair("nickname", nickname));
            params.add(new BasicNameValuePair("context", this.ctx));

            //TODO: send version?

            HttpResponse response = this._request(params);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            response.getEntity().writeTo(outputStream);

            return new JSONObject(outputStream.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private HttpResponse _request(List<NameValuePair> params)
            throws IOException, URISyntaxException {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost();

        request.setURI(uri);

        request.setHeader("Content-type", "application/x-www-form-urlencoded");
        request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        return client.execute(request);
    }
}
