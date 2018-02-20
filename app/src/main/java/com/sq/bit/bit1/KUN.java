package com.sq.bit.bit1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class KUN extends AppCompatActivity {
    WebView kun;
    private SwipeRefreshLayout swipeLayout;
    int p=0;
    TextView appName;
    ProgressBar kunprogress;
    SharedPreferences pref = Preferences.pref2;
    boolean desktopView;
    String toLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kun);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appName = (TextView)findViewById(R.id.tvKUN);
        Typeface school = Typeface.createFromAsset(getAssets(),"fonts/sensation.ttf");
        appName.setTypeface(school);
        toLoad = "http://www.kashmiruniversity.net/notifications.aspx";
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        kunprogress = (ProgressBar)findViewById(R.id.kunprogress);
        kun=(WebView)findViewById(R.id.wvKUN);
        WebSettings settings = kun.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        String newUA = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";
        final String foo = settings.getUserAgentString();
        if(pref!=null){
            desktopView = pref.getBoolean("DESKTOP_VIEW",false);
        }
        if(desktopView){
            settings.setUserAgentString(newUA);
        } else
            settings.setUserAgentString(foo);
        kun.setWebViewClient(new myWebViewClient());
        kun.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                /*if (mimetype.equalsIgnoreCase("application/pdf")) {
                    kun.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);
                } else {*/
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                //}
                //Toast.makeText(KUN.this, "mimeType: " + mimetype + "\nLength: " + (contentLength / 1024) + " KBs", Toast.LENGTH_LONG).show();

            }
        });
        kun.setWebChromeClient(new myWebChromeClient());
        if(checkInternetConnection(this)){
            kun.loadUrl("http://www.kashmiruniversity.net/notifications.aspx");
        }else
        {
            kun.loadUrl("file:///android_asset/home/ku.html");
        }
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (checkInternetConnection(KUN.this)) {
                        kun.loadUrl(toLoad);
                        appName.setVisibility(View.VISIBLE);
                        kunprogress.setVisibility(View.VISIBLE);
                    } else {
                        kun.loadUrl("file:///android_asset/home/ku.html");
                    }
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



    public class myWebChromeClient extends WebChromeClient {
        public myWebChromeClient() {
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            p=newProgress;
            kunprogress.setProgress(p);
            super.onProgressChanged(view, newProgress);
        }
    }


    public class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            appName.setVisibility(View.VISIBLE);
            kunprogress.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if(!kun.getUrl().equalsIgnoreCase("file:///android_asset/home/ku.html")){
                toLoad = kun.getUrl();
            }
            kun.loadUrl("file:///android_asset/home/ku.html");
            Toast.makeText(KUN.this, "Ouch!! " + description, Toast.LENGTH_SHORT).show();
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if(swipeLayout.isRefreshing()){
                swipeLayout.setRefreshing(false);
            }
            appName.setVisibility(View.GONE);
            kunprogress.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && kun.canGoBack()){
            kun.goBack();
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
            Intent i = new Intent(KUN.this, AboutUs.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_exit) {
            finish();
        }
        if (id == R.id.preferences) {
            Intent i = new Intent(KUN.this, Preferences.class);
            startActivity(i);
            return true;
        }
        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
