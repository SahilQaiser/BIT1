package com.sq.bit.bit1;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button bit, papers, kun, kur, set, exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView appName = (TextView)findViewById(R.id.appNameView);
        Typeface school = Typeface.createFromAsset(getAssets(),"fonts/sensation.ttf");
        appName.setTypeface(school);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Feedback.class);
                startActivity(i);
            }
        });
    }
    public void onClickKUN(View view)
    {

        Intent kun = new Intent(MainActivity.this, KUN.class);
        startActivity(kun);
    }
    Intent i;

    public void onClickBit(View view)
    {
        bit = (Button)findViewById(R.id.bBit);
        i = new Intent(MainActivity.this, bit.class);
        startActivity(i);

    }

    public void onClickBitPaper(View view)
    {
        Intent i = new Intent(MainActivity.this, Papers.class);
        startActivity(i);
    }

    public void onClickKUR(View view)
    {
        Intent kur = new Intent(MainActivity.this, KUR.class);
        startActivity(kur);
    }

    public void onClickPref(View view)
    {
        Intent settings = new Intent(MainActivity.this, Preferences.class);
        startActivity(settings);
    }

    public void onClickExit(View view)
    {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent i = new Intent(MainActivity.this, AboutUs.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_exit) {
            finish();
        }
        if (id == R.id.preferences) {
            Intent i = new Intent(MainActivity.this, Preferences.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
