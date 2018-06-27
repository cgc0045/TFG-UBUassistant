package es.ubu.cgc0045.ubuassistant;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Vote extends Activity {

    private Button enviar;
    private RatingBar ratingBar;
    private Global global;
    private ConstraintLayout cl;
    private Button rechazar;


    @Override
    public void finish(){
        fgTransparent();
        super.finish();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.votewindow);
        ratingBar = findViewById(R.id.ratingBar);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        enviar = findViewById(R.id.enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rate = ((int) ratingBar.getRating());
                Log.e("Valoración", String.valueOf(rate));
                new valueResponse().execute();
                finish();
            }
        });

        rechazar = findViewById(R.id.cancelar);
        rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        global = (Global) getApplicationContext();

        cl = global.getCl();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cl.getForeground().setAlpha(128);
        }


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 225, getResources().getDisplayMetrics()));

    }

    private void fgTransparent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cl.getForeground().setAlpha(0);
        }
    }

    private class valueResponse extends AsyncTask<String, Void, String> {
        String retorno;
        ArrayList<Integer> responseCodes;
        HttpURLConnection conexion;
        JSONObject json;

        private void createJSON(){
            json = new JSONObject();
            JSONArray userID = new JSONArray();
            JSONArray palabras = new JSONArray();
            JSONArray valoracion = new JSONArray();

            userID.put(global.getUserID());
            for (String s: global.getWords()) {
                palabras.put(s);
                if (palabras.length() < 8) { break; }
            }
            int valor = ((int) ratingBar.getRating());
            valoracion.put(valor == 0 ? 1 : valor);

            try {
                json.put("userID", userID);
                json.put("palabras", palabras);
                json.put("valoracion", valoracion);
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
                URL url = new URL(global.getUrl() + ":8080/UBUassistant/post/vote");
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
            return retorno;
        }
    }
}
