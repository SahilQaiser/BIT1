package com.sq.bit.bit1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class KUR extends AppCompatActivity {
    WebView kur;
    private SwipeRefreshLayout swipeLayout;
    ProgressBar progress;
    int p=0;
    TextView appName;
    SharedPreferences pref = Preferences.pref2;
    boolean desktopView;
    String toLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kur);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appName = (TextView)findViewById(R.id.tvKUR);
        Typeface school = Typeface.createFromAsset(getAssets(),"fonts/sensation.ttf");
        appName.setTypeface(school);
        toLoad = "http://egov.uok.edu.in/results/";

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        kur=(WebView)findViewById(R.id.wvKUR);
        progress = (ProgressBar)findViewById(R.id.progress);
        WebSettings settings = kur.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        final String foo = kur.getSettings().getUserAgentString();
        String newUA = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";
        if(pref!=null){
            desktopView = pref.getBoolean("DESKTOP_VIEW",false);
        }
        if(desktopView){
            settings.setUserAgentString(newUA);
        } else
            settings.setUserAgentString(foo);
        Log.d("UA", kur.getSettings().getUserAgentString());
        kur.setWebChromeClient(new myWebChromeClient());
        kur.setWebViewClient(new myWebViewClient());
        if(checkInternetConnection(this)){
            kur.loadUrl("http://egov.uok.edu.in/results/");
        }else {
            kur.loadUrl("file:///android_asset/home/ku.html");
        }

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(checkInternetConnection(KUR.this)){
                    kur.loadUrl(toLoad);
                    appName.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.VISIBLE);
                }else kur.loadUrl("file:///android_asset/home/ku.html");
            }
        });
    }
    //Check Internet
    public static boolean checkInternetConnection(Context context) {

        ConnectivityManager con_manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
    //Custom WebChromeClient
    public class myWebChromeClient extends WebChromeClient {
        public myWebChromeClient() {
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            p=newProgress;
            progress.setProgress(p);
            super.onProgressChanged(view, newProgress);
        }
    }
    public class myWebViewClient extends WebViewClient{
    @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        appName.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if(!kur.getUrl().equalsIgnoreCase("file:///android_asset/home/ku.html")){
            toLoad = kur.getUrl();
        }
        kur.loadUrl("file:///android_asset/home/ku.html");
        Toast.makeText(KUR.this, "Ouch!! "+description, Toast.LENGTH_SHORT).show();
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if(swipeLayout.isRefreshing()){
            swipeLayout.setRefreshing(false);
        }
        appName.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        super.onPageFinished(view, url);
    }
}


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && kur.canGoBack()){
            kur.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.page_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_aboutUs) {
            Intent i = new Intent(KUR.this, AboutUs.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_exit) {
            finish();
        }
        if (id == R.id.preferences) {
            Intent i = new Intent(KUR.this, Preferences.class);
            startActivity(i);
            return true;
        }
        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
