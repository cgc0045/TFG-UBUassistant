package es.ubu.cgc0045.ubuassistant;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Vote extends Activity {

    private RatingBar ratingBar;
    private Global global;
    private ConstraintLayout cl;

    /**
     * Method used to finish the activity.
     */
    @Override
    public void finish(){
        fgTransparent();
        super.finish();
    }

    /**
     * Method used to initialize the variables and start the procedure.
     * @param savedInstanceState Previous state of the app.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.votewindow);
        ratingBar = findViewById(R.id.ratingBar);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        Button enviar = findViewById(R.id.enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rate = ((int) ratingBar.getRating());
                Log.e("Valoración", String.valueOf(rate));
                new valueResponse().execute();
                finish();
            }
        });

        Button rechazar = findViewById(R.id.cancelar);
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

        getWindow().setLayout((int) (width*0.8), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 225, getResources().getDisplayMetrics()));

    }

    /**
     * Method used to set the alpha channel of the foreground to 0 value.
     */
    private void fgTransparent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cl.getForeground().setAlpha(0);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class valueResponse extends AsyncTask<String, Void, String> {
        String retorno;
        JSONObject json;

        /**
         * Method used to create the JSON file.
         */
        private void createJSON(){
            json = new JSONObject();
            JSONArray userID = new JSONArray();
            JSONArray palabras = new JSONArray();
            JSONArray valoracion = new JSONArray();

            List<String> temp = global.getWords();

            while (temp.size() > 7){
                temp.remove(temp.size()-1);
            }

            userID.put(global.getUserID());
            for (String s: temp) {
                palabras.put(s);
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
            }
            return retorno;
        }
    }
}
