package es.ubu.cgc0045.ubuassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author Carlos González Calatrava
 */
public class NotConnectError extends AppCompatActivity {

    /**
     * Method used to initialize the variables and start the procedure.
     * @param savedInstanceState Previous state of the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_connect_error);

        Button cerrar = findViewById(R.id.cerrar);

        cerrar.setOnClickListener(new View.OnClickListener() {
            /**
             * Method used to start an action when the button is clicked.
             * @param v View of the application
             */
            @Override
            public void onClick(View v) {
                finish();
                finishAffinity();
                System.exit(0);
            }
        });
    }
}
