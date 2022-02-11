package com.example.rr1706scoutingapp2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
        Random rand = new Random();
        int ds_cooldown = 0; //ds_cooldown is the cool down for the data_submitted animation
        int team;
        int round;
        int autoLowerScore;
        int autoUpperScore;
        int teleopUpperScore;
        int teleopLowerScore;
        int missedshots;
        String alliance = "none";
        boolean autoUpdateTeams = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        //Ints

        //Constraints
        //final ConstraintLayout PREGAME = findViewById(R.id.PREGAME);
        final ConstraintLayout Background = findViewById(R.id.Background);
        final ConstraintLayout Pregame = findViewById(R.id.Pregame);
        final ConstraintLayout Endgame = findViewById(R.id.Endgame);
        //Lines
        final ImageView data_submitted = findViewById(R.id.data_submitted);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //EditTexts
        final EditText name_input = findViewById(R.id.name_input);
        final EditText team_input = findViewById(R.id.team_input);
        final EditText round_input = findViewById(R.id.round_input);
        final EditText notes = findViewById(R.id.notes);
        final TextView auto_upper_text = findViewById(R.id.auto_upper_text);
        final TextView auto_lower_text = findViewById(R.id.auto_lower_text);
        final TextView teleop_upper_text = findViewById(R.id.teleop_upper_text);
        final TextView teleop_lower_text = findViewById(R.id.teleop_lower_text);
        data_submitted.setVisibility(View.INVISIBLE);
        //Buttons
        //final Button pregame_open_button = findViewById(R.id.pregame_open_button);
        //final Button pregame_close_button = findViewById(R.id.pregame_close_button);
        final Button Blue_Alliance = findViewById(R.id.Blue_Alliance);
        final Button Red_Alliance = findViewById(R.id.Red_Alliance);
        final Button Pregame_Box = findViewById(R.id.Pregame_Box);
        final Button Endgame_Box = findViewById(R.id.Endgame_Box);
        final Button no_show = findViewById(R.id.no_show);
        final Button submit = findViewById(R.id.submit);
        final ImageView auto_upper_plus = findViewById(R.id.auto_upper_plus);
        final ImageView auto_upper_minus = findViewById(R.id.auto_upper_minus);
        final ImageView auto_lower_plus = findViewById(R.id.auto_lower_plus);
        final ImageView auto_lower_minus = findViewById(R.id.auto_lower_minus);
        final ImageView teleop_upper_plus = findViewById(R.id.teleop_upper_plus);
        final ImageView teleop_upper_minus = findViewById(R.id.teleop_upper_minus);
        final ImageView teleop_lower_plus = findViewById(R.id.teleop_lower_plus);
        final ImageView teleop_lower_minus = findViewById(R.id.teleop_lower_minus);
        //No Show
        final DialogInterface.OnClickListener NoShowDialog = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    data_submitted.setImageResource(R.drawable.check);
                    data_submitted.setVisibility(View.VISIBLE);
                    ds_cooldown = 150;

                    SimpleDateFormat time = new SimpleDateFormat("dd-HHmmss", Locale.getDefault());
                    File dir = getDataDirectory();

                    try {
                        File myFile = new File(dir, team + "_" + round + "_" + time.format(new Date()) + ".txt");
                        FileOutputStream fOut = new FileOutputStream(myFile, true);
                        PrintWriter myOutWriter = new PrintWriter(new OutputStreamWriter(fOut));

                        myOutWriter.println("Scouter: " + name_input.getText());
                        myOutWriter.println("Team: " + team);
                        myOutWriter.println("Timestamp: " + time.format(new Date()));
                        myOutWriter.println("Match: " + round);

                        myOutWriter.flush();
                        myOutWriter.close();
                        fOut.close();

                        Toast.makeText(getApplicationContext(), "Data Submitted!", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "Data Submission Failed! (Tell scouting)", Toast.LENGTH_SHORT).show();
                        Log.e("Exception", "File write failed: " + e.toString());
                    }

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };

        no_show.setOnClickListener(v -> {
            String submitError = "";

            //Special handling

            if (alliance == "none") { submitError += " No Alliance,"; }
            if (name_input.getText().toString().equals("")) { submitError += " No Name,"; }
            if (team_input.getText().toString().equals("")) { submitError += " No Team#,"; }
            if (round_input.getText().toString().equals("")) { submitError += " No Round#,"; }
            if (!submitError.equals("")) { submitError = submitError.substring(0,submitError.length()-1)+"."; }

            if (!(submitError.equals(""))) {
                Toast.makeText(getApplicationContext(), "Submit Error:"+submitError, Toast.LENGTH_LONG).show();

                data_submitted.setVisibility(View.VISIBLE);
                data_submitted.setImageResource(R.drawable.x);
                ds_cooldown = 150;
            } else {
                builder.setMessage("Are you sure the team is a no show?")
                        .setPositiveButton("Yes", NoShowDialog)
                        .setNegativeButton("No", NoShowDialog)
                        .show();

            }
        });
        //The great while loop (100/sec)
        Runnable myRunnable = () -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                data_submitted.post(() -> {
                    //data_submitted stuff
                    if (ds_cooldown > 0) { ds_cooldown--; }

                    if (ds_cooldown == 0) { data_submitted.setVisibility(View.GONE); }
                });
            }
        };
        Thread myThread = new Thread(myRunnable);
        myThread.start();
        auto_upper_plus.setOnClickListener(v -> {
            if (autoUpperScore < 99) { autoUpperScore++; }
            auto_upper_text.setText(Integer.toString(autoUpperScore));
        });
        auto_upper_minus.setOnClickListener(v -> {
            if (autoUpperScore > 0) { autoUpperScore--; }
            auto_upper_text.setText(Integer.toString(autoUpperScore));
        });

        auto_lower_plus.setOnClickListener(v -> {
            if (autoLowerScore < 99) { autoLowerScore++; }
            auto_lower_text.setText(Integer.toString(autoLowerScore));
        });
        auto_lower_minus.setOnClickListener(v -> {
            if (autoLowerScore > 0) { autoLowerScore--; }
            auto_lower_text.setText(Integer.toString(autoLowerScore));
        });


        teleop_upper_plus.setOnClickListener(v -> {
            if (teleopUpperScore < 99) { teleopUpperScore++; }
            teleop_upper_text.setText(Integer.toString(teleopUpperScore));
        });
        teleop_upper_minus.setOnClickListener(v -> {
            if (teleopUpperScore > 0) { teleopUpperScore--; }
            teleop_upper_text.setText(Integer.toString(teleopUpperScore));
        });

        teleop_lower_plus.setOnClickListener(v -> {
            if (teleopLowerScore < 99) { teleopLowerScore++; }
            teleop_lower_text.setText(Integer.toString(teleopLowerScore));
        });
        teleop_lower_minus.setOnClickListener(v -> {
            if (teleopLowerScore > 0) { teleopLowerScore--; }
            teleop_lower_text.setText(Integer.toString(teleopLowerScore));
        });
        //ImageViews

        //TextViews

        //EditTexts

        //CheckBoxes

        //Spinners

        //Other elements

        //Set invisible/visible/tinted elements

        //Groups


        //Pregame
        Endgame.setVisibility(View.INVISIBLE);
        Pregame_Box.setOnClickListener(view -> {
            if(Pregame.getVisibility()==View.INVISIBLE) {
                Endgame.setVisibility(View.INVISIBLE);
                Pregame.setVisibility(View.VISIBLE);
            }
            else if(Pregame.getVisibility()==View.VISIBLE) {
                Pregame.setVisibility(View.INVISIBLE);
                Endgame.setVisibility(View.INVISIBLE);
            }
        });
        Endgame_Box.setOnClickListener(view -> {
            if(Endgame.getVisibility()==View.VISIBLE) {
                Pregame.setVisibility(View.INVISIBLE);
                Endgame.setVisibility(View.INVISIBLE);
            }
            else if(Endgame.getVisibility()==View.INVISIBLE) {
                Endgame.setVisibility(View.VISIBLE);
                Pregame.setVisibility(View.INVISIBLE);
            }
        });
        Blue_Alliance.setOnClickListener(v -> {
            Background.setBackgroundResource(R.drawable.blueappbackground);
            Pregame.setBackgroundColor(Color.argb(255, 127, 127, 247));
            Endgame.setBackgroundColor(Color.argb(255, 127, 127, 247));
            alliance="blue";
        });


        Red_Alliance.setOnClickListener(v -> {
            Background.setBackgroundResource(R.drawable.redappbackground);
            Pregame.setBackgroundColor(Color.argb(255, 247, 127, 127));
            Endgame.setBackgroundColor(Color.argb(255, 247, 127, 127));
            alliance="red";
    });
    }
    private File getDataDirectory() {
        File directory = Environment.getExternalStorageDirectory();
        File myDir = new File(directory + "/ScoutingData");
        myDir.mkdirs();
        return myDir;
    }
    public void hideKeyboard(View view)
    {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}