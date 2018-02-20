package com.sq.bit.bit1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    TextView tvAbout,tvAbout1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        tvAbout = (TextView)findViewById(R.id.tvAbout);
        tvAbout1 = (TextView)findViewById(R.id.tvAbout1);
        Typeface amatic = Typeface.createFromAsset(getAssets(),"fonts/Amatic.ttf");
        Typeface school = Typeface.createFromAsset(getAssets(),"fonts/SweetlyBroken.ttf");
        tvAbout.setTextSize(25);
        tvAbout.setTypeface(amatic);
        tvAbout1.setTextSize(20);
        tvAbout1.setTypeface(school);
    }
    public void onContactUs(View view){
        Button SahilQaiser = (Button)findViewById(R.id.SahilQaiser);
        Button Wajid = (Button)findViewById(R.id.Wajid);
        Button Faysal = (Button)findViewById(R.id.Faysal);
        Button Tufail = (Button)findViewById(R.id.Tufail);
        //Make Buttons Visible
        SahilQaiser.setVisibility(View.VISIBLE);
        SahilQaiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/Sahil.Qaiser"));
                startActivity(i);
            }
        });
        //Wajid Button
        Wajid.setVisibility(View.VISIBLE);
        Wajid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/syedwajid.aalam"));
                startActivity(i);
            }
        });
        //Faysal Button
        Faysal.setVisibility(View.VISIBLE);
        Faysal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/shk.faysal"));
                startActivity(i);
            }
        });
        //Tufail Button
        Tufail.setVisibility(View.VISIBLE);
        Tufail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/profile.php?id=100006486880479"));
                startActivity(i);
            }
        });
        //Actions Down
        tvAbout.setText("Contact Details");
        tvAbout1.setVisibility(View.GONE);


    }

}
