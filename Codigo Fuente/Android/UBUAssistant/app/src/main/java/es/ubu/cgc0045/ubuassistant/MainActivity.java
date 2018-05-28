package es.ubu.cgc0045.ubuassistant;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity{

    private TextView resultText;
    private ArrayList<String> respuestas;
    private ListView messages;
    private ArrayAdapter<String> adapter;
    private Button enviar;
    private HttpURLConnection conexion;
    private JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = findViewById(R.id.editText);
        enviar = findViewById(R.id.enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                respuestas.add(resultText.getText().toString());
                adapter.notifyDataSetChanged();
                messages.setSelection(adapter.getCount() - 1);

                try {
                    String solution = new getResponse().execute(respuestas.get(respuestas.size()-1)).get();
                    respuestas.add(solution);
                    adapter.notifyDataSetChanged();
                    messages.setSelection(adapter.getCount() - 1);
                } catch (InterruptedException e) {
                    Log.e("InterruptedException", e.getMessage());
                } catch (ExecutionException e) {
                    Log.e("ExecutionException", e.getMessage());
                }

            }
        });
        respuestas = new ArrayList<>();
        try {
            respuestas.add(new connectServer().execute().get());
        }catch (InterruptedException e){
            Log.e("Interrupted exception", e.getMessage());
        }catch (ExecutionException e){
            Log.e("Execution exception", e.getMessage());
        }
        messages = findViewById(R.id.messages);
        adapter = new ArrayAdapter<>(this, R.layout.messages, respuestas);
        messages.setAdapter(adapter);
    }

    public void onButtonClick(View v){
        if(v.getId() == R.id.imageButton){

            promptSpeechInput();
        }
    }

    public void promptSpeechInput(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.say_something));

        try {
            startActivityForResult(i, 100);
        }catch(ActivityNotFoundException a){
            Toast.makeText(this, "Sorry! Your device doesn't support speech language!", Toast.LENGTH_LONG).show();
        }
    }

    private String castToHTML(String mes){

        //String res = escapeHtml(mes);

        StringBuilder response = new StringBuilder();

        for (char c: mes.toCharArray()){
            if (c == ' '){
                response.append("%20");
            }else{
                response.append(c);
            }
        }

        return StringUtils.stripAccents(response.toString());
    }

    private String parseResponse(String text, int code){
        String respuesta = "";

        try {
            json = new JSONObject(text);

            respuesta = json.getString("message");

            JSONArray responses = json.getJSONArray("responses");

            for (int i=0; i < responses.length(); i++){
                //respuesta += "\n\n" + responses.getString(i);
                respuesta += "\n\n" + responses.getJSONArray(i).getString(0) + "\n" + responses.getJSONArray(i).getString(1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return respuesta;
    }

    public void onActivityResult(int request_code, int result_code, Intent i){
        super.onActivityResult(request_code, result_code, i);

        switch (request_code){
            case 100:
                if(result_code == RESULT_OK && i != null){
                    ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    resultText.setText(result.get(0));
                    Log.e("Voice", resultText.toString());
                }
                break;
        }
    }

    private class getResponse extends AsyncTask<String, Void, String>{
        String retorno;
        ArrayList<Integer> responseCodes;

        @Override
        protected void onPreExecute(){
            responseCodes = new ArrayList<>(Arrays.asList(200,201,202,203));
        }

        @Override
        protected String doInBackground(String... strings) {

            try{
                String send = castToHTML(strings[0]);

                URL serverURL = new URL("http://10.190.39.88:8080/UBUassistant/service/" + send);
                Log.w("URL conexión: ", "http://10.190.39.88:8080/UBUassistant/service/" + send);

                conexion = (HttpURLConnection) serverURL.openConnection();

                Log.w("Res", String.valueOf(conexion.getResponseCode()));

                if(responseCodes.contains(conexion.getResponseCode())){
                    InputStream res = conexion.getInputStream();
                    retorno = IOUtils.toString(res);

                    retorno = parseResponse(retorno, conexion.getResponseCode());

                    Log.w("Respuesta", retorno);
                }else{
                    retorno = "No se ha conectado al servidor";
                }
            } catch (MalformedURLException e) {
                Log.e("Error url", e.toString());
            } catch (IOException e){
                Log.e("Error url", e.toString());
            }

            conexion.disconnect();
            return retorno;
        }
    }

    private class connectServer extends AsyncTask<String, Void, String>{

        String retorno;

        @Override
        protected  String doInBackground(String... params) {
            try {

                URL pruebaURL = new URL("http://10.190.39.88:8080/UBUassistant/service/");
                conexion = (HttpURLConnection) pruebaURL.openConnection();

                if(conexion.getResponseCode() == 200){
                    InputStream res = conexion.getInputStream();
                    retorno = IOUtils.toString(res);
                }else{
                    retorno = "No se ha conectado al servidor";
                }

                Log.w("Respuesta", retorno);

            } catch (MalformedURLException e) {
                Log.e("Error url", e.toString());
            } catch (IOException e){
                Log.e("Error url", e.toString());
            }

            conexion.disconnect();
            return retorno;
        }
    }

}