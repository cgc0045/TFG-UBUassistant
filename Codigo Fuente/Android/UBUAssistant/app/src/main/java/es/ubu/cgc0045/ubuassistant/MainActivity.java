package es.ubu.cgc0045.ubuassistant;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author Carlos González Calatrava
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageListAdapter adapter;
    private List<Message> messages;
    private EditText resultText;
    private Global global;
    private Context context;
    private Set<String> busqueda;

    private static List<String> URLS = new ArrayList<>(Arrays.asList("http://ubuassistant.westeurope.cloudapp.azure.com","http://charlie96.ddns.jazztel.es"));
    private static String URL = URLS.get(0);

    /**
     * Method used to initialize the variables and the components
     * @param savedInstanceState Previous saved instance of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("UBUassistant");

        setContentView(R.layout.activity_main);

        ConstraintLayout cl = findViewById(R.id.mainLayout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cl.getForeground().setAlpha(0);
        }

        context = this;

        busqueda = new HashSet<>();
        messages = new ArrayList<>();
        global = (Global) getApplicationContext();
        global.setCl(cl);
        resultText = findViewById(R.id.edittext_chatbox);

        Button enviar = findViewById(R.id.button_chatbox_send);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardaBusqueda();

                enviarPregunta();

                resultText.setText("");
            }
        });

        Button nuevo = findViewById(R.id.button_new);
        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevaBusqueda();

                enviarPregunta();

                resultText.setText("");
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

    /**
     * Method used to save in a list the words used to do the search.
     */
    private void guardaBusqueda(){
        if (busqueda.size() == 0){
            busqueda.addAll(new ArrayList<>(Arrays.asList(resultText.getText().toString().split(" "))));

        }else{
            List<String> temp = Arrays.asList(resultText.getText().toString().split(" "));
            Log.e("Array temp", temp.toString());
            busqueda.addAll(temp);

        }

        for (Iterator<String> iterator = busqueda.iterator(); iterator.hasNext();) {
            String s =  iterator.next();
            if (s.length() <= 3) {
                iterator.remove();
            }
        }
    }

    /**
     * Method used to clear the list that store the words used to do the search.
     */
    private void nuevaBusqueda(){
        busqueda.clear();
        busqueda.addAll(new ArrayList<>(Arrays.asList(resultText.getText().toString().split(" "))));

        for (Iterator<String> iterator = busqueda.iterator(); iterator.hasNext();) {
            String s =  iterator.next();
            if (s.length() <= 3) {
                iterator.remove();
            }
        }
    }

    /**
     * Method used to send the words to the server to do a search and receive a response from it.
     */
    private void enviarPregunta(){
        Log.e("Array terminos", busqueda.toString());
        global.setWords(new ArrayList<>(busqueda));
        Log.w("Palabras buscadas", busqueda.toString());

                messages.add(new Message(messages.size(),busquedaToString()));

        adapter.notifyItemInserted(messages.size()-1);
        recyclerView.scrollToPosition(messages.size());
        Log.e("Enviado", resultText.getText().toString());


        try {
            String solution = new getResponse().execute(messages.get(messages.size()-1).getMessage()).get();
            messages.add(new Message(messages.size(),solution));
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(messages.size()-1);
        } catch (InterruptedException e) {
            Log.e("InterruptedException", e.getMessage());
        } catch (ExecutionException e) {
            Log.e("ExecutionException", e.getMessage());
        }
    }

    /**
     * Method used to cast the list with the words into an unique string. The words are splitted by one white space.
     * @return string with all  words from the list
     */
    private String busquedaToString(){
        StringBuilder busca = new StringBuilder();
        for (String s: busqueda){
            busca.append(s).append(" ");
        }

        return busca.toString();
    }

    /**
     * Method used to start the cast from voice to text.
     * @param v Actual view of the application
     */
    public void onButtonClick(View v){
        if(v.getId() == R.id.imageButton2){

            promptSpeechInput();
        }
    }

    /**
     * Method used to cast the voice into text.
     */
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

    /**
     * Method used to parse the voice recognition to legible string.
     *
     * @param request_code Code send it by the voice recognition
     * @param result_code Code generated after the parsing
     * @param i Intent used to get the voice recognition activity
     */
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

    /**
     * Method used to convert the sending string to html format for the server understanding the request.
     * @param mes String used to convert
     * @return String converted in HTML format
     */
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

    /**
     * Method used to parse the json response and create the hyperlinks of response.
     * @param text JSON file
     * @return String with legible response
     */
    private String parseResponse(String text){
        StringBuilder respuesta = new StringBuilder();

        try {
            JSONObject json = new JSONObject(text);

            respuesta = new StringBuilder(json.getString("message"));

            JSONArray responses = json.getJSONArray("responses");

            for (int i=0; i < responses.length(); i++){
                respuesta.append("<br><br><a href='ubuassistant://").append(responses.getJSONArray(i).getString(1)).append("'> ").append(responses.getJSONArray(i).getString(0)).append("</a>");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return respuesta.toString();
    }

    @SuppressLint("StaticFieldLeak")
    private class getResponse extends AsyncTask<String, Void, String>{
        String retorno;
        ArrayList<Integer> responseCodes;
        HttpURLConnection conexion;

        /**
         * Method used to do tasks before to start the AsyncTask
         */
        @Override
        protected void onPreExecute(){
            responseCodes = new ArrayList<>(Arrays.asList(200,201,202,203));
        }

        /**
         * Method used to do procedures in an AsyncTask.
         * @param strings Array with parameters in String class
         * @return Response message obtained form the server
         */
        @Override
        protected String doInBackground(String... strings) {

            try{
                String send = castToHTML(strings[0]);

                URL serverURL = new URL(URL + ":8080/UBUassistant/service/" + send);
                Log.w("URL conexión: ", URL + ":8080/UBUassistant/service/" + send);

                conexion = (HttpURLConnection) serverURL.openConnection();

                int responseCode = conexion.getResponseCode();
                global.setState(responseCode);

                Log.w("Res", String.valueOf(responseCode));

                if(responseCodes.contains(conexion.getResponseCode())){
                    InputStream res = conexion.getInputStream();
                    retorno = IOUtils.toString(res);

                    retorno = parseResponse(retorno);

                    Log.w("Respuesta", retorno);
                }else{
                    retorno = "No se ha conectado al servidor";
                }
            } catch (IOException e){
                Log.e("Error url", e.toString());
            }

            conexion.disconnect();
            return retorno;
        }
    }

    /**
     * Method used to check if an URL exists.
     * @return boolean state about the URL existence.
     */
    private static boolean checkIfURLExists() {
        HttpURLConnection httpUrlConn;
        try {
            httpUrlConn = (HttpURLConnection) new URL(URL + ":8080").openConnection();


            httpUrlConn.setRequestMethod("HEAD");

            httpUrlConn.setConnectTimeout(7500);
            httpUrlConn.setReadTimeout(7500);

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

        /**
         * Method used to do procedures in an AsyncTask.
         * @param params Array with parameters in String class
         * @return Response message obtained form the server
         */
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

            global.setUrl(URL);

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
