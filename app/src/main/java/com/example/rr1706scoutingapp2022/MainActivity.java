package com.example.rr1706scoutingapp2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
        final ConstraintLayout PREGAME = findViewById(R.id.PREGAME);

        //Lines

        //Buttons
        final Button pregame_open_button = findViewById(R.id.pregame_open_button);
        final Button pregame_close_button = findViewById(R.id.pregame_close_button);
        //ImageViews

        //TextViews

        //EditTexts

        //CheckBoxes

        //Spinners

        //Other elements

        //Set invisible/visible/tinted elements

        //Pregame
        pregame_open_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PREGAME.setVisibility(View.VISIBLE);
            }
        });

        pregame_close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PREGAME.setVisibility(View.INVISIBLE);
            }
        });
    }
}