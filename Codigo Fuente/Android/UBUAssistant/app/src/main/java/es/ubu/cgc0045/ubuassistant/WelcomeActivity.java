package es.ubu.cgc0045.ubuassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome2);
    }

    public void onButtonClick(View v){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}
