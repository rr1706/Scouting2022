package com.example.rr1706scoutingapp2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        //Constraints
        //final ConstraintLayout PREGAME = findViewById(R.id.PREGAME);
        final ConstraintLayout Background = findViewById(R.id.Background);
        final ConstraintLayout Pregame = findViewById(R.id.Pregame);
        //Lines

        //Buttons
        //final Button pregame_open_button = findViewById(R.id.pregame_open_button);
        //final Button pregame_close_button = findViewById(R.id.pregame_close_button);
        final Button Blue_Alliance = findViewById(R.id.Blue_Alliance);
        final Button Red_Alliance = findViewById(R.id.Red_Alliance);
        final Button Pregame_Box = findViewById(R.id.Pregame_Box);
        //ImageViews

        //TextViews

        //EditTexts

        //CheckBoxes

        //Spinners

        //Other elements

        //Set invisible/visible/tinted elements

        //Groups


        //Pregame
        /*pregame_open_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {"
                PREGAME.setVisibility(View.VISIBLE);
            }
        });

        pregame_close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PREGAME.setVisibility(View.INVISIBLE);
            }
        });*/
        Pregame_Box.setOnClickListener(view -> {
            if(Pregame.getVisibility()==View.INVISIBLE)
                Pregame.setVisibility(View.VISIBLE);
            else if(Pregame.getVisibility()==View.VISIBLE)
                Pregame.setVisibility(View.INVISIBLE);
        });

        Blue_Alliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Background.setBackgroundColor(Color.argb(255, 223, 223, 255));
                Pregame.setBackgroundColor(Color.argb(255, 127, 127, 247));
            }
        });


        Red_Alliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Background.setBackgroundColor(Color.argb(255, 255, 223, 223));
                Pregame.setBackgroundColor(Color.argb(255, 247, 127, 127));
        }});
    }

    public void hideKeyboard(View view)
    {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    };
}