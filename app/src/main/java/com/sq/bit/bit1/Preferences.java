package com.sq.bit.bit1;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Preferences extends AppCompatActivity{
    Switch switchKU;
    NumberPicker npText;
    RadioGroup rgText;
    RadioGroup rgBack;
    Spinner font_select;
    Typeface font;

    public final String Font = "FONT";
    public final String FontSize="FONT_SIZE";
    public final String FontColor="FONT_COLOR";
    public final String BackColor="BACK_COLOR";
    public final String DesktopView="DESKTOP_VIEW";

    Button b, bR;
    TextView tvTextFontAdviser, textColor, backColor, Sample, tvFontChange;

    public SharedPreferences pref1;
    public static SharedPreferences pref2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        npText = (NumberPicker)findViewById(R.id.fontSize);
        npText.setMaxValue(100);
        npText.setMinValue(10);
        npText.setValue(20);
        npText.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        //TextSize
        pref1 = getSharedPreferences(Font, MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref1.edit();
            Integer val = (int)pref1.getInt(FontSize, 20);
            npText.setValue(val);
            int size = npText.getValue(); //needs edit
            Sample = (TextView) findViewById(R.id.tvSample);
            Sample.setTextSize(size);
            textColor = (TextView) findViewById(R.id.tvTextColor);
            textColor.setTextSize(size);
            backColor = (TextView) findViewById(R.id.tvBackColor);
            backColor.setTextSize(size);
            tvFontChange = (TextView)findViewById(R.id.tvFontChange);
        //BrowserView
        switchKU = (Switch)findViewById(R.id.switchKUView);
        pref2 = getSharedPreferences(DesktopView, MODE_PRIVATE);
        final SharedPreferences.Editor editor2 = pref2.edit();
            boolean desktop = pref2.getBoolean(DesktopView, false);
            switchKU.setChecked(desktop);
            switchKU.setFocusable(true);
        //TextColor
        //pref1 = getSharedPreferences(FontColor,MODE_PRIVATE);
        //final SharedPreferences.Editor editor3 = pref3.edit();
        rgText = (RadioGroup)findViewById(R.id.rgText);
            int tcolor = pref1.getInt(FontColor,R.id.rbtBlack);
            rgText.check(tcolor);
        //BackColor
        //pref4 = getSharedPreferences(BackColor,MODE_PRIVATE);
        //final SharedPreferences.Editor editor4 = pref4.edit();
        rgBack = (RadioGroup)findViewById(R.id.rgBack);
            int bcolor = pref1.getInt(BackColor,R.id.rbbOlive);
            rgBack.check(bcolor);
        //Buttons
        b = (Button) findViewById(R.id.bSave);
        bR = (Button) findViewById(R.id.bReset);
        //desktopView = pref.getBoolean(DESKTOP_VIEW, false);

        //Spinner for selecting a font
        font = Typeface.createFromAsset(getAssets(),"fonts/Quicksand.ttf");
        font_select = (Spinner)findViewById(R.id.spinner_font_select);

        //Creating a DataAdapter and Adding Fonts to Spinner List
        List<String> list = new ArrayList<String>();
        list.add("Choose A Font :");
        list.add("QuickSand");
        list.add("Amatic");
        list.add("Sensation");
        list.add("BlackJack");
        list.add("Journal");
        list.add("PWSchool");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        font_select.setAdapter(dataAdapter);
        font_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch_font(position);
                ((TextView) view).setTypeface(font);
                textColor.setTypeface(font);
                backColor.setTypeface(font);
                switchKU.setTypeface(font);
                tvTextFontAdviser.setTypeface(font);
                Sample.setTypeface(font);
                tvFontChange.setTypeface(font);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tvTextFontAdviser = (TextView) findViewById(R.id.tvTextFontAdviser);
        int font_selection = pref1.getInt("FONT SELECTION", 0);
        font_select.setSelection(font_selection);

        switch_font(font_selection);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int font_selection = font_select.getSelectedItemPosition();
                editor.putInt(FontSize, npText.getValue()); //editor
                //editor2
                if (switchKU.isChecked()) {
                    editor2.putBoolean(DesktopView, true);
                } else
                    editor2.putBoolean(DesktopView, false);
                //FontColor
                editor.putInt(FontColor, rgText.getCheckedRadioButtonId());
                //BackColor
                editor.putInt(BackColor, rgBack.getCheckedRadioButtonId());
                //Font Selection
                editor.putInt("FONT SELECTION", font_selection);
                //Commiting the Changes
                editor.commit();
                editor2.apply();
                Toast.makeText(Preferences.this, "Settings Saved....", Toast.LENGTH_SHORT).show();
                //editor3.apply();
                //editor4.apply();
            }
        });
        npText.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                TextView Sample = (TextView) findViewById(R.id.tvSample);
                Sample.setTextSize(newVal);
                TextView textColor = (TextView) findViewById(R.id.tvTextColor);
                textColor.setTextSize(newVal);
                TextView backColor = (TextView) findViewById(R.id.tvBackColor);
                backColor.setTextSize(newVal);
            }
        });

        bR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //editor.putInt(FontSize, 20);
                npText.setValue(20);
                switchKU.setChecked(false);
                rgText.check(R.id.rbtBlack);
                rgBack.check(R.id.rbbOlive);
                TextView Sample = (TextView) findViewById(R.id.tvSample);
                Sample.setTextSize(npText.getValue());
                TextView textColor = (TextView) findViewById(R.id.tvTextColor);
                textColor.setTextSize(npText.getValue());
                TextView backColor = (TextView) findViewById(R.id.tvBackColor);
                backColor.setTextSize(npText.getValue());
            }
        });

        switchKU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchKU.isChecked())
                    Toast.makeText(Preferences.this, "Works on Some Devices :p", Toast.LENGTH_SHORT).show();
            }
        });
        switchKU.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    editor2.putBoolean(DesktopView, true);
                } else editor2.putBoolean(DesktopView, false);

            }
        });
    }
    private void switch_font(int font_id){
        switch (font_id) {
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
    }
}

