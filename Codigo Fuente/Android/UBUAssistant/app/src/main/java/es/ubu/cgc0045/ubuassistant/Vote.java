package es.ubu.cgc0045.ubuassistant;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class Vote extends Activity {

    Button enviar;
    RatingBar ratingBar;
    Global global;
    ConstraintLayout cl;


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
                Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(rate), Toast.LENGTH_LONG);
                toast.show();
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

    public void rateMe(View view){

        Toast.makeText(getApplicationContext(),
                String.valueOf(ratingBar.getRating()), Toast.LENGTH_LONG).show();
    }
}
