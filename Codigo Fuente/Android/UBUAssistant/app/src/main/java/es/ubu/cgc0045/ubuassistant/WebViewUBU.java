package es.ubu.cgc0045.ubuassistant;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.Toast;

public class WebViewUBU extends AppCompatActivity {
    String title;
    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        global = (Global) getApplicationContext();
        logVisit();
        this.setTitle("Cargando...");

        setContentView(R.layout.activity_web_view_ubu);

        String url = "www.google.es";

        if(savedInstanceState == null){
            url = getIntent().getDataString().replace("ubuassistant://","");
        }

        WebView myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                WebViewUBU.this.setTitle(view.getTitle());
                Log.w("Global", global.getWords().get(0));
            }
        });
        myWebView.loadUrl(url);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();

        Intent i = new Intent(getApplicationContext(), Vote.class);
        startActivity(i);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void logVisit(){

    }
}
