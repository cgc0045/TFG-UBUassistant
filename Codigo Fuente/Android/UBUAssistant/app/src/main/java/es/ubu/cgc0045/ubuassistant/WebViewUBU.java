package es.ubu.cgc0045.ubuassistant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author Carlos González Calatrava
 */
public class WebViewUBU extends AppCompatActivity {
    String title;
    Global global;
    private String android_id;
    String url;

    /**
     * Method used to initialize the variables and the components
     * @param savedInstanceState Previous saved instance of the app
     */
    @SuppressLint({"HardwareIds", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        global = (Global) getApplicationContext();
        this.setTitle("Cargando...");
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        setContentView(R.layout.activity_web_view_ubu);

        if(savedInstanceState == null){
            url = Objects.requireNonNull(getIntent().getDataString()).replace("ubuassistant://","");
        }

        WebView myWebView = findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                WebViewUBU.this.setTitle(view.getTitle());
                Log.w("Global", global.getWords().get(0));
            }
        });
        myWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        myWebView.loadUrl(url);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        new logVisit().execute();

    }

    /**
     * Method used to do action when back button is pressed.
     */
    @Override
    public void onBackPressed(){
        super.onBackPressed();

        Intent i = new Intent(getApplicationContext(), Vote.class);
        startActivity(i);
    }

    /**
     * Method used to enable to return to the parent activity
     * @return true
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private class logVisit extends AsyncTask<String, Void, String> {
        String retorno;
        JSONObject json;

        /**
         * Method used to create the log JSON file.
         */
        private void createJSON(){
            json = new JSONObject();
            JSONArray userID = new JSONArray();
            JSONArray palabras = new JSONArray();
            JSONArray respuesta = new JSONArray();

            @SuppressLint("SimpleDateFormat") DateFormat formatForId = new SimpleDateFormat("yyMMddHHmmssSSS");
            String id = formatForId.format(new Date()) + "_" + android_id;
            userID.put(id);
            global.setUserID(id);

            for (int i = 0; i < global.getWords().size() && i < 7; i++){
                palabras.put(global.getWords().get(i));
            }

            respuesta.put(url);

            try {
                json.put("userID", userID);
                json.put("palabras", palabras);
                json.put("respuesta", respuesta);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**
         * Method used to create the learn JSON file
         */
        private void createLearnJSON(){
            json = new JSONObject();

            try {
                json.put("userID", global.getUserID());
                StringBuilder p1 = new StringBuilder();
                for (String s: global.getWords()){
                    p1.append(p1.toString().equals("") ? s : (" " + s));
                }
                json.put("p1", p1.toString());
                json.put("p2", url);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**
         * Method used to do procedures in an AsyncTask.
         * @param strings Array with parameters in String class
         * @return Response message obtained form the server
         */
        @Override
        protected String doInBackground(String... strings) {
            createJSON();

            String JsonDATA = json.toString();
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL(global.getUrl() + ":8080/UBUassistant/post/log");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(JsonDATA);
                writer.close();
                retorno = String.valueOf(urlConnection.getResponseCode());
                Log.e("Respuesta POST", String.valueOf(urlConnection.getResponseCode()));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            if (global.getState() == 201){
                createLearnJSON();

                Log.e("JSON", json.toString());

                try {
                    URL url = new URL(global.getUrl() + ":8080/UBUassistant/post/learn");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Accept", "application/json");
                    Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                    writer.write(json.toString());
                    writer.close();
                    retorno = String.valueOf(urlConnection.getResponseCode());
                    Log.e("Respuesta POST", String.valueOf(urlConnection.getResponseCode()));
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }
            return retorno;
        }
    }
}
