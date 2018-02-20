package com.sq.bit.bit1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


    }
    public void onClickSend(View view)
    {
        EditText feedback = (EditText)findViewById(R.id.body);
        EditText name = (EditText)findViewById(R.id.name);
        EditText email = (EditText)findViewById(R.id.email);
        String body = "Hello My Name is "+name.getText().toString()+", My Email is "+email.getText().toString()+"\n"
                +"Here is my FeedBack: \n"+feedback.getText().toString();
        String emailList[] = { "sahil.qaiser@gmail.com", "shkfaysal225@gmail.com, sdwjd51@gmail.com"};
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL, emailList);
        i.putExtra(Intent.EXTRA_SUBJECT, "Feedback (BScIT)");
        i.setType("plain/text");
        i.putExtra(Intent.EXTRA_TEXT,body);
        startActivity(i);

    }
}
