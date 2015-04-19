package net.beco_ippei.toytalker;

import android.app.LoaderManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class TopActivity extends ActionBarActivity
        implements TextToSpeech.OnInitListener, LoaderManager.LoaderCallbacks<JSONObject>
{
    // ダミーの識別子
    private static final int REQUEST_CODE = 0;

    private String message = null;

    // 音声合成用
    TextToSpeech tts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        tts = new TextToSpeech(this, this);
    }

    public void sendTextMessage(View view) {
        try {
            EditText editText = (EditText) findViewById(R.id.edit_message);
            this.message = editText.getText().toString();

            // 表示
            Toast.makeText(this, this.message, Toast.LENGTH_LONG).show();
            System.out.println("Text :: " + this.message);

            Bundle bundle = new Bundle();
            getLoaderManager().restartLoader(0, bundle, this);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Loader<JSONObject> onCreateLoader(int id, Bundle bundle) {
        try {
            if (id == 0) {
                // ValuesAsyncLoaderの生成
                Talker talker = new Talker(this);

                talker.setMessage(this.message);
                talker.setNickname("ippei");

                // Web APIの呼び出し
                talker.forceLoad();
                return talker;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject response) {
        try {
            // 早すぎると不自然なので少し待つ
            Thread.sleep(1000);

            String message = response.getString("msg");

            // 表示
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            // 音声合成して発音
            if(tts.isSpeaking()) {
                tts.stop();
            }
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {
        // 今回は何も処理しない
    }


    public void inputSpeech(View view) {
        try {
            // "android.speech.action.RECOGNIZE_SPEECH" を引数にインテント作成
            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            // 「お話しください」の画面で表示される文字列
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "音声認識中です");

            // 音声入力開始
            startActivityForResult(intent, REQUEST_CODE);

        } catch (ActivityNotFoundException e) {
            // 非対応の場合
            Toast.makeText(this, "音声入力に非対応です。", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // インテントの発行元を限定
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            // 音声入力の結果の最上位のみを取得
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            this.message = results.get(0);

            // 表示
            Toast.makeText(this, this.message, Toast.LENGTH_LONG).show();
            System.out.println("speech :: " + this.message);

            Bundle bundle = new Bundle();
            getLoaderManager().initLoader(0, bundle, this);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            // 音声合成の設定を行う

            float pitch = 1.0f; // 音の高低
            float rate = 1.0f; // 話すスピード
//            Locale locale = Locale.US; // 対象言語のロケール
            Locale locale = Locale.JAPAN;
            // ※ロケールの一覧表
            //   http://docs.oracle.com/javase/jp/1.5.0/api/java/util/Locale.html

            tts.setPitch(pitch);
            tts.setSpeechRate(rate);
            tts.setLanguage(locale);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if( tts != null ) {
            tts.shutdown();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}