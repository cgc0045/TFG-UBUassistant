package es.ubu.cgc0045.ubuassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author Carlos González Calatrava
 */
public class NotInternetAccess extends AppCompatActivity {

    Button salir;

    /**
     * Method used to retry open the app
     * @param savedInstanceState Previous app state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_internet_access);

        salir = findViewById(R.id.salir);
        Button reintentar = findViewById(R.id.reintentar);

        reintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(NotInternetAccess.this, SplashScreen.class);
                startActivity(it);
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                finishAffinity();
                System.exit(0);
            }
        });
    }
}
