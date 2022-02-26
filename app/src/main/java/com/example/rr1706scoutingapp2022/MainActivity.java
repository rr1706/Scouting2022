package com.example.rr1706scoutingapp2022;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
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
        String scouterName;
        int round;
        int roundfill = 1;
        int missedScore;
        int autoLowerScore;
        int autoUpperScore;
        int teleopUpperScore;
        int teleopLowerScore;
        int missedshots;
        String alliance = "none";
        boolean autoUpdateTeams = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Ints
        final Spinner speed = findViewById(R.id.robotSpeed);
        final Spinner shotDistance = findViewById(R.id.shotDistance);
        final Spinner endgame_results = findViewById(R.id.endgameResults);
        final Spinner violations = findViewById(R.id.violations);
        final Spinner climbResult = findViewById(R.id.endgameClimb);
        //Constraints
        //final ConstraintLayout PREGAME = findViewById(R.id.PREGAME);
        final ConstraintLayout Background = findViewById(R.id.Background);
        final ConstraintLayout Pregame = findViewById(R.id.Pregame);
        final ConstraintLayout Endgame = findViewById(R.id.Endgame);
        final Button Gray_Box = findViewById(R.id.grayBox);
        //Lines
        final ImageView data_submitted = findViewById(R.id.data_submitted);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final TextView allianceText = findViewById(R.id.alliance_text);
        final TextView toptext = findViewById(R.id.toptext);
        final TextView bottomtext = findViewById(R.id.bottomText);
        final TextView toptext2 = findViewById(R.id.topText2);
        final TextView bottomtext2 = findViewById(R.id.bottomText2);
        final TextView missedtext = findViewById(R.id.missedShots);
        //EditTexts
        final EditText name_input = findViewById(R.id.name_input);
        final EditText team_input = findViewById(R.id.team_input);
        final EditText round_input = findViewById(R.id.round_input);
        final EditText notes = findViewById(R.id.notes);
        final TextView auto_upper_text = findViewById(R.id.auto_upper_text);
        final TextView auto_lower_text = findViewById(R.id.auto_lower_text);
        final TextView missedShotsText = findViewById(R.id.missedShotsText);
        final TextView teleop_upper_text = findViewById(R.id.teleop_upper_text);
        final TextView shotDistanceText = findViewById(R.id.shotDistanceText);
        final TextView teleop_lower_text = findViewById(R.id.teleop_lower_text);
        data_submitted.setVisibility(View.INVISIBLE);
        //Buttons
        //final Button pregame_open_button = findViewById(R.id.pregame_open_button);
        //final Button pregame_close_button = findViewById(R.id.pregame_close_button);
        final Button Blue_Alliance = findViewById(R.id.Blue_Alliance);
        final CheckBox teamAutofill = findViewById(R.id.autoFill);
        final Button sameScouter = findViewById(R.id.sameScouter);
        final Button Red_Alliance = findViewById(R.id.Red_Alliance);
        final Button Pregame_Box = findViewById(R.id.Pregame_Box);
        final Button pregame_close = findViewById(R.id.pregame_close);
        final Button Endgame_Box = findViewById(R.id.Endgame_Box);
        final Button no_show = findViewById(R.id.no_show);
        final Button submit = findViewById(R.id.submit);
        final Button devMode = findViewById(R.id.devMode);
        final ImageView missedShotsPositive = findViewById(R.id.missedShotsPosititve);
        final ImageView missedShotsMinus = findViewById(R.id.missedShotsMinus);
        final ImageView auto_upper_plus = findViewById(R.id.auto_upper_plus);
        final ImageView auto_upper_minus = findViewById(R.id.auto_upper_minus);
        final ImageView auto_lower_plus = findViewById(R.id.auto_lower_plus);
        final ImageView auto_lower_minus = findViewById(R.id.auto_lower_minus);
        final ImageView teleop_upper_plus = findViewById(R.id.teleop_upper_plus);
        final ImageView teleop_upper_minus = findViewById(R.id.teleop_upper_minus);
        final ImageView teleop_lower_plus = findViewById(R.id.teleop_lower_plus);
        final ImageView teleop_lower_minus = findViewById(R.id.teleop_lower_minus);
        final Switch auto_no_auto = findViewById(R.id.noAutoSwitch);
        final Switch autoMovement = findViewById(R.id.autoMovementSwitch);
        final Switch robotError = findViewById(R.id.robotErrors);
        round_input.setText(String.valueOf(roundfill));
        Drawable textBackground = round_input.getBackground();
        Drawable nameBackground = name_input.getBackground();
        if (roundfill==1) {sameScouter.setVisibility(View.GONE);}
        //No Show
        Endgame.setVisibility(View.INVISIBLE);
        Pregame.bringToFront();
        final DialogInterface.OnClickListener NoShowDialog = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE: //If the yes button is clicked for no show, this executes
                    allianceText.setBackgroundColor(Color.TRANSPARENT);
                    round_input.setBackground(textBackground);
                    team_input.setBackground(textBackground);
                    name_input.setBackground(nameBackground);
                    data_submitted.setImageResource(R.drawable.check);
                    data_submitted.setVisibility(View.VISIBLE);
                    ds_cooldown = 150;
                    roundfill = Integer.parseInt(round_input.getText().toString());
                    roundfill ++;
                    teleopLowerScore = 0;
                    teleopUpperScore = 0;
                    autoLowerScore = 0;
                    autoUpperScore = 0;
                    missedScore = 0;
                    scouterName = name_input.getText().toString();
                    notes.setText("");
                    name_input.setText("");
                    round_input.setText(String.valueOf(roundfill));
                    team_input.setText("");
                    endgame_results.setSelection(0);
                    speed.setSelection(0);
                    teamAutofill.setChecked(false);
                    climbResult.setSelection(0);
                    violations.setSelection(0);
                    shotDistance.setSelection(0);
                    auto_no_auto.setChecked(false);
                    autoMovement.setChecked(false);
                    robotError.setChecked(false);
                    teleop_upper_text.setText("0");
                    teleop_lower_text.setText("0");
                    auto_upper_text.setText("0");
                    auto_lower_text.setText("0");
                    missedShotsText.setText("0");
                    if (roundfill>1) {sameScouter.setVisibility(View.VISIBLE);}
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
                        Log.e("Exception", "File write failed: " + e);
                    }
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };
        //This is the gray box that allows for external box clicks from regame and endgame to close menus
        Gray_Box.setBackgroundColor(Color.argb(127,240,240,240));
        no_show.setOnClickListener(v -> {
            String submitError = "";
            //Special handling
            if (alliance.equals("none")) {
                submitError += " No Alliance,";
            }
            if (name_input.getText().toString().equals("")) {
                submitError += " No Name,";
            }
            if (team_input.getText().toString().equals("")) {
                submitError += " No Team#,";
            }
            if (round_input.getText().toString().equals("")) {
                submitError += " No Round#,";
            }
            if (!submitError.equals("")) {
                submitError = submitError.substring(0, submitError.length() - 1) + ".";
            }
            if (!(submitError.equals(""))) {
                Toast.makeText(getApplicationContext(), "Submit Error:" + submitError, Toast.LENGTH_LONG).show();
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
        //This sets the scouters name from the previous match. It only appears above match 2
        sameScouter.setOnClickListener(v-> {
            name_input.setText(scouterName);
        });
        //The great while loop (100 times/sec)
        Runnable myRunnable = () -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {}
                //This is just the big check or X if people submit data.
                data_submitted.post(() -> {
                    //data_submitted stuff
                    if (ds_cooldown > 0) {
                        ds_cooldown--;
                    }
                    if (ds_cooldown == 0) {
                        data_submitted.setVisibility(View.GONE);
                    }
                });
            }
        };
teamAutofill.setOnClickListener(v -> {
    if (teamAutofill.isChecked()) {
        String newTeam;
        try {
            roundfill = Integer.parseInt(round_input.getText().toString());
            newTeam = getTeams().substring(
                    getTeams().indexOf("." + roundfill + ":") + 1 + ("." + roundfill).length(), //Start
                    getTeams().substring(getTeams().indexOf("." + roundfill + ":")).indexOf("\n") + getTeams().indexOf("." + roundfill + ":") //End
            );
        } catch (Exception e) {
            newTeam = "";
            Log.e("log", e.toString());
        }
        team_input.setText(newTeam);
    }
    if (!teamAutofill.isChecked()) {
        team_input.setText("");
    }
                });
        //These lines are all the auto-mode validation. This makes sure no auto works with the other auto inputs
        auto_no_auto.setOnClickListener(v -> {
            if (auto_no_auto.isChecked()) {
                autoMovement.setChecked(false);
                autoMovement.setEnabled(false);
                auto_lower_text.setEnabled(false);
                auto_upper_text.setEnabled(false);
                auto_upper_minus.setEnabled(false);
                auto_upper_plus.setEnabled(false);
                auto_lower_minus.setEnabled(false);
                auto_lower_plus.setEnabled(false);
            }
            if (!auto_no_auto.isChecked()) {
                autoMovement.setEnabled(true);
                auto_lower_text.setEnabled(true);
                auto_upper_text.setEnabled(true);
                auto_upper_minus.setEnabled(true);
                auto_upper_plus.setEnabled(true);
                auto_lower_minus.setEnabled(true);
                auto_lower_plus.setEnabled(true);
            }
        });
        //This function prevents the score from going below 0 and above 99
        Thread myThread = new Thread(myRunnable);
        myThread.start();
        auto_upper_plus.setOnClickListener(v -> {
            if (autoUpperScore < 99) {
                autoUpperScore++;
            }
            auto_upper_text.setText(Integer.toString(autoUpperScore));
        });
        auto_upper_minus.setOnClickListener(v -> {
            if (autoUpperScore > 0) {
                autoUpperScore--;
            }
            auto_upper_text.setText(Integer.toString(autoUpperScore));
        });
        auto_lower_plus.setOnClickListener(v -> {
            if (autoLowerScore < 99) {
                autoLowerScore++;
            }
            auto_lower_text.setText(Integer.toString(autoLowerScore));
        });
        auto_lower_minus.setOnClickListener(v -> {
            if (autoLowerScore > 0) {
                autoLowerScore--;
            }
            auto_lower_text.setText(Integer.toString(autoLowerScore));
        });
        teleop_upper_plus.setOnClickListener(v -> {
            if (teleopUpperScore < 99) {
                teleopUpperScore++;
            }
            teleop_upper_text.setText(Integer.toString(teleopUpperScore));
        });
        teleop_upper_minus.setOnClickListener(v -> {
            if (teleopUpperScore > 0) {
                teleopUpperScore--;
            }
            teleop_upper_text.setText(Integer.toString(teleopUpperScore));
        });
        teleop_lower_plus.setOnClickListener(v -> {
            if (teleopLowerScore < 99) {
                teleopLowerScore++;
            }
            teleop_lower_text.setText(Integer.toString(teleopLowerScore));
        });
        teleop_lower_minus.setOnClickListener(v -> {
            if (teleopLowerScore > 0) {
                teleopLowerScore--;
            }
            teleop_lower_text.setText(Integer.toString(teleopLowerScore));
        });
        missedShotsPositive.setOnClickListener(v -> {
            if (missedScore < 99) {
                missedScore++;
            }
            missedShotsText.setText(Integer.toString(missedScore));
        });
        missedShotsMinus.setOnClickListener(v -> {
            if (missedScore > 0) {
                missedScore--;
            }
            missedShotsText.setText(Integer.toString(missedScore));
        });
        //Enter Dev Mode. Only necessary if something is broke
        devMode.setOnClickListener(v-> {
            if (alliance == "blue") {
                Background.setBackgroundResource(R.drawable.bluedev);
                auto_lower_text.setTextColor(Color.WHITE);
                auto_upper_text.setTextColor(Color.WHITE);
                auto_no_auto.setTextColor(Color.WHITE);
                autoMovement.setTextColor(Color.WHITE);
                toptext.setTextColor(Color.WHITE);
                toptext2.setTextColor(Color.WHITE);
                missedtext.setTextColor(Color.WHITE);
                bottomtext.setTextColor(Color.WHITE);
                bottomtext2.setTextColor(Color.WHITE);
                teleop_lower_text.setTextColor(Color.WHITE);
                teleop_upper_text.setTextColor(Color.WHITE);
                missedShotsText.setTextColor(Color.WHITE);
                shotDistanceText.setTextColor(Color.WHITE);
            }
            if (alliance == "red") {
                Background.setBackgroundResource(R.drawable.reddev);
                auto_lower_text.setTextColor(Color.WHITE);
                auto_upper_text.setTextColor(Color.WHITE);
                auto_no_auto.setTextColor(Color.WHITE);
                autoMovement.setTextColor(Color.WHITE);
                toptext.setTextColor(Color.WHITE);
                toptext2.setTextColor(Color.WHITE);
                missedtext.setTextColor(Color.WHITE);
                bottomtext.setTextColor(Color.WHITE);
                bottomtext2.setTextColor(Color.WHITE);
                teleop_lower_text.setTextColor(Color.WHITE);
                teleop_upper_text.setTextColor(Color.WHITE);
                missedShotsText.setTextColor(Color.WHITE);
                shotDistanceText.setTextColor(Color.WHITE);
            }
        });
        //This is validation to make sure that the fields have been filled before pregame is closed
        pregame_close.setOnClickListener(view -> {
            String closeError = "";
            if (team_input.getText().toString().equals("")) {
                closeError += " No Team,";
                team_input.setBackgroundColor(Color.argb(255, 255, 255, 0));
            }
            if (round_input.getText().toString().equals("")) {
                closeError += " No Match,";
                round_input.setBackgroundColor(Color.argb(255, 255, 255, 0));
            }
            if (alliance == "none") { closeError += " No Alliance,"; allianceText.setBackgroundColor(Color.argb(255,255,255,0));}
            if (name_input.getText().toString().equals("")) { closeError += " No Name,"; name_input.setBackgroundColor(Color.argb(255,255,255,0));}
            if (!closeError.equals("")) { closeError = closeError.substring(0,closeError.length()-1)+"."; }
            if (!(closeError.equals(""))) {
                Toast.makeText(getApplicationContext(), "Submit Error:"+closeError, Toast.LENGTH_LONG).show();
                data_submitted.setVisibility(View.VISIBLE);
                data_submitted.setImageResource(R.drawable.x);
                ds_cooldown = 150;
            } else {
                Pregame.setVisibility(View.INVISIBLE);
                Gray_Box.setVisibility(View.INVISIBLE);
                allianceText.setBackgroundColor(Color.TRANSPARENT);
                round_input.setBackground(textBackground);
                team_input.setBackground(textBackground);
                name_input.setBackground(nameBackground);
            }
        });
        //This is the gray box that closes the endgame box
        Gray_Box.setOnClickListener(View -> {
            String closeError = "";
            if (team_input.getText().toString().equals("")) {
                closeError += " No Team,";
                team_input.setBackgroundColor(Color.argb(255, 255, 255, 0));
            }
            if (round_input.getText().toString().equals("")) {
                closeError += " No Match,";
                round_input.setBackgroundColor(Color.argb(255, 255, 255, 0));
            }
            if (alliance == "none") { closeError += " No Alliance,"; allianceText.setBackgroundColor(Color.argb(255,255,255,0));}
            if (name_input.getText().toString().equals("")) { closeError += " No Name,"; name_input.setBackgroundColor(Color.argb(255,255,255,0));}
            if (!closeError.equals("")) { closeError = closeError.substring(0,closeError.length()-1)+"."; }

            if (!(closeError.equals(""))) {
                Toast.makeText(getApplicationContext(), "Submit Error:"+closeError, Toast.LENGTH_LONG).show();

                data_submitted.setVisibility(View.VISIBLE);
                data_submitted.setImageResource(R.drawable.x);
                ds_cooldown = 150;
            } else {
                Pregame.setVisibility(View.INVISIBLE);
                Gray_Box.setVisibility(View.INVISIBLE);
                allianceText.setBackgroundColor(Color.TRANSPARENT);
                round_input.setBackground(textBackground);
                team_input.setBackground(textBackground);
                name_input.setBackground(nameBackground);
            }
            Endgame.setVisibility(View.INVISIBLE);
         });
        //These lines are the special function toggles.
        //The Pregame_box is a dev way to open and close pregame without filling out fields
        //The Endgame_box is a operator way to open and close endgame.
        Endgame.setVisibility(View.INVISIBLE);
        Pregame_Box.setOnClickListener(view -> {
            if (Pregame.getVisibility() == View.INVISIBLE) {
                Endgame.setVisibility(View.INVISIBLE);
                Pregame.setVisibility(View.VISIBLE);
                Gray_Box.setVisibility(View.VISIBLE);
                Pregame.bringToFront();
            } else if (Pregame.getVisibility() == View.VISIBLE) {
                Pregame.setVisibility(View.INVISIBLE);
                Endgame.setVisibility(View.INVISIBLE);
                Gray_Box.setVisibility(View.INVISIBLE);
            }
        });
        Endgame_Box.setOnClickListener(view -> {
            if (Endgame.getVisibility() == View.VISIBLE) {
                Pregame.setVisibility(View.INVISIBLE);
                Endgame.setVisibility(View.INVISIBLE);
                Gray_Box.setVisibility(View.INVISIBLE);
            } else if (Endgame.getVisibility() == View.INVISIBLE) {
                Endgame.setVisibility(View.VISIBLE);
                Pregame.setVisibility(View.INVISIBLE);
                Gray_Box.setVisibility(View.VISIBLE);
                Endgame.bringToFront();
            }
        });
        //This sets the alliance blue and it sets the background colour blue.
        Blue_Alliance.setOnClickListener(v -> {
            Background.setBackgroundResource(R.drawable.blueappbackground);
            Pregame.setBackgroundColor(Color.argb(255, 127, 127, 247));
            Endgame.setBackgroundColor(Color.argb(255, 127, 127, 247));
            auto_lower_text.setTextColor(Color.BLACK);
            auto_upper_text.setTextColor(Color.BLACK);
            auto_no_auto.setTextColor(Color.BLACK);
            autoMovement.setTextColor(Color.BLACK);
            toptext.setTextColor(Color.BLACK);
            toptext2.setTextColor(Color.BLACK);
            missedtext.setTextColor(Color.BLACK);
            bottomtext.setTextColor(Color.BLACK);
            bottomtext2.setTextColor(Color.BLACK);
            teleop_lower_text.setTextColor(Color.BLACK);
            teleop_upper_text.setTextColor(Color.BLACK);
            missedShotsText.setTextColor(Color.BLACK);
            shotDistanceText.setTextColor(Color.BLACK);
            alliance = "blue";
        });
        //This sets the alliance red and sets the background color red.
        Red_Alliance.setOnClickListener(v -> {
            Background.setBackgroundResource(R.drawable.redappbackground);
            Pregame.setBackgroundColor(Color.argb(255, 247, 127, 127));
            Endgame.setBackgroundColor(Color.argb(255, 247, 127, 127));
            auto_lower_text.setTextColor(Color.BLACK);
            auto_upper_text.setTextColor(Color.BLACK);
            auto_no_auto.setTextColor(Color.BLACK);
            autoMovement.setTextColor(Color.BLACK);
            toptext.setTextColor(Color.BLACK);
            toptext2.setTextColor(Color.BLACK);
            missedtext.setTextColor(Color.BLACK);
            bottomtext.setTextColor(Color.BLACK);
            bottomtext2.setTextColor(Color.BLACK);
            teleop_lower_text.setTextColor(Color.BLACK);
            teleop_upper_text.setTextColor(Color.BLACK);
            missedShotsText.setTextColor(Color.BLACK);
            shotDistanceText.setTextColor(Color.BLACK);
            alliance = "red";
        });
        //All of these OnTouchListeners are to reset the color of the boxes when they turn yellow from a lack of inputs.
        climbResult.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!(climbResult.getSelectedItem() == "No Input")) {
                    climbResult.setBackgroundResource(R.drawable.spinnerbackground);
                }
                return false;
            }
        });
        violations.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!(violations.getSelectedItem() == "No Input")) {
                    violations.setBackgroundResource(R.drawable.spinnerbackground);
                }
                return false;
            }
        });
        endgame_results.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!(endgame_results.getSelectedItem() == "No Input")) {
                    endgame_results.setBackgroundResource(R.drawable.spinnerbackground);
                }
                return false;
            }
        });
        speed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!(speed.getSelectedItem() == "No Input")) {
                    speed.setBackgroundResource(R.drawable.spinnerbackground);
                }
                return false;
            }
        });
        shotDistance.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!(shotDistance.getSelectedItem() == "No Input")) {
                    shotDistance.setBackgroundResource(R.drawable.spinnerbackground);
                }
                return false;
            }
        });
        //All of these OnFocusChanges are to get the keyboard out of the way when inputting stuff is finished
        name_input.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { hideKeyboard(v); }
        });
        round_input.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { hideKeyboard(v); }
        });
        team_input.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { hideKeyboard(v); }
        });
        notes.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { hideKeyboard(v); }
        });
        //This is all of the garbage that the submit button does.
        submit.setOnClickListener(v -> {
            String submitError = "";
            SimpleDateFormat time = new SimpleDateFormat("dd-HHmmss", Locale.getDefault());
            int team;
            int round;
            //Special handling
            if (auto_no_auto.isChecked()) {
                autoUpperScore=0;
                autoLowerScore=0;
                auto_lower_text.setText("0");
                auto_upper_text.setText("0");
                autoMovement.setChecked(false);
            }
            if (team_input.getText().toString().equals("")) { team = -1; }
            else { team = Integer.parseInt(team_input.getText().toString()); }
            if (round_input.getText().toString().equals("")) { round = -1; }
            else { round = Integer.parseInt(round_input.getText().toString()); }
            //These are telling the toast what to put in the error field, and it changes the color to yellow.
            if (alliance == "none") { submitError += " No Alliance,"; allianceText.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.INVISIBLE); Pregame.setVisibility(View.VISIBLE);}
            if (name_input.getText().toString().equals("")) { submitError += " No Name,"; name_input.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.INVISIBLE); Pregame.setVisibility(View.VISIBLE);}
            if (speed.getSelectedItem().toString().equals("No Input")) { submitError += " No Speed,"; speed.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.VISIBLE); Pregame.setVisibility(View.INVISIBLE);}
            if (endgame_results.getSelectedItem().toString().equals("No Input")) { submitError += " No Results,"; endgame_results.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.VISIBLE); Pregame.setVisibility(View.INVISIBLE);}
            if (violations.getSelectedItem().toString().equals("No Input")) { submitError += " No Violations,"; violations.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.VISIBLE); Pregame.setVisibility(View.INVISIBLE);}
            if (climbResult.getSelectedItem().toString().equals("No Input")) { submitError += " No Climb Result,"; climbResult.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.VISIBLE); Pregame.setVisibility(View.INVISIBLE);}
            if (shotDistance.getSelectedItem().toString().equals("No Input")) { submitError += " No Shot Distance,"; shotDistance.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.INVISIBLE); Pregame.setVisibility(View.INVISIBLE); Gray_Box.setVisibility(View.INVISIBLE);}
            if (team_input.getText().toString().equals("")) { submitError += " No Team#,"; team_input.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.INVISIBLE); Pregame.setVisibility(View.VISIBLE);}
            if (round_input.getText().toString().equals("")) { submitError += " No Round#,"; round_input.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.INVISIBLE); Pregame.setVisibility(View.VISIBLE); }
            if (!submitError.equals("")) { submitError = submitError.substring(0,submitError.length()-1)+"."; }
            //If any of the above are true, the thing returns a submit error.
            if (!(submitError.equals(""))) {
                Toast.makeText(getApplicationContext(), "Submit Error:"+submitError, Toast.LENGTH_LONG).show();
                //Place an X if incorrect
                data_submitted.setVisibility(View.VISIBLE);
                data_submitted.setImageResource(R.drawable.x);
                ds_cooldown = 150;
            } else {
                //This is what happens whenever all is correct
                data_submitted.setVisibility(View.VISIBLE);
                data_submitted.setImageResource(R.drawable.check);
                team_input.setBackground(textBackground);
                round_input.setBackground(textBackground);
                shotDistance.setBackgroundResource(R.drawable.spinnerbackground);
                name_input.setBackground(nameBackground);
                allianceText.setBackgroundResource(Color.TRANSPARENT);
                climbResult.setBackgroundResource(R.drawable.spinnerbackground);
                violations.setBackgroundResource(R.drawable.spinnerbackground);
                endgame_results.setBackgroundResource(R.drawable.spinnerbackground);
                speed.setBackgroundResource(R.drawable.spinnerbackground);
                ds_cooldown = 150; //Makes the check mark appear
                //Save data for transfer
                File dir = getDataDirectory();
                //This creates a file
                try {
                    File myFile = new File(dir, team + "_" + round + "_" + time.format(new Date()) + ".txt");
                    FileOutputStream fOut = new FileOutputStream(myFile, true);
                    PrintWriter myOutWriter = new PrintWriter(new OutputStreamWriter(fOut));
                    //This prints all of the lines into the file for transfer.
                    myOutWriter.println("Scouter: "+name_input.getText());
                    myOutWriter.println("Team: "+team);
                    myOutWriter.println("Timestamp: "+time.format(new Date()));
                    myOutWriter.println("Match: "+round);
                    myOutWriter.println("Alliance: "+alliance);
                    myOutWriter.println("Speed: "+speed.getSelectedItem().toString());
                    myOutWriter.println("Robot Errors: "+robotError.isChecked());
                    myOutWriter.println("Auto Top Score: "+autoUpperScore);
                    myOutWriter.println("Auto Bottom Score: "+autoLowerScore);
                    myOutWriter.println("No Auto: "+auto_no_auto.isChecked());
                    myOutWriter.println("Auto Movement: "+autoMovement.isChecked());
                    myOutWriter.println("Teleop Top Score: "+teleopUpperScore);
                    myOutWriter.println("Teleop Bottom Score: "+teleopLowerScore);
                    myOutWriter.println("Missed Shots: "+missedScore);
                    myOutWriter.println("Shot Distance: "+shotDistance.getSelectedItem());
                    myOutWriter.println("Endgame: "+climbResult.getSelectedItem());
                    myOutWriter.println("Results: "+endgame_results.getSelectedItem());
                    myOutWriter.println("Violations: "+violations.getSelectedItem());
                    myOutWriter.println("Notes: "+notes.getText());
                    myOutWriter.flush();
                    myOutWriter.close();
                    fOut.close();
                    Toast.makeText(getApplicationContext(), "Data Submitted!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    //If anything goes wrong, it throws an error instead of crashing
                    Toast.makeText(getApplicationContext(), "Data Submission Failed! (Tell scouting)", Toast.LENGTH_SHORT).show();
                    Log.e("Exception", "File write failed: " + e.toString());
                }
                //Reset Everything
                roundfill = Integer.parseInt(round_input.getText().toString());
                roundfill ++;
                teleopLowerScore = 0;
                teleopUpperScore = 0;
                autoLowerScore = 0;
                autoUpperScore = 0;
                missedScore = 0;
                autoMovement.setEnabled(true);
                auto_lower_text.setEnabled(true);
                auto_upper_text.setEnabled(true);
                auto_upper_minus.setEnabled(true);
                auto_upper_plus.setEnabled(true);
                auto_lower_minus.setEnabled(true);
                auto_lower_plus.setEnabled(true);
                teamAutofill.setChecked(false);
                notes.setText("");
                scouterName = name_input.getText().toString();
                name_input.setText("");
                round_input.setText(String.valueOf(roundfill));
                team_input.setText("");
                endgame_results.setSelection(0);
                speed.setSelection(0);
                climbResult.setSelection(0);
                violations.setSelection(0);
                shotDistance.setSelection(0);
                auto_no_auto.setChecked(false);
                autoMovement.setChecked(false);
                robotError.setChecked(false);
                teleop_upper_text.setText("0");
                teleop_lower_text.setText("0");
                auto_upper_text.setText("0");
                auto_lower_text.setText("0");
                missedShotsText.setText("0");
                Endgame.setVisibility(View.INVISIBLE);
                Pregame.setVisibility(View.VISIBLE);
                if (roundfill>1) {sameScouter.setVisibility(View.VISIBLE);}
            }
        });
    }
    private File getDataDirectory() {
        File directory = Environment.getExternalStorageDirectory();
        File myDir = new File(directory + "/ScoutingData");
        myDir.mkdirs();
        return myDir;
    }
    @Override
    public void onBackPressed() {}
    private String getTeams() {
        String text = "";
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File file;
            file = new File(sdcard + "/Documents/ScoutingTeams.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text += line + "\n";
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("log", text);
        return text;
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}