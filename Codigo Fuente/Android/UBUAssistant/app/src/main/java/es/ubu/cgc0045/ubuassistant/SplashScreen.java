package es.ubu.cgc0045.ubuassistant;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.List;

/**
 * @author Carlos González Calatrava
 */
public class SplashScreen extends Activity{
    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    Thread splashThread;

    /**
     * Method used to initialize the variables and start the procedure.
     * @param savedInstanceState Previous state of the app.
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        StartAnimations();
    }

    /**
     * Method used to start the initial animation of the app
     */
    public void StartAnimations(){
        //Create the animation and start it
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashThread = new Thread() {
            @Override
            public void run(){
                try{
                    int waited = 0;

                    while (waited < 3500){
                        sleep(100);
                        waited += 100;
                    }

                    Intent it = isOnline() ? new Intent(SplashScreen.this, MainActivity.class) : new Intent(SplashScreen.this, NotInternetAccess.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(it);
                    SplashScreen.this.finish();
                } catch (InterruptedException e){
                    Log.e("InterruptedException", e.getMessage());
                } finally {
                    SplashScreen.this.finish();
                }
            }
        };
        splashThread.start();
    }

    public boolean isOnline() {
        try {
            int timeoutMs = 5000;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        } catch (IOException e) { return false; }
    }
}
