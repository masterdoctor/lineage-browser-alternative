package com.clout_team.masterdoctor.browser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainWindow extends AppCompatActivity {

    //public static EditText url;
    public static WebView engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Set up variables and URL viewer
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        engine = (WebView) findViewById(R.id.webView);
        EditText url = (EditText) findViewById(R.id.editText);
        url.setImeActionLabel("Browse", KeyEvent.KEYCODE_ENTER);
        url.setFocusableInTouchMode(true);

        Toast.makeText(MainWindow.this, "debug1", Toast.LENGTH_SHORT).show();

        //Set URL viewer enter event
        url.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //If is 'enter' key
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    Toast.makeText(MainWindow.this, "debug2", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(MainWindow.this, "Downloading page...", Toast.LENGTH_SHORT).show();
                    EditText url = (EditText) findViewById(R.id.editText);
                    openPage(url.getText().toString());
                    checkButtons();
                    return true;
                }
                return false;
            }
        });


        //Configure engine
        WebSettings engineSettings = engine.getSettings();
        engineSettings.setJavaScriptEnabled(true);

        //Load default homepage
        engine.loadUrl(url.getText().toString());
        checkButtons();

        //Set browse event code.
        engine.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
                openPage(request.getUrl().toString());
                checkButtons();
                return true;
            }
        });
        //int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
    }

    //KeyEvent for back key
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(engine.canGoBack()) {
                engine.goBack();
                checkButtons();
            }else{
                this.finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //Function for opening page
    public void openPage(String requestedURL){
        Toast.makeText(MainWindow.this, "debug3", Toast.LENGTH_SHORT).show();

        engine.loadUrl(requestedURL);
        checkButtons();
    }

    //Go back
    public void goBack(View view){
        if(engine.canGoBack()){
            engine.goBack();
        }

        checkButtons();
    }

    //Go forward
    public void goForward(View view){
        if(engine.canGoForward()){
            engine.goForward();
        }

        checkButtons();
    }

    public void checkButtons(){
        if(!engine.canGoBack()){
            //set back button disabled
            ImageButton btn = (ImageButton) findViewById(R.id.backBtn);
            btn.setEnabled(false);
        }else{
            ImageButton btn = (ImageButton) findViewById(R.id.backBtn);
            btn.setEnabled(true);
        }

        if(!engine.canGoForward()){
            //set forward button disabled
            ImageButton btn = (ImageButton) findViewById(R.id.fwdBtn);
            btn.setEnabled(false);
        }else{
            ImageButton btn = (ImageButton) findViewById(R.id.fwdBtn);
            btn.setEnabled(true);
        }

        updateUrlBar();
    }

    public void updateUrlBar(){
        EditText url = (EditText) findViewById(R.id.editText);
        url.setText(engine.getUrl().toString());
    }
}
