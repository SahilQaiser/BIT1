package com.sq.bit.bit1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class Syllabus extends Activity  {
    int file;
    String tabHeader;
    TabHost th;

    SharedPreferences prefFont;
    //SharedPreferences prefFontColor = Preferences.pref3;
    //SharedPreferences prefBackColor = Preferences.pref4;

    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    Typeface font;

    int newSize;
    int newFont;
    int newColor;
    int backColor;
    LinearLayout mainPref;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);
        prefFont = getSharedPreferences("FONT", Context.MODE_PRIVATE);
        th = (TabHost)findViewById(R.id.tabhost);
        th.setup();
        getDataFromIntent();
        setTabs();
        setTabParams();
        newText();
        viewText();
        selectTextView();
    }
    //Check Focussed TextView
    private void selectTextView(){
        if(tv1.isFocused())
            shareText(tv1);
        else if(tv2.isFocused())
            shareText(tv2);
        else if(tv3.isFocused())
            shareText(tv3);
        else if(tv4.isFocused())
            shareText(tv4);
    }
    //share Text
    private void shareText(TextView tv){
        tv.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                getMenuInflater().inflate(R.menu.share_main, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                /**
                 * Use the following code if you want to remove the default icons (select all, cut or copy).
                 */
                // Remove the "select all" option
                menu.removeItem(android.R.id.selectAll);
                // Remove the "cut" option
                //menu.removeItem(android.R.id.cut);
                // Remove the "copy all" option
                //menu.removeItem(android.R.id.copy);
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_item_share:
                        String selectedText = getSelectedText();
                        Share(selectedText);
                        return true;
                    case R.id.web_search:
                        String sel = getSelectedText();
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.in/search?q=" + sel));
                        startActivity(i);
                        break;
                    /*case R.id.menu_item_highlight:
                        prefCount++;
                        String selectedText1 = getSelectedText();
                        Highlight(selectedText1);
                    case R.id.menu_item_unhighlight:
                        String selectedText2 = getSelectedText();
                        UnHighlight(selectedText2);*/
                    default:
                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }

        });
    }
    //Highlight
    private void Highlight(String selectedText){
        Toast.makeText(this, "Cant Highlight yet!!", Toast.LENGTH_SHORT).show();
        Spannable word = new SpannableString(tv1.getText());
        word.setSpan(new BackgroundColorSpan(Color.YELLOW),min, max, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv1.setText(word);
    }

    //UnHighlight
    private void UnHighlight(String selectedText){
        Spannable word = new SpannableString(tv1.getText());
        word.setSpan(new BackgroundColorSpan(Color.TRANSPARENT),min, max, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv1.setText(word);
    }

    int min = 0;
    int max = 1;

    //getSelectedText
    private String getSelectedText(){
        String selectedText = "";
        if(tv1.isFocused()){
            final int textStartIndex = tv1.getSelectionStart();
            final int textEndIndex = tv1.getSelectionEnd();
            min = Math.max(0,Math.min(textStartIndex, textEndIndex));
            max = Math.max(0, Math.max(textStartIndex, textEndIndex));
            selectedText = tv1.getText().subSequence(min, max).toString().trim();
            //addPref(min,max);
        }
        return selectedText;
    }
    //addPreferences


    //Share
    private void Share(String text){
        if(!text.equals("")){
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(share, "Share Via"));
        }
        else Toast.makeText(this, "Empty Selection", Toast.LENGTH_SHORT).show();
    }
    //Change Text Size and Color
    public void newText(){
        if(prefFont !=null){
            newSize=prefFont.getInt("FONT_SIZE", 30);
        }
        if(prefFont !=null){
            newFont=prefFont.getInt("FONT SELECTION", 1);
        }
        tv1 = (TextView) findViewById(R.id.tvB11);
        tv2 = (TextView) findViewById(R.id.tvB12);
        tv3 = (TextView) findViewById(R.id.tvB13);
        tv4 = (TextView) findViewById(R.id.tvB14);
        font = Typeface.createFromAsset(getAssets(),"fonts/Quicksand.ttf");
        //Set new TypeFace
        switch(newFont){
            case 0:
                break;
            case 1:
                font = Typeface.createFromAsset(getAssets(),"fonts/Quicksand.ttf");
                break;
            case 2:
                font = Typeface.createFromAsset(getAssets(),"fonts/Amatic.ttf");
                break;
            case 3:
                font = Typeface.createFromAsset(getAssets(),"fonts/sensation.ttf");
                break;
            case 4:
                font = Typeface.createFromAsset(getAssets(),"fonts/black_jack.ttf");
                break;
            case 5:
                font = Typeface.createFromAsset(getAssets(),"fonts/DancingScript.ttf");
                break;
            case 6:
                font = Typeface.createFromAsset(getAssets(),"fonts/school.ttf");
                break;
        }
        tv1.setTypeface(font);
        tv2.setTypeface(font);
        tv4.setTypeface(font);
        tv3.setTypeface(font);

        if(newSize>0) {
            tv1.setTextSize(newSize);
            tv2.setTextSize(newSize);
            tv3.setTextSize(newSize);
            tv4.setTextSize(newSize);
        }
        if(prefFont != null){
            switch(prefFont.getInt("FONT_COLOR",R.id.rbtBlack)){
                case R.id.rbtBlack:
                    newColor = Color.BLACK;
                    break;
                case R.id.rbtBlue:
                    newColor = Color.BLUE;
                    break;
                case R.id.rbtGray:
                    newColor = Color.GRAY;
                    break;
                case R.id.rbtGreen:
                    newColor = Color.GREEN;
                    break;
                case R.id.rbtPurple:
                    newColor = Color.parseColor("#8a2be2");
                    break;
                case R.id.rbtSteel:
                    newColor = Color.parseColor("#C5CAE9");
                    break;
            }
        }
        if(newColor != 0){
            tv1.setTextColor(newColor);
            tv2.setTextColor(newColor);
            tv3.setTextColor(newColor);
            tv4.setTextColor(newColor);
        }
        if(prefFont != null){
            switch(prefFont.getInt("BACK_COLOR",R.id.rbbOlive)){
                case R.id.rbbWhite:
                    backColor = Color.WHITE;
                    break;
                case R.id.rbbBlue:
                    backColor = Color.BLUE;
                    break;
                case R.id.rbbGray:
                    backColor = Color.GRAY;
                    break;
                case R.id.rbbOlive:
                    backColor = Color.parseColor("#d3ffce");
                    break;
                case R.id.rbbPurple:
                    backColor = Color.parseColor("#8a2be2");
                    break;
                case R.id.rbbIndigo:
                    backColor = Color.parseColor("#3F51B5");
                    break;
            }
            if(backColor != 0){
                mainPref = (LinearLayout)findViewById(R.id.MainPref);
                mainPref.setBackgroundColor(backColor);
            }
        }
    }

    public void setTabs(){
        TabHost.TabSpec specs = th.newTabSpec("tag1"); //Tab 1
        specs.setContent(R.id.tab1);
        specs.setIndicator(tabHeader + "01");
        th.addTab(specs);
        specs = th.newTabSpec("tag2"); //Tab2
        specs.setContent(R.id.tab2);
        specs.setIndicator(tabHeader+"02");
        th.addTab(specs);
        specs = th.newTabSpec("tag3"); //Tab3
        specs.setContent(R.id.tab3);
        specs.setIndicator(tabHeader + "03");
        th.addTab(specs);
        specs = th.newTabSpec("tag4"); //Tab4
        specs.setContent(R.id.tab4);
        specs.setIndicator(tabHeader + "04");
        th.addTab(specs);
    }
    public void getDataFromIntent(){
        //Get Values from Intents
        Intent intent = getIntent();
        file = intent.getIntExtra(bit.EXTRA_FILE, 600);
        switch(file/100){
            case 1:
                tabHeader = "BIT1";
                break;
            case 2:
                tabHeader = "BIT2";
                break;
            case 3:
                tabHeader = "BIT3";
                break;
            case 4:
                tabHeader = "BIT4";
                break;
            case 5:
                tabHeader = "BIT5";
                break;
            case 6:
                tabHeader = "BIT6";
                break;
        }
    }
    public void viewText(){
        String text="";

        int semCode = file;
        while(file <(semCode+4)) {
            try {
                file++;
                String fileNum = "syllabus/" + file + ".txt";
                InputStream is = getAssets().open(fileNum); //Tab1
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                text = new String(buffer);
                tv1.setText(text);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                file++;
                String fileNum = "syllabus/" + file + ".txt";
                InputStream is = getAssets().open(fileNum); //Tab2
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                text = new String(buffer);
                tv2.setText(text);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                file++;
                String fileNum = "syllabus/" + file + ".txt";
                InputStream is = getAssets().open(fileNum); //Tab3
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                text = new String(buffer);
                tv3.setText(text);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                file++;
                String fileNum = "syllabus/" + file + ".txt";
                InputStream is = getAssets().open(fileNum); //Tab4
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                text = new String(buffer);
                tv4.setText(text);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(file>semCode+4)
                break;
        }
    }
    public void setTabParams(){
        //Change Width of TabTexts
        //Tab1
        ViewGroup.LayoutParams params = th.getTabWidget().getChildAt(0).getLayoutParams();
        params.width=30;
        th.getTabWidget().getChildAt(0).setLayoutParams(params);
        //Tab2
        params = th.getTabWidget().getChildAt(1).getLayoutParams();
        params.width=30;
        th.getTabWidget().getChildAt(1).setLayoutParams(params);
        //Tab3
        params = th.getTabWidget().getChildAt(2).getLayoutParams();
        params.width=30;
        th.getTabWidget().getChildAt(2).setLayoutParams(params);
        //Tab4
        params = th.getTabWidget().getChildAt(3).getLayoutParams();
        params.width=30;
        th.getTabWidget().getChildAt(3).setLayoutParams(params);
    }

}
