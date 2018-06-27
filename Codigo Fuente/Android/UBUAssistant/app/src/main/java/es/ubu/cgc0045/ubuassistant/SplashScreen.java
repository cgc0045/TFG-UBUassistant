package es.ubu.cgc0045.ubuassistant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

                    Intent it = new Intent(SplashScreen.this, MainActivity.class);
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
}
