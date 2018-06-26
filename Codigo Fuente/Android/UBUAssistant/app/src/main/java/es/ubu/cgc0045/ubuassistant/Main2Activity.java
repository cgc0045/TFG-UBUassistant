package es.ubu.cgc0045.ubuassistant;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class Main2Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageListAdapter adapter;
    private List<Message> messages;
    private ImageButton mic;
    private Button enviar;
    private EditText resultText;
    private JSONObject json;
    private int responseCode;
    private Global global;
    private ConstraintLayout cl;
    private Context context;

    private static List<String> URLS = new ArrayList<>(Arrays.asList("http://ubuassistant.westeurope.cloudapp.azure.com","http://charlie96.ddns.jazztel.es"));
    private static String URL = URLS.get(0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("UBUassistant");

        setContentView(R.layout.activity_main2);

        cl = findViewById(R.id.mainLayout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cl.getForeground().setAlpha(0);
        }

        context = this;

        messages = new ArrayList<>();
        global = (Global) getApplicationContext();
        global.setCl(cl);
        resultText = findViewById(R.id.edittext_chatbox);

        enviar = findViewById(R.id.button_chatbox_send);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.setWords(resultText.getText().toString());
                Log.w("Palabras buscadas", global.getWords().get(global.getWords().size()-1));

                messages.add(new Message(messages.size(),resultText.getText().toString()));

                adapter.notifyItemInserted(messages.size()-1);
                //adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(messages.size());
                Log.e("Enviado", resultText.getText().toString());
                //recyclerView.setSelection(adapter.getCount() - 1);


                try {
                    String solution = new getResponse().execute(messages.get(messages.size()-1).getMessage()).get();
                    messages.add(new Message(messages.size(),solution));
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(messages.size()-1);
                    //messages.setSelection(adapter.getCount() - 1);
                } catch (InterruptedException e) {
                    Log.e("InterruptedException", e.getMessage());
                } catch (ExecutionException e) {
                    Log.e("ExecutionException", e.getMessage());
                }

            }
        });

        try {
            String respuesta = new connectServer().execute().get();
            messages.add(new Message(1, respuesta));
        }catch (InterruptedException e) {
            Log.e("InterruptedException", e.getMessage());
        } catch (ExecutionException e) {
            Log.e("ExecutionException", e.getMessage());
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MessageListAdapter(this, messages);
        recyclerView.setAdapter(adapter);


    }

    public void onButtonClick(View v){
        if(v.getId() == R.id.imageButton2){

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

    private String castToHTML(String mes){

        char[] chars = mes.toCharArray();

        if (Character.isWhitespace(chars[chars.length-1])){
            chars = Arrays.copyOf(chars, chars.length-1);
        }

        StringBuilder response = new StringBuilder();

        for (char c: chars){
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
                respuesta += "<br><br><a href='ubuassistant://" + responses.getJSONArray(i).getString(1) + "'> " + responses.getJSONArray(i).getString(0) + "</a>";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return respuesta;
    }

    private class getResponse extends AsyncTask<String, Void, String>{
        String retorno;
        ArrayList<Integer> responseCodes;
        HttpURLConnection conexion;

        @Override
        protected void onPreExecute(){
            responseCodes = new ArrayList<>(Arrays.asList(200,201,202,203));
        }

        @Override
        protected String doInBackground(String... strings) {

            try{
                String send = castToHTML(strings[0]);

                URL serverURL = new URL(URL + ":8080/UBUassistant/service/" + send);
                Log.w("URL conexión: ", URL + ":8080/UBUassistant/service/" + send);

                conexion = (HttpURLConnection) serverURL.openConnection();

                responseCode = conexion.getResponseCode();
                global.setState(responseCode);

                Log.w("Res", String.valueOf(responseCode));

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

    private static boolean checkIfURLExists() {
        HttpURLConnection httpUrlConn;
        try {
            httpUrlConn = (HttpURLConnection) new URL(URL + ":8080")
                    .openConnection();

            // A HEAD request is just like a GET request, except that it asks
            // the server to return the response headers only, and not the
            // actual resource (i.e. no message body).
            // This is useful to check characteristics of a resource without
            // actually downloading it,thus saving bandwidth. Use HEAD when
            // you don't actually need a file's contents.
            httpUrlConn.setRequestMethod("HEAD");

            // Set timeouts in milliseconds
            httpUrlConn.setConnectTimeout(5000);
            httpUrlConn.setReadTimeout(5000);

            // Print HTTP status code/message for your information.
            System.out.println("Response Code: "
                    + httpUrlConn.getResponseCode());
            System.out.println("Response Message: "
                    + httpUrlConn.getResponseMessage());

            return (httpUrlConn.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class connectServer extends AsyncTask<String, Void, String> {

        String retorno;
        HttpURLConnection conexion;

        @Override
        protected  String doInBackground(String... params) {
            boolean valid_1 = checkIfURLExists();
            boolean valid_2 = false;

            Log.e("Accesible 1", String.valueOf(valid_1));

            if (!valid_1){
                URL = URLS.get(1);
                valid_2 = checkIfURLExists();
                Log.e("Accesible 2", String.valueOf(valid_2));
            }

            if (valid_1 || valid_2) {

                try {

                    URL pruebaURL = new URL(URL + ":8080/UBUassistant/service/");

                    conexion = (HttpURLConnection) pruebaURL.openConnection();

                    if (conexion.getResponseCode() == 200) {
                        InputStream res = conexion.getInputStream();
                        retorno = IOUtils.toString(res);
                    } else {
                        retorno = "No se ha conectado al servidor";
                    }

                    Log.w("Respuesta", retorno);

                } catch (IOException e) {
                    Log.e("Error url", e.toString());
                }

                conexion.disconnect();
                return retorno;
            }else{
                Intent intent = new Intent(context, NotConnectError.class);
                startActivity(intent);

                return "Error de conexión";
            }
        }
    }

}
