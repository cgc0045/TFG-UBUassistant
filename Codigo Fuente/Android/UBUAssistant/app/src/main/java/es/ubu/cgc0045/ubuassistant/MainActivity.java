package es.ubu.cgc0045.ubuassistant;

import android.content.ActivityNotFoundException;
import android.content.Context;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static android.text.Html.escapeHtml;

public class MainActivity extends AppCompatActivity{

    private TextView resultText;
    private Socket socket;
    private ArrayList<String> respuestas;
    private ListView messages;
    private ArrayAdapter<String> adapter;
    private Button enviar;
    private HttpURLConnection conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = findViewById(R.id.editText);
        enviar = findViewById(R.id.enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;

                try {
                    respuesta = new connectServer().execute().get();
                }catch (InterruptedException e){
                    Log.e("Interrupted exception", e.getMessage());
                }catch (ExecutionException e){
                    Log.e("Execution exception", e.getMessage());
                }

                Log.w("Post", respuesta);

                Toast toast = Toast.makeText(context, respuesta, duration);
                toast.show();*/
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

        //new Thread(new ClientThread()).start();
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

        String response ="";

        for (char c: mes.toCharArray()){
            if (c == ' '){
                response += "%20";
            }else{
                response += c;
            }
        }

        return StringUtils.stripAccents(response);
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

    class getResponse extends AsyncTask<String, Void, String>{
        String retorno;

        @Override
        protected String doInBackground(String... strings) {

            try{
                String send = castToHTML(strings[0]);

                URL serverURL = new URL("http://192.168.1.17:8080/UBUassistant/service/" + send);
                Log.w("URL conexión: ", "http://192.168.1.17:8080/UBUassistant/service/" + send);

                conexion = (HttpURLConnection) serverURL.openConnection();

                Log.w("Res", String.valueOf(conexion.getResponseCode()));

                if(conexion.getResponseCode() == 200){
                    InputStream res = conexion.getInputStream();
                    retorno = IOUtils.toString(res);
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

                URL pruebaURL = new URL("http://192.168.1.17:8080/UBUassistant/service/");
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
