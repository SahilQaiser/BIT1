package com.sq.bit.bit1;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class bit extends AppCompatActivity {

    public static String EXTRA_FILE = "600";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView appName = (TextView)findViewById(R.id.appNameView);
        Typeface school = Typeface.createFromAsset(getAssets(),"fonts/sensation.ttf");
        appName.setTypeface(school);
    }
    public void onClickB6(View view)
    {
        Intent b6 = new Intent(bit.this, Syllabus.class);
        b6.putExtra(EXTRA_FILE, 600);
        startActivity(b6);
    }
    public void onClickB5(View view)
    {
        Intent b5 = new Intent(bit.this, Syllabus.class);
        b5.putExtra(EXTRA_FILE, 500);
        startActivity(b5);
    }
    public void onClickB4(View view)
    {
        Intent b4 = new Intent(bit.this, Syllabus.class);
        b4.putExtra(EXTRA_FILE, 400);
        startActivity(b4);
    }
    public void onClickB3(View view)
    {
        Intent b3 = new Intent(bit.this, Syllabus.class);
        b3.putExtra(EXTRA_FILE, 300);
        startActivity(b3);
    }
    public void onClickB2(View view)
    {
        Intent b2 = new Intent(bit.this, Syllabus.class);
        b2.putExtra(EXTRA_FILE, 200);
        startActivity(b2);
    }
    public void onClickB1(View view)
    {
        Intent b1 = new Intent(bit.this, Syllabus.class);
        b1.putExtra(EXTRA_FILE, 100);
        startActivity(b1);
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
            Intent i = new Intent(bit.this, AboutUs.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_exit) {
            finish();
        }
        if (id == R.id.preferences) {
            Intent i = new Intent(bit.this, Preferences.class);
            startActivity(i);
            return true;
        }
        if (id==android.R.id.home) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
