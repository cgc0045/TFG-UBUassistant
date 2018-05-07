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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    private TextView resultText;
    private Socket socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = (TextView) findViewById(R.id.editText);
        Button enviar = (Button) findViewById(R.id.enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new connectServer().execute();
            }
        });

        new Thread(new ClientThread()).start();
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

    public void onActivityResult(int request_code, int result_code, Intent i){
        super.onActivityResult(request_code, result_code, i);

        switch (request_code){
            case 100:
                if(result_code == RESULT_OK && i != null){
                    ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    resultText.setText(result.get(0));
                }
                break;
        }
    }

    class ClientThread implements Runnable{

        @Override
        public void run(){

            Log.w("Thread", "Funciona!!!");

            try{
                InetAddress serverAddr = InetAddress.getByName("192.168.1.19");

                socket = new Socket("192.168.1.19", 6789);
            }catch (UnknownHostException e1){
                e1.printStackTrace();
            }catch (IOException e2){
                e2.printStackTrace();
            }
        }
    }

    private class connectServer extends AsyncTask<String, Void, String>{
        @Override
        protected  String doInBackground(String... params){

            String respuesta;
            String mod;

            Log.w("Async", "Funcionando");

            try {

                Log.w("Async", "Funcionando");

                DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                Log.w("Async", "Funcionando");

                respuesta = resultText.getText().toString();
                outToServer.writeBytes(respuesta);
                mod = inFromServer.readLine();
                Log.w("Respuesta", "Pues va a ser que si");

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, mod, duration);
                toast.show();

                socket.close();

            }catch (IOException e){

            }

            return "Executed";
        }
    }

    public void connectServer(){

    }

}
