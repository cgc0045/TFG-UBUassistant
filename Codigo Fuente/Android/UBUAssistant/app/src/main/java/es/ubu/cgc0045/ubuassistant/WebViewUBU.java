package es.ubu.cgc0045.ubuassistant;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class WebViewUBU extends AppCompatActivity {
    String title;
    Global global;
    private String android_id;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        global = (Global) getApplicationContext();
        this.setTitle("Cargando...");
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        setContentView(R.layout.activity_web_view_ubu);

        if(savedInstanceState == null){
            url = getIntent().getDataString().replace("ubuassistant://","");
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
        myWebView.loadUrl(url);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        new logVisit().execute();

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();

        Intent i = new Intent(getApplicationContext(), Vote.class);
        startActivity(i);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private class logVisit extends AsyncTask<String, Void, String> {
        String retorno;
        ArrayList<Integer> responseCodes;
        HttpURLConnection conexion;
        JSONObject json;

        private void createJSON(){
            json = new JSONObject();
            JSONArray userID = new JSONArray();
            JSONArray palabras = new JSONArray();
            JSONArray respuesta = new JSONArray();

            DateFormat formatForId = new SimpleDateFormat("yyMMddHHmmssSSS");
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

        private void createLearnJSON(){
            json = new JSONObject();

            try {
                json.put("userID", global.getUserID());
                String p1 = "";
                for (String s: global.getWords()){
                    p1 += p1.equals("") ? s : (" " + s);
                }
                json.put("p1", p1);
                json.put("p2", url);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute(){
            responseCodes = new ArrayList<>(Arrays.asList(200,201,202,203));
        }

        @Override
        protected String doInBackground(String... strings) {
            createJSON();

            String JsonDATA = json.toString();
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

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
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
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
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                        }
                    }
                }
            }
            return retorno;
        }
    }
}
