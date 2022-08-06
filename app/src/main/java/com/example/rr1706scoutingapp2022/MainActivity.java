package com.example.rr1706scoutingapp2022;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
        int ds_cooldown = 0; //ds_cooldown is the cool down for the data_submitted animation
        int team;
        int centisecondsdefending = 0 ;
        int secondsdefending = 0 ;
        int minutesdefending = 0 ;
        int centisecondsclimb = 0 ;
        int secondsclimb = 0 ;
        int minutesclimb = 0 ;
        int milisecondsclimbraw = 0;
        int dev = 0;
        int apple = 0;
        String scouterName;
        int round;
        int roundfill = 1;
        int missedScore;
        int autoLowerScore;
        int autoUpperScore;
        int teleopUpperScore;
        int climbtimerreset;
        int teleopLowerScore;
        int defenseUpperScore;
        int timerReset;
        int defenseLowerScore;
        int milisecondsdefending = 0;
        int milisecondsdefendingraw = 0;
        int milisecondsclimb = 0;
        int defensetimer = 0;
        int climbtimer = 0;
        String alliance = "none";
        String defending1;
        String defending2;
        String defending3;
        String tabletName;
        int tabletnumber;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Setup stuff
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Spinners
        final Spinner endgame_results = findViewById(R.id.endgameResults);
        final Spinner climbResult = findViewById(R.id.endgameClimb);
        //Constraints
        final ConstraintLayout Background = findViewById(R.id.Background);
        final ConstraintLayout Pregame = findViewById(R.id.Pregame);
        final ConstraintLayout Endgame = findViewById(R.id.Endgame);
        final ConstraintLayout Defense = findViewById(R.id.Defense);
        //Alerts
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //EditTexts
        final EditText name_input = findViewById(R.id.name_input);
        final EditText team_input = findViewById(R.id.team_input);
        final EditText round_input = findViewById(R.id.round_input);
        final EditText notes = findViewById(R.id.notes);
        //TextViews
        final TextView auto_upper_text = findViewById(R.id.auto_upper_text);
        final TextView timerClear = findViewById(R.id.clearTimer);
        final TextView climbTimerClear = findViewById(R.id.climbTimerReset);
        final TextView climbTimer = findViewById(R.id.climbTimer);
        final TextView attemptedAutoText = findViewById(R.id.autoAttempted);
        final TextView auto_lower_text = findViewById(R.id.auto_lower_text);
        final TextView missedShotsText = findViewById(R.id.missedShotsText);
        final TextView teleop_upper_text = findViewById(R.id.teleop_upper_text);
        final TextView teleop_lower_text = findViewById(R.id.teleop_lower_text);
        final TextView allianceText = findViewById(R.id.alliance_text);
        final TextView toptext = findViewById(R.id.toptext);
        final TextView bottomtext = findViewById(R.id.bottomText);
        final TextView toptext2 = findViewById(R.id.topText2);
        final TextView bottomtext2 = findViewById(R.id.bottomText2);
        final TextView missedtext = findViewById(R.id.missedShots);
        final TextView defenseTimer = findViewById(R.id.defenseTimer);
        final TextView defenseNumberText = findViewById(R.id.InputTeamDefenseT);
        final EditText DefenseNumber = findViewById(R.id.InputTeamDefense);
        //Buttons
        final Button Blue_Alliance = findViewById(R.id.Blue_Alliance);
        final Button sameScouter = findViewById(R.id.sameScouter);
        final Button Red_Alliance = findViewById(R.id.Red_Alliance);
        final Button Gray_Box = findViewById(R.id.grayBox);
        final Button Pregame_Box = findViewById(R.id.Pregame_Box);
        final Button Defense_Box = findViewById(R.id.Defense_Box);
        final Button pregame_close = findViewById(R.id.pregame_close);
        final Button Endgame_Box = findViewById(R.id.Endgame_Box);
        final Button no_show = findViewById(R.id.no_show);
        final Button submit = findViewById(R.id.submit);
        final Button devMode = findViewById(R.id.devMode);
        final Button startDefenseTimer = findViewById(R.id.startDefendingTimer);
        final Button closeDefense = findViewById(R.id.closeDefense);
        final Button climbTimerStart = findViewById(R.id.climbTimerStart);
        //Images
        final ImageView autoScoreAttempt = findViewById(R.id.autoScoreAttempt);
        final ImageView auto_no_autoColor = findViewById(R.id.auto_no_autocolor);
        final ImageView seekbarcover = findViewById(R.id.whiteseekbar);
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
        final ImageView data_submitted = findViewById(R.id.data_submitted);
        //Switches
        final Switch auto_no_auto = findViewById(R.id.noAutoSwitch);
        final Switch playedDefense = findViewById(R.id.playedDefense);
        final Switch robotError = findViewById(R.id.robotErrors);
        //Seekbars
        final SeekBar autoAttempted = findViewById(R.id.autoAttemptedBar);
        //Image Buttons
        final ImageButton rrlogo = findViewById(R.id.rrlogobtn);

        //Checkboxes
        final CheckBox teamAutofill = findViewById(R.id.autoFill);
        final CheckBox defended1 = findViewById(R.id.defendingTeam1);
        final CheckBox defended2 = findViewById(R.id.defendingTeam2);
        final CheckBox defended3 = findViewById(R.id.defendingTeam3);

        //Random Initial Start Things
        if (getTeams() != "") {
            teamAutofill.setChecked(true);
        }
        round_input.setText(String.valueOf(roundfill));
        Drawable textBackground = round_input.getBackground();
        Drawable nameBackground = name_input.getBackground();
        data_submitted.setVisibility(View.INVISIBLE);
        if (roundfill == 1) {
            sameScouter.setVisibility(View.GONE);
        }

        String[] splitstrArray = null;
        String[] strArray = null;
        strArray = getTeams().split("\n");
        splitstrArray = strArray[roundfill - 1].split(",");


        tabletName = Settings.Secure.getString(getContentResolver(), "bluetooth_name");
        if (tabletName.equals("1706's 1st Fire")) {
            tabletnumber = 4;
        } else if (tabletName.equals("1706's 2nd Fire")) {
            tabletnumber = 5;
        } else if (tabletName.equals("1706's 3rd Fire")) {
            tabletnumber = 6;
        } else if (tabletName.equals("1706's 4th Fire")) {
            tabletnumber = 1;
        } else if (tabletName.equals("1706's 5th Fire")) {
            tabletnumber = 2;
        } else if (tabletName.equals("1706's 6th Fire")) {
            tabletnumber = 3;
        }
        int tabletnumbercomp = tabletnumber - 1;
        if (tabletnumber <= 3) {
            dev = 0;
            Background.setBackgroundResource(R.drawable.redappbackground);
            Pregame.setBackgroundColor(Color.argb(255, 247, 127, 127));
            Endgame.setBackgroundColor(Color.argb(255, 247, 127, 127));
            Defense.setBackgroundColor(Color.argb(255, 247, 127, 127));
            auto_lower_text.setTextColor(Color.BLACK);
            auto_upper_text.setTextColor(Color.BLACK);
            auto_no_auto.setTextColor(Color.BLACK);
            autoAttempted.setBackgroundColor(Color.TRANSPARENT);
            autoScoreAttempt.setBackgroundResource(R.drawable.seekbar);
            attemptedAutoText.setTextColor(Color.BLACK);
            toptext.setTextColor(Color.BLACK);
            toptext2.setTextColor(Color.BLACK);
            missedtext.setTextColor(Color.BLACK);
            bottomtext.setTextColor(Color.BLACK);
            bottomtext2.setTextColor(Color.BLACK);
            teleop_lower_text.setTextColor(Color.BLACK);
            seekbarcover.setImageResource(Color.TRANSPARENT);
            teleop_upper_text.setTextColor(Color.BLACK);
            missedShotsText.setTextColor(Color.BLACK);
            alliance = "red";
        }
        if (tabletnumber >= 4) {
            dev = 0;
            Background.setBackgroundResource(R.drawable.blueappbackground);
            Pregame.setBackgroundColor(Color.argb(255, 127, 127, 247));
            Endgame.setBackgroundColor(Color.argb(255, 127, 127, 247));
            Defense.setBackgroundColor(Color.argb(255, 127, 127, 247));
            auto_lower_text.setTextColor(Color.BLACK);
            auto_upper_text.setTextColor(Color.BLACK);
            auto_no_auto.setTextColor(Color.BLACK);
            toptext.setTextColor(Color.BLACK);
            toptext2.setTextColor(Color.BLACK);
            missedtext.setTextColor(Color.BLACK);
            bottomtext.setTextColor(Color.BLACK);
            bottomtext2.setTextColor(Color.BLACK);
            teleop_lower_text.setTextColor(Color.BLACK);
            teleop_upper_text.setTextColor(Color.BLACK);
            missedShotsText.setTextColor(Color.BLACK);
            autoAttempted.setBackgroundColor(Color.TRANSPARENT);
            autoScoreAttempt.setBackgroundResource(R.drawable.seekbar);
            seekbarcover.setImageResource(Color.TRANSPARENT);
            attemptedAutoText.setTextColor(Color.BLACK);
            alliance = "blue";
        }

        if (getTeams() != "") {
            if (tabletnumber >= 4) {
                defended1.setText(splitstrArray[0]);
                defended2.setText(splitstrArray[1]);
                defended3.setText(splitstrArray[2]);
            } else if (tabletnumber <= 3) {
                defended1.setText(splitstrArray[3]);
                defended2.setText(splitstrArray[4]);
                defended3.setText(splitstrArray[5]);
            }
        }
        //No Show
        Endgame.setVisibility(View.INVISIBLE);
        Pregame.bringToFront();
        String[] finalSplitstrArray1 = splitstrArray;
        final DialogInterface.OnClickListener NoShowDialog = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE: //If the yes button is clicked for no show, this executes
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
                    allianceText.setBackgroundColor(Color.TRANSPARENT);
                    round_input.setBackground(textBackground);
                    team_input.setBackground(textBackground);
                    name_input.setBackground(nameBackground);
                    data_submitted.setImageResource(R.drawable.check);
                    data_submitted.setVisibility(View.VISIBLE);
                    auto_lower_minus.setAlpha((float) 1);
                    auto_lower_plus.setAlpha((float) 1);
                    auto_lower_text.setAlpha((float) 1);
                    auto_upper_minus.setAlpha((float) 1);
                    auto_upper_plus.setAlpha((float) 1);
                    autoAttempted.setEnabled(true);
                    autoScoreAttempt.setAlpha((float) 1);
                    attemptedAutoText.setAlpha((float) 1);
                    auto_upper_text.setAlpha((float) 1);
                    toptext.setAlpha((float) 1);
                    bottomtext.setAlpha((float) 1);
                    ds_cooldown = 1500;
                    roundfill = Integer.parseInt(round_input.getText().toString());
                    roundfill++;
                    teleopLowerScore = 0;
                    teleopUpperScore = 0;
                    autoLowerScore = 0;
                    autoUpperScore = 0;
                    missedScore = 0;
                    autoAttempted.setProgress(0);
                    auto_no_autoColor.setBackgroundColor(Color.TRANSPARENT);
                    scouterName = name_input.getText().toString();
                    notes.setText("");
                    name_input.setText("");
                    round_input.setText(String.valueOf(roundfill));
                    team_input.setText("");
                    endgame_results.setSelection(0);
                    climbResult.setSelection(0);
                    auto_no_auto.setChecked(false);
                    robotError.setChecked(false);
                    teleop_upper_text.setText("0");
                    teleop_lower_text.setText("0");
                    defenseTimer.setText("00:00.00");
                    climbTimer.setText("00:00.00");
                    playedDefense.setChecked(false);
                    defensetimer = 0;
                    climbtimer = 0;
                    defenseLowerScore = 0;
                    defenseUpperScore = 0;
                    Defense.setVisibility(View.INVISIBLE);
                    startDefenseTimer.setText("Start Timer");
                    climbTimerStart.setText("Start Timer");
                    climbtimerreset = 0;
                    timerReset = 0;
                    milisecondsclimbraw = 0;
                    milisecondsclimb = 0;
                    milisecondsdefendingraw = 0;
                    milisecondsdefending = 0;
                    secondsclimb = 0;
                    secondsdefending = 0;
                    minutesclimb = 0;
                    minutesdefending = 0;
                    defended1.setChecked(false);
                    defended2.setChecked(false);
                    defended3.setChecked(false);
                    DefenseNumber.setText("");
                    auto_upper_text.setText("0");
                    auto_lower_text.setText("0");
                    missedShotsText.setText("0");
                    startDefenseTimer.setEnabled(false);
                    startDefenseTimer.setAlpha((float) 0.5);
                    if (roundfill > 1) {
                        sameScouter.setVisibility(View.VISIBLE);
                    }
                    if (teamAutofill.isChecked() && getTeams() != "") {
                        String[] tempIntArr = null;
                        String[] splittempIntArr = null;
                        tempIntArr = getTeams().split("\n");
                        roundfill = Integer.parseInt(round_input.getText().toString());
                        splittempIntArr = tempIntArr[roundfill - 1].split(",");
                        team_input.setText(splittempIntArr[tabletnumbercomp]);
                    }
                    if (!teamAutofill.isChecked()) {
                        team_input.setText("");
                    }
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };
        //This is the gray box that allows for external box clicks from regame and endgame to close menus
        Gray_Box.setBackgroundColor(Color.argb(180, 240, 240, 240));
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
                ds_cooldown = 1500;
            } else {
                builder.setMessage("Are you sure the team is a no show?")
                        .setPositiveButton("Yes", NoShowDialog)
                        .setNegativeButton("No", NoShowDialog)
                        .show();
            }
        });

        //This sets the scouters name from the previous match. It only appears above match 2
        sameScouter.setOnClickListener(v -> name_input.setText(scouterName));
        //The great while loop (100 times/sec)
        Runnable myRunnable = () -> {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
                //This is just the big check or X if people submit data.
                data_submitted.post(() -> {
                    //data_submitted stuff
                    if (ds_cooldown > 0) {
                        ds_cooldown--;
                    }
                    if (ds_cooldown == 0) {
                        data_submitted.setVisibility(View.GONE);
                        timerClear.setVisibility(View.GONE);
                        climbTimerClear.setVisibility(View.GONE);
                    }
                    //Defense Timer Starting
                    if (defensetimer == 1) {
                        milisecondsdefendingraw++;
                    }
                    if (milisecondsdefending >= 1000) {
                        secondsdefending++;
                        milisecondsdefending = 0;
                    }
                    if (secondsdefending >= 60) {
                        minutesdefending++;
                        secondsdefending = 0;
                    }
                    //Cases for if the number does not match the type of standard number format
                    if (defensetimer == 1) {
                        milisecondsdefending++;
                        if (secondsdefending < 10 && minutesdefending >= 10) {
                            defenseTimer.setText(Integer.toString(minutesdefending) + ":" + "0" + Integer.toString(secondsdefending) + "." + Integer.toString(milisecondsdefending));
                        }
                        if (minutesdefending < 10 && secondsdefending >= 10) {
                            defenseTimer.setText("0" + Integer.toString(minutesdefending) + ":" + Integer.toString(secondsdefending) + "." + Integer.toString(milisecondsdefending));
                        }
                        if (minutesdefending < 10 && secondsdefending < 10) {
                            defenseTimer.setText("0" + Integer.toString(minutesdefending) + ":" + "0" + Integer.toString(secondsdefending) + "." + Integer.toString(milisecondsdefending));
                        }
                        if (secondsdefending >= 10 && minutesdefending >= 10) {
                            defenseTimer.setText(Integer.toString(minutesdefending) + ":" + Integer.toString(secondsdefending) + "." + Integer.toString(milisecondsdefending));
                        }
                    }
                    //Starting the climb timer
                    if (climbtimer == 1) {
                        milisecondsclimbraw++;
                    }
                    if (milisecondsclimb >= 1000) {
                        secondsclimb++;
                        milisecondsclimb = 0;
                    }
                    if (secondsclimb >= 60) {
                        minutesclimb++;
                        secondsclimb = 0;
                    }
                    //Cases for if the number does not match the type of standard number format
                    if (climbtimer == 1) {
                        milisecondsclimb++;
                        if (secondsclimb >= 10 && minutesclimb >= 10) {
                            climbTimer.setText(Integer.toString(minutesclimb) + ":" + Integer.toString(secondsclimb) + "." + Integer.toString(milisecondsclimb));
                        }
                        if (secondsclimb < 10 && minutesclimb >= 10) {
                            climbTimer.setText(Integer.toString(minutesclimb) + ":" + "0" + Integer.toString(secondsclimb) + "." + Integer.toString(milisecondsclimb));
                        }
                        if (secondsclimb >= 10 && minutesclimb < 10) {
                            climbTimer.setText("0" + Integer.toString(minutesclimb) + ":" + Integer.toString(secondsclimb) + "." + Integer.toString(milisecondsclimb));
                        }
                        if (secondsclimb < 10 && minutesclimb < 10) {
                            climbTimer.setText("0" + Integer.toString(minutesclimb) + ":" + "0" + Integer.toString(secondsclimb) + "." + Integer.toString(milisecondsclimb));
                        }
                    }

                });
            }
        };
        //Code for the team Autofill Stuff
        if (teamAutofill.isChecked() && getTeams() != "") {
            String[] tempIntArr = null;
            String[] splittempIntArr = null;

            tempIntArr = getTeams().split("\n");
            roundfill = Integer.parseInt(round_input.getText().toString());
            splittempIntArr = tempIntArr[roundfill - 1].split(",");
            team_input.setText(splittempIntArr[tabletnumbercomp]);        //SPINNY BOI
            if (team_input.getText().toString().equals("1706")) {
                rrlogo.animate().rotation(2880f).setDuration(5000).start();
            }
        }


        String[] finalSplitstrArray2 = splitstrArray;
        teamAutofill.setOnClickListener(v -> {
            if (teamAutofill.isChecked() && getTeams() != "") {

                String[] tempIntArr = null;
                String[] splittempIntArr = null;

                tempIntArr = getTeams().split("\n");
                roundfill = Integer.parseInt(round_input.getText().toString());
                splittempIntArr = tempIntArr[roundfill - 1].split(",");
                team_input.setText(splittempIntArr[tabletnumbercomp]);        //SPINNY BOI
                if (team_input.getText().toString().equals("1706")) {
                    rrlogo.animate().rotation(2880f).setDuration(5000).start();
                }
            }
            if (!teamAutofill.isChecked()) {
                team_input.setText("");
            }
        });
        closeDefense.setOnClickListener(v -> {
            startDefenseTimer.setText("Start Timer");
            defensetimer = 0;
            Defense.setVisibility(View.INVISIBLE);
            Gray_Box.setVisibility(View.GONE);
        });
        if (getTeams().equals("")) {
            defenseNumberText.setVisibility(View.VISIBLE);
            DefenseNumber.setVisibility(View.VISIBLE);
        }
        if (getTeams() != "") {
            defenseNumberText.setVisibility(View.INVISIBLE);
            DefenseNumber.setVisibility(View.INVISIBLE);
        }

        startDefenseTimer.setEnabled(false);
        startDefenseTimer.setAlpha((float) 0.5);
        defended1.setEnabled(false);
        defended1.setAlpha((float) 0.5);
        defended2.setEnabled(false);
        defended2.setAlpha((float) 0.5);
        defended3.setEnabled(false);
        defended3.setAlpha((float) 0.5);
        DefenseNumber.setEnabled(false);
        DefenseNumber.setAlpha((float) 0.5);

        playedDefense.setOnClickListener(v -> {
            if (playedDefense.isChecked()) {
                startDefenseTimer.setEnabled(true);
                startDefenseTimer.setAlpha((float) 1);
                if (getTeams() != "") {
                    defended1.setEnabled(true);
                    defended1.setAlpha((float) 1);
                    defended2.setEnabled(true);
                    defended2.setAlpha((float) 1);
                    defended3.setEnabled(true);
                    defended3.setAlpha((float) 1);
                }
                defenseNumberText.setEnabled(true);
                defenseNumberText.setAlpha((float) 1);
                DefenseNumber.setEnabled(true);
                DefenseNumber.setAlpha((float) 1);
            }
            if (!playedDefense.isChecked()) {
                startDefenseTimer.setEnabled(false);
                startDefenseTimer.setAlpha((float) 0.5);
                if (getTeams() != "") {
                    defended1.setEnabled(false);
                    defended1.setAlpha((float) 0.5);
                    defended2.setEnabled(false);
                    defended2.setAlpha((float) 0.5);
                    defended3.setEnabled(false);
                    defended3.setAlpha((float) 0.5);
                }
                defenseNumberText.setEnabled(false);
                defenseNumberText.setAlpha((float) 0.5);
                DefenseNumber.setEnabled(false);
                DefenseNumber.setAlpha((float) 0.5);
            }
        });

        //These lines are all the auto-mode validation. This makes sure no auto works with the other auto inputs
        auto_no_auto.setOnClickListener(v -> {
            if (auto_no_auto.isChecked()) {
                auto_lower_text.setEnabled(false);
                auto_lower_text.setAlpha((float) 0.5);
                auto_upper_text.setEnabled(false);
                auto_upper_text.setAlpha((float) 0.5);
                auto_upper_minus.setEnabled(false);
                auto_upper_minus.setAlpha((float) 0.5);
                auto_upper_plus.setEnabled(false);
                auto_upper_plus.setAlpha((float) 0.5);
                auto_lower_minus.setEnabled(false);
                auto_lower_minus.setAlpha((float) 0.5);
                auto_lower_plus.setEnabled(false);
                auto_lower_plus.setAlpha((float) 0.5);
                toptext.setAlpha((float) 0.5);
                bottomtext.setAlpha((float) 0.5);
                autoAttempted.setEnabled(false);
                autoAttempted.setAlpha((float) 0.5);
                autoScoreAttempt.setAlpha((float) 0.5);
                attemptedAutoText.setAlpha((float) 0.5);
                if (autoLowerScore > 0 | autoAttempted.getProgress() > 0 | autoUpperScore > 0) {
                    auto_no_autoColor.setBackgroundColor(Color.YELLOW);
                }
            }
            if (!auto_no_auto.isChecked()) {
                auto_lower_text.setEnabled(true);
                auto_lower_text.setAlpha((float) 1);
                auto_upper_text.setEnabled(true);
                auto_upper_text.setAlpha((float) 1);
                auto_upper_minus.setEnabled(true);
                auto_upper_minus.setAlpha((float) 1);
                auto_upper_plus.setEnabled(true);
                auto_upper_plus.setAlpha((float) 1);
                auto_lower_minus.setEnabled(true);
                auto_lower_minus.setAlpha((float) 1);
                auto_lower_plus.setEnabled(true);
                auto_lower_plus.setAlpha((float) 1);
                toptext.setAlpha((float) 1);
                bottomtext.setAlpha((float) 1);
                autoAttempted.setEnabled(true);
                autoAttempted.setAlpha((float) 1);
                autoScoreAttempt.setAlpha((float) 1);
                attemptedAutoText.setAlpha((float) 1);
                auto_no_autoColor.setBackgroundColor(Color.TRANSPARENT);
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

        //Defense Area


        climbTimerStart.setOnClickListener(v -> {
            if (climbtimer == 0) {
                climbTimerStart.setText("Stop Timer");
                climbtimer = 1;
            } else if (climbtimer == 1) {
                climbTimerStart.setText("Start Timer");
                climbtimer = 0;
            }
        });


        climbTimer.setOnClickListener(v -> {
            {
                if (climbtimerreset == 0) {
                    climbTimerClear.setVisibility(View.VISIBLE);
                    ds_cooldown = 750;
                }
                if (climbtimerreset >= 0) {
                    climbtimerreset++;
                }
                if (climbtimerreset == 3) {
                    climbTimer.setText("00:00.00");
                    climbtimerreset = 0;
                    minutesclimb = 0;
                    secondsclimb = 0;
                    milisecondsclimb = 0;
                    milisecondsclimbraw = 0;
                }
            }
        });

        defenseTimer.setOnClickListener(v -> {
            {
                if (timerReset == 0) {
                    timerClear.setVisibility(View.VISIBLE);
                    ds_cooldown = 750;
                }
                if (timerReset >= 0) {
                    timerReset++;
                }
                if (timerReset == 3) {
                    defenseTimer.setText("00:00.00");
                    timerReset = 0;
                    minutesdefending = 0;
                    secondsdefending = 0;
                    milisecondsdefending = 0;
                    milisecondsdefendingraw = 0;
                }
            }
        });


        startDefenseTimer.setOnClickListener(v -> {
            if (defensetimer == 0) {
                startDefenseTimer.setText("Stop Timer");
                defensetimer = 1;
            } else if (defensetimer == 1) {
                startDefenseTimer.setText("Start Timer");
                defensetimer = 0;
            }
        });


        //Enter Dev Mode. Only necessary if something is broke
        rrlogo.setOnClickListener(v -> {
            if (dev == 0) {
                dev = 1;
            } else if (dev == 1) {
                dev = 0;
            }
            apple++;
            if (apple == 50) {
                rrlogo.setImageResource(R.drawable.apples);
            }
            if (apple == 51) {
                rrlogo.setImageResource(R.drawable.rrlogo);
            }
            if (apple == 52) {
                apple = 0;
            }
        });
        devMode.setOnClickListener(v -> {
            if (alliance.equals("blue") && dev == 1) {
                Background.setBackgroundResource(R.drawable.bluedev);
                auto_lower_text.setTextColor(Color.WHITE);
                auto_upper_text.setTextColor(Color.WHITE);
                auto_no_auto.setTextColor(Color.WHITE);
                toptext.setTextColor(Color.WHITE);
                toptext2.setTextColor(Color.WHITE);
                missedtext.setTextColor(Color.WHITE);
                bottomtext.setTextColor(Color.WHITE);
                bottomtext2.setTextColor(Color.WHITE);
                teleop_lower_text.setTextColor(Color.WHITE);
                teleop_upper_text.setTextColor(Color.WHITE);
                missedShotsText.setTextColor(Color.WHITE);
                seekbarcover.setImageResource(R.drawable.white);
                autoScoreAttempt.setBackgroundResource(R.drawable.seekbarwhite);
                attemptedAutoText.setTextColor(Color.WHITE);
            }
            if (alliance.equals("red") && dev == 1) {
                Background.setBackgroundResource(R.drawable.reddev);
                autoScoreAttempt.setBackgroundResource(R.drawable.seekbarwhite);
                auto_lower_text.setTextColor(Color.WHITE);
                auto_upper_text.setTextColor(Color.WHITE);
                auto_no_auto.setTextColor(Color.WHITE);
                toptext.setTextColor(Color.WHITE);
                toptext2.setTextColor(Color.WHITE);
                seekbarcover.setImageResource(R.drawable.white);
                missedtext.setTextColor(Color.WHITE);
                bottomtext.setTextColor(Color.WHITE);
                bottomtext2.setTextColor(Color.WHITE);
                teleop_lower_text.setTextColor(Color.WHITE);
                teleop_upper_text.setTextColor(Color.WHITE);
                missedShotsText.setTextColor(Color.WHITE);
                attemptedAutoText.setTextColor(Color.WHITE);
            }
            if (alliance.equals("none") && dev == 1) {
                Background.setBackgroundResource(R.drawable.nodev);
                autoScoreAttempt.setBackgroundResource(R.drawable.seekbarwhite);
                auto_lower_text.setTextColor(Color.WHITE);
                attemptedAutoText.setTextColor(Color.WHITE);
                auto_upper_text.setTextColor(Color.WHITE);
                auto_no_auto.setTextColor(Color.WHITE);
                toptext.setTextColor(Color.WHITE);
                toptext2.setTextColor(Color.WHITE);
                missedtext.setTextColor(Color.WHITE);
                bottomtext.setTextColor(Color.WHITE);
                bottomtext2.setTextColor(Color.WHITE);
                teleop_lower_text.setTextColor(Color.WHITE);
                teleop_upper_text.setTextColor(Color.WHITE);
                missedShotsText.setTextColor(Color.WHITE);
                seekbarcover.setImageResource(R.drawable.white);
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
            if (alliance.equals("none")) {
                closeError += " No Alliance,";
                allianceText.setBackgroundColor(Color.argb(255, 255, 255, 0));
            }
            if (name_input.getText().toString().equals("")) {
                closeError += " No Name,";
                name_input.setBackgroundColor(Color.argb(255, 255, 255, 0));
            }
            if (!closeError.equals("")) {
                closeError = closeError.substring(0, closeError.length() - 1) + ".";
            }
            if (!(closeError.equals(""))) {
                Toast.makeText(getApplicationContext(), "Submit Error:" + closeError, Toast.LENGTH_LONG).show();
                data_submitted.setVisibility(View.VISIBLE);
                data_submitted.setImageResource(R.drawable.x);
                ds_cooldown = 1500;
            } else {
                Pregame.setVisibility(View.INVISIBLE);
                Gray_Box.setVisibility(View.INVISIBLE);
                allianceText.setBackgroundColor(Color.TRANSPARENT);
                round_input.setBackground(textBackground);
                team_input.setBackground(textBackground);
                name_input.setBackground(nameBackground);
            }
        });
        DefenseNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus == false) {
                hideKeyboard(v);
            }

        });
        //This is the gray box that closes the endgame box
        Gray_Box.setOnClickListener(View -> {
            rrlogo.setImageResource(R.drawable.rrlogo);
            apple = 0;
            if (Pregame.getVisibility() == android.view.View.VISIBLE) {
                String closeError = "";
                if (team_input.getText().toString().equals("")) {
                    closeError += " No Team,";
                    team_input.setBackgroundColor(Color.argb(255, 255, 255, 0));
                }
                if (round_input.getText().toString().equals("")) {
                    closeError += " No Match,";
                    round_input.setBackgroundColor(Color.argb(255, 255, 255, 0));
                }
                if (alliance.equals("none")) {
                    closeError += " No Alliance,";
                    allianceText.setBackgroundColor(Color.argb(255, 255, 255, 0));
                }
                if (name_input.getText().toString().equals("")) {
                    closeError += " No Name,";
                    name_input.setBackgroundColor(Color.argb(255, 255, 255, 0));
                }
                if (!closeError.equals("")) {
                    closeError = closeError.substring(0, closeError.length() - 1) + ".";
                }


                if (!(closeError.equals(""))) {
                    Toast.makeText(getApplicationContext(), "Submit Error:" + closeError, Toast.LENGTH_LONG).show();

                    data_submitted.setVisibility(android.view.View.VISIBLE);
                    data_submitted.setImageResource(R.drawable.x);
                    ds_cooldown = 1500;
                } else {
                    Pregame.setVisibility(android.view.View.INVISIBLE);
                    Gray_Box.setVisibility(android.view.View.INVISIBLE);
                    allianceText.setBackgroundColor(Color.TRANSPARENT);
                    round_input.setBackground(textBackground);
                    team_input.setBackground(textBackground);
                    name_input.setBackground(nameBackground);
                }
            }
            if (Defense.getVisibility() == android.view.View.VISIBLE) {

                if (playedDefense.isChecked()) {
                    String closeError = "";

                    if (DefenseNumber.getText().toString().equals("") && DefenseNumber.getVisibility() == android.view.View.VISIBLE) {
                        closeError += " No Team,";
                        DefenseNumber.setBackgroundColor(Color.argb(255, 255, 255, 0));
                    }
                    //if (milisecondsdefendingraw <= 0) {
                        //closeError += " No Time,";
                        //startDefenseTimer.setBackgroundColor(Color.argb(255, 255, 255, 0));
                    //}
                    if (defended1.isChecked() == false && defended2.isChecked() == false && defended3.isChecked() == false) {
                        closeError += " No Team,";
                        defended1.setBackgroundColor(Color.argb(255, 255, 255, 0));
                        defended2.setBackgroundColor(Color.argb(255, 255, 255, 0));
                        defended3.setBackgroundColor(Color.argb(255, 255, 255, 0));
                    }


                    if (!closeError.equals("")) {
                        closeError = closeError.substring(0, closeError.length() - 1) + ".";
                    }


                    if (!(closeError.equals(""))) {
                        Toast.makeText(getApplicationContext(), "Submit Error:" + closeError, Toast.LENGTH_LONG).show();

                        data_submitted.setVisibility(android.view.View.VISIBLE);
                        data_submitted.setImageResource(R.drawable.x);
                        ds_cooldown = 1500;


                    } else {
                        Defense.setVisibility(android.view.View.INVISIBLE);
                        Gray_Box.setVisibility(android.view.View.INVISIBLE);
                        DefenseNumber.setBackground(textBackground);
                        startDefenseTimer.setBackgroundColor(Color.argb(100, 98, 0, 238));
                        defended1.setBackgroundColor(Color.TRANSPARENT);
                        defended2.setBackgroundColor(Color.TRANSPARENT);
                        defended3.setBackgroundColor(Color.TRANSPARENT);
                    }
                }

                if (playedDefense.isChecked() == false) {
                    Defense.setVisibility(android.view.View.INVISIBLE);
                    Gray_Box.setVisibility(android.view.View.INVISIBLE);
                    DefenseNumber.setBackground(textBackground);
                    startDefenseTimer.setBackgroundColor(Color.argb(100, 98, 0, 238));
                    defended1.setBackgroundColor(Color.TRANSPARENT);
                    defended2.setBackgroundColor(Color.TRANSPARENT);
                    defended3.setBackgroundColor(Color.TRANSPARENT);
                }
            }
            Endgame.setVisibility(android.view.View.INVISIBLE);
            startDefenseTimer.setText("Start Timer");
            defensetimer = 0;
            climbTimerStart.setText("Start Timer");
            climbtimer = 0;

        });
        //These lines are the special function toggles.
        //The Pregame_box is a dev way to open and close pregame without filling out fields
        //The Endgame_box is a operator way to open and close endgame.
        Endgame.setVisibility(android.view.View.INVISIBLE);
        Defense.setVisibility(android.view.View.INVISIBLE);

        Pregame_Box.setOnClickListener(view -> {
            if (Pregame.getVisibility() == View.INVISIBLE) {
                Endgame.setVisibility(View.INVISIBLE);
                Defense.setVisibility(View.INVISIBLE);
                Pregame.setVisibility(View.VISIBLE);
                Gray_Box.setVisibility(View.VISIBLE);
                Pregame.bringToFront();
            } else if (Pregame.getVisibility() == View.VISIBLE) {
                Pregame.setVisibility(View.INVISIBLE);
                Endgame.setVisibility(View.INVISIBLE);
                Defense.setVisibility(View.INVISIBLE);
                Gray_Box.setVisibility(View.INVISIBLE);
            }
        });
        Endgame_Box.setOnClickListener(view -> {
            if (Endgame.getVisibility() == View.VISIBLE) {
                Pregame.setVisibility(View.INVISIBLE);
                Defense.setVisibility(View.INVISIBLE);
                Endgame.setVisibility(View.INVISIBLE);
                Gray_Box.setVisibility(View.INVISIBLE);
                climbTimerStart.setText("Start Timer");
                climbtimer = 0;
            } else if (Endgame.getVisibility() == View.INVISIBLE) {
                Endgame.setVisibility(View.VISIBLE);
                Pregame.setVisibility(View.INVISIBLE);
                Defense.setVisibility(View.INVISIBLE);
                Gray_Box.setVisibility(View.VISIBLE);
                Endgame.bringToFront();
            }
        });
        Defense_Box.setOnClickListener(view -> {
            if (Defense.getVisibility() == View.VISIBLE) {
                Pregame.setVisibility(View.INVISIBLE);
                Defense.setVisibility(View.INVISIBLE);
                Endgame.setVisibility(View.INVISIBLE);
                Gray_Box.setVisibility(View.INVISIBLE);
                startDefenseTimer.setText("Start Timer");
                defensetimer = 0;

            } else if (Defense.getVisibility() == View.INVISIBLE) {
                Endgame.setVisibility(View.INVISIBLE);
                Pregame.setVisibility(View.INVISIBLE);
                Defense.setVisibility(View.VISIBLE);
                Gray_Box.setVisibility(View.VISIBLE);
                Defense.bringToFront();
            }
        });
        //This sets the alliance blue and it sets the background colour blue.
        Blue_Alliance.setOnClickListener(v -> {
            dev=0;
            Background.setBackgroundResource(R.drawable.blueappbackground);
            Pregame.setBackgroundColor(Color.argb(255, 127, 127, 247));
            Endgame.setBackgroundColor(Color.argb(255, 127, 127, 247));
            Defense.setBackgroundColor(Color.argb(255, 127, 127, 247));
            auto_lower_text.setTextColor(Color.BLACK);
            auto_upper_text.setTextColor(Color.BLACK);
            auto_no_auto.setTextColor(Color.BLACK);
            toptext.setTextColor(Color.BLACK);
            toptext2.setTextColor(Color.BLACK);
            missedtext.setTextColor(Color.BLACK);
            bottomtext.setTextColor(Color.BLACK);
            bottomtext2.setTextColor(Color.BLACK);
            teleop_lower_text.setTextColor(Color.BLACK);
            teleop_upper_text.setTextColor(Color.BLACK);
            missedShotsText.setTextColor(Color.BLACK);
            autoAttempted.setBackgroundColor(Color.TRANSPARENT);
            autoScoreAttempt.setBackgroundResource(R.drawable.seekbar);
            seekbarcover.setImageResource(Color.TRANSPARENT);
            attemptedAutoText.setTextColor(Color.BLACK);
            alliance = "blue";
        });
        //This sets the alliance red and sets the background color red.
        Red_Alliance.setOnClickListener(v -> {
            dev=0;
            Background.setBackgroundResource(R.drawable.redappbackground);
            Pregame.setBackgroundColor(Color.argb(255, 247, 127, 127));
            Endgame.setBackgroundColor(Color.argb(255, 247, 127, 127));
            Defense.setBackgroundColor(Color.argb(255, 247, 127, 127));
            auto_lower_text.setTextColor(Color.BLACK);
            auto_upper_text.setTextColor(Color.BLACK);
            auto_no_auto.setTextColor(Color.BLACK);
            autoAttempted.setBackgroundColor(Color.TRANSPARENT);
            autoScoreAttempt.setBackgroundResource(R.drawable.seekbar);
            attemptedAutoText.setTextColor(Color.BLACK);
            toptext.setTextColor(Color.BLACK);
            toptext2.setTextColor(Color.BLACK);
            missedtext.setTextColor(Color.BLACK);
            bottomtext.setTextColor(Color.BLACK);
            bottomtext2.setTextColor(Color.BLACK);
            teleop_lower_text.setTextColor(Color.BLACK);
            seekbarcover.setImageResource(Color.TRANSPARENT);
            teleop_upper_text.setTextColor(Color.BLACK);
            missedShotsText.setTextColor(Color.BLACK);
            alliance = "red";
        });
        //All of these OnTouchListeners are to reset the color of the boxes when they turn yellow from a lack of inputs.
        climbResult.setOnTouchListener((view, motionEvent) -> {
            if (!(climbResult.getSelectedItem() == "No Input")) {
                climbResult.setBackgroundResource(R.drawable.spinnerbackground);
            }
            return false;
        });
        endgame_results.setOnTouchListener((view, motionEvent) -> {
            if (!(endgame_results.getSelectedItem() == "No Input")) {
                endgame_results.setBackgroundResource(R.drawable.spinnerbackground);
            }
            return false;
        });
        //All of these OnFocusChanges are to get the keyboard out of the way when inputting stuff is finished
        name_input.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { hideKeyboard(v); }
        });
        String[] finalSplitstrArray = splitstrArray;
        round_input.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && getTeams() != "") {
                String[] tempIntArr = null;
                String[] splittempIntArr = null;
                    tempIntArr = getTeams().split("\n");

                if (round_input.getText().toString().equals("")) {round_input.setText("1");} else
                {roundfill = Integer.parseInt(round_input.getText().toString());}

                splittempIntArr = tempIntArr[roundfill-1].split(",");
                if (tabletnumber >= 4) {
                    defended1.setText(splittempIntArr[0]);
                    defended2.setText(splittempIntArr[1]);
                    defended3.setText(splittempIntArr[2]);
                } else if (tabletnumber <= 3) {
                    defended1.setText(splittempIntArr[3]);
                    defended2.setText(splittempIntArr[4]);
                    defended3.setText(splittempIntArr[5]);
                }
                hideKeyboard(v);
                if (teamAutofill.isChecked()) {
                    team_input.setText(splittempIntArr[tabletnumbercomp]);
                }
            }
        });
        team_input.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { hideKeyboard(v); }
            if (team_input.getText().toString().equals("1706")) {rrlogo.animate().rotation(2880f).setDuration(5000).start();}
        });
        notes.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { hideKeyboard(v); }
        });
        //This is all of the garbage that the submit button does.
        String[] finalSplitstrArray3 = splitstrArray;
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
            }
            if (team_input.getText().toString().equals("")) { team = -1; }
            else { team = Integer.parseInt(team_input.getText().toString()); }
            if (round_input.getText().toString().equals("")) { round = -1; }
            else { round = Integer.parseInt(round_input.getText().toString()); }
            //These are telling the toast what to put in the error field, and it changes the color to yellow.
            if (alliance.equals("none")) { submitError += " No Alliance,"; allianceText.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.INVISIBLE); Pregame.setVisibility(View.VISIBLE);}
            if (name_input.getText().toString().equals("")) { submitError += " No Name,"; name_input.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.INVISIBLE); Pregame.setVisibility(View.VISIBLE);}
            if (round_input.getText().toString().equals("420")) { submitError += " No. Its not even funny,"; round_input.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.INVISIBLE); Pregame.setVisibility(View.VISIBLE);}
            if (endgame_results.getSelectedItem().toString().equals("No Input")) { submitError += " No Results,"; endgame_results.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.VISIBLE); Pregame.setVisibility(View.INVISIBLE);}
            if (climbResult.getSelectedItem().toString().equals("No Input")) { submitError += " No Climb Result,"; climbResult.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.VISIBLE); Pregame.setVisibility(View.INVISIBLE);}
            if (autoUpperScore+autoLowerScore > Integer.parseInt(String.valueOf(autoAttempted.getProgress()))&& dev==0) {submitError += " More Auto than Auto Attempted,"; autoAttempted.setBackgroundColor(Color.argb(255,255,255,0));Endgame.setVisibility(View.INVISIBLE); Pregame.setVisibility(View.INVISIBLE);}
            if (team_input.getText().toString().equals("")) { submitError += " No Team#,"; team_input.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.INVISIBLE); Pregame.setVisibility(View.VISIBLE);}
            if (round_input.getText().toString().equals("")) { submitError += " No Round#,"; round_input.setBackgroundColor(Color.argb(255,255,255,0)); Endgame.setVisibility(View.INVISIBLE); Pregame.setVisibility(View.VISIBLE); }
            if (!submitError.equals("")) { submitError = submitError.substring(0,submitError.length()-1)+"."; }
            //If any of the above are true, the thing returns a submit error.
            if (!(submitError.equals(""))) {
                Toast.makeText(getApplicationContext(), "Submit Error:"+submitError, Toast.LENGTH_LONG).show();
                //Place an X if incorrect
                data_submitted.setVisibility(View.VISIBLE);
                data_submitted.setImageResource(R.drawable.x);
                ds_cooldown = 1500;
            } else {
                //This is what happens whenever all is correct
                data_submitted.setVisibility(View.VISIBLE);
                data_submitted.setImageResource(R.drawable.check);
                team_input.setBackground(textBackground);
                round_input.setBackground(textBackground);
                name_input.setBackground(nameBackground);
                allianceText.setBackgroundResource(Color.TRANSPARENT);
                climbResult.setBackgroundResource(R.drawable.spinnerbackground);
                endgame_results.setBackgroundResource(R.drawable.spinnerbackground);
                ds_cooldown = 1500; //Makes the check mark appear
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
                    myOutWriter.println("Robot Errors: "+robotError.isChecked());
                    myOutWriter.println("Auto Top Score: "+autoUpperScore);
                    myOutWriter.println("Auto Bottom Score: "+autoLowerScore);
                    myOutWriter.println("No Auto: "+auto_no_auto.isChecked());
                    myOutWriter.println("Attempted Auto: "+autoAttempted.getProgress());
                    myOutWriter.println("Teleop Top Score: "+teleopUpperScore);
                    myOutWriter.println("Teleop Bottom Score: "+teleopLowerScore);
                    myOutWriter.println("Missed Shots: "+missedScore);
                    myOutWriter.println("Endgame: "+climbResult.getSelectedItem());
                    myOutWriter.println("Results: "+endgame_results.getSelectedItem());
                    myOutWriter.println("Notes: "+notes.getText());
                    myOutWriter.println("Played Defense: "+playedDefense.isChecked());
                    if(playedDefense.isChecked()) {
                        if (defended1.isChecked() && defended2.isChecked() && defended3.isChecked()) {
                            myOutWriter.println("Defended Teams: " + defended1.getText() + "," + defended2.getText() + "," + defended3.getText());
                        } else if (defended1.isChecked() && defended2.isChecked()) {
                            myOutWriter.println("Defended Teams: " + defended1.getText() + "," + defended2.getText());
                        } else if (defended3.isChecked() && defended2.isChecked()) {
                            myOutWriter.println("Defended Teams: " + defended2.getText() + "," + defended3.getText());
                        } else if (defended1.isChecked() && defended3.isChecked()) {
                            myOutWriter.println("Defended Teams: " + defended1.getText() + "," + defended3.getText());
                        } else if (defended1.isChecked()) {
                            myOutWriter.println("Defended Teams: " + defended1.getText());
                        } else if (defended3.isChecked()) {
                            myOutWriter.println("Defended Teams: " + defended3.getText());
                        } else if (defended2.isChecked()) {
                            myOutWriter.println("Defended Teams: " + defended2.getText());
                        }
                    }
                    if(playedDefense.isChecked()==false) {
                        myOutWriter.println("Defended Teams: ");

                    }


                    //Climb Time Detection
                    if ((climbResult.getSelectedItem().toString().equals("FAIL")) && milisecondsclimbraw>0) {myOutWriter.println("Climb Time: "+milisecondsclimbraw);}
                    if ((climbResult.getSelectedItem().toString().equals("LOW")) && milisecondsclimbraw>0) {myOutWriter.println("Climb Time: "+milisecondsclimbraw);}
                    if ((climbResult.getSelectedItem().toString().equals("MIDDLE")) && milisecondsclimbraw>0) {myOutWriter.println("Climb Time: "+milisecondsclimbraw);}
                    if ((climbResult.getSelectedItem().toString().equals("HIGH")) && milisecondsclimbraw>0) {myOutWriter.println("Climb Time: "+milisecondsclimbraw);}
                    if ((climbResult.getSelectedItem().toString().equals("TRAVERSE")) && milisecondsclimbraw>0) {myOutWriter.println("Climb Time: "+milisecondsclimbraw);}
                    if ((climbResult.getSelectedItem().toString().equals("NONE")) && milisecondsclimbraw>0) {myOutWriter.println("Climb Time: BadData");}
                    if ((climbResult.getSelectedItem().toString().equals("FAIL")) && milisecondsclimbraw==0) {myOutWriter.println("Climb Time: BadData");}
                    if ((climbResult.getSelectedItem().toString().equals("LOW")) && milisecondsclimbraw==0) {myOutWriter.println("Climb Time: BadData");}
                    if ((climbResult.getSelectedItem().toString().equals("MIDDLE")) && milisecondsclimbraw==0) {myOutWriter.println("Climb Time: BadData");}
                    if ((climbResult.getSelectedItem().toString().equals("HIGH")) && milisecondsclimbraw==0) {myOutWriter.println("Climb Time: BadData");}
                    if ((climbResult.getSelectedItem().toString().equals("TRAVERSE")) && milisecondsclimbraw==0) {myOutWriter.println("Climb Time: BadData");}
                    if ((climbResult.getSelectedItem().toString().equals("NONE")) && milisecondsclimbraw==0) {myOutWriter.println("Climb Time: 0");}


                    myOutWriter.flush();
                    myOutWriter.close();
                    fOut.close();
                    Toast.makeText(getApplicationContext(), "Data Submitted!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    //If anything goes wrong, it throws an error instead of crashing
                    Toast.makeText(getApplicationContext(), "Data Submission Failed! (Tell scouting)", Toast.LENGTH_SHORT).show();
                    Log.e("Exception", "File write failed: " + e);
                }
                //Reset Everything
                roundfill = Integer.parseInt(round_input.getText().toString());
                roundfill ++;
                teleopLowerScore = 0;
                teleopUpperScore = 0;
                autoLowerScore = 0;
                autoUpperScore = 0;
                missedScore = 0;
                auto_lower_text.setEnabled(true);
                auto_upper_text.setEnabled(true);
                auto_upper_minus.setEnabled(true);
                auto_upper_plus.setEnabled(true);
                auto_lower_minus.setEnabled(true);
                auto_lower_plus.setEnabled(true);
                auto_lower_minus.setAlpha((float) 1);
                auto_lower_plus.setAlpha((float) 1);
                auto_lower_text.setAlpha((float) 1);
                auto_upper_minus.setAlpha((float) 1);
                auto_upper_plus.setAlpha((float) 1);
                auto_upper_text.setAlpha((float) 1);
                toptext.setAlpha((float) 1);
                autoAttempted.setEnabled(true);
                autoScoreAttempt.setAlpha((float) 1);
                attemptedAutoText.setAlpha((float) 1);
                bottomtext.setAlpha((float) 1);
                notes.setText("");
                scouterName = name_input.getText().toString();
                autoAttempted.setBackgroundColor(Color.TRANSPARENT);
                auto_no_autoColor.setBackgroundColor(Color.TRANSPARENT);
                name_input.setText("");
                autoAttempted.setProgress(0);
                round_input.setText(String.valueOf(roundfill));
                team_input.setText("");
                endgame_results.setSelection(0);
                climbResult.setSelection(0);
                auto_no_auto.setChecked(false);
                robotError.setChecked(false);
                teleop_upper_text.setText("0");
                teleop_lower_text.setText("0");
                auto_upper_text.setText("0");
                auto_lower_text.setText("0");
                missedShotsText.setText("0");
                defenseTimer.setText("00:00.00");
                climbTimer.setText("00:00.00");
                playedDefense.setChecked(false);
                defensetimer = 0;
                climbtimer = 0;
                startDefenseTimer.setEnabled(false);
                startDefenseTimer.setAlpha((float) 0.5);
                defended1.setEnabled(false);
                defended1.setAlpha((float) 0.5);
                defended2.setEnabled(false);
                defended2.setAlpha((float) 0.5);
                defended3.setEnabled(false);
                defended3.setAlpha((float) 0.5);
                DefenseNumber.setEnabled(false);
                DefenseNumber.setAlpha((float) 0.5);
                defenseLowerScore = 0;
                defenseUpperScore = 0;
                Defense.setVisibility(View.INVISIBLE);
                startDefenseTimer.setText("Start Timer");
                climbTimerStart.setText("Start Timer");
                climbtimerreset = 0;
                timerReset = 0;
                milisecondsclimbraw = 0;
                milisecondsclimb = 0;
                milisecondsdefendingraw = 0;
                milisecondsdefending = 0;
                secondsclimb = 0;
                secondsdefending = 0;
                minutesclimb = 0;
                minutesdefending = 0;

                defended1.setChecked(false);
                defended2.setChecked(false);
                defended3.setChecked(false);
                DefenseNumber.setText("");
                Endgame.setVisibility(View.INVISIBLE);
                Pregame.setVisibility(View.VISIBLE);
                startDefenseTimer.setEnabled(false);
                startDefenseTimer.setAlpha((float) 0.5);
                if (roundfill>1) {sameScouter.setVisibility(View.VISIBLE);}
                String[] tempIntArr1 = null;
                String[] splittempIntArr1 = null;
                tempIntArr1 = getTeams().split("\n");
                roundfill = Integer.parseInt(round_input.getText().toString());
                splittempIntArr1 = tempIntArr1[roundfill-1].split(",");
                if (tabletnumber >=4) {
                    defended1.setText(splittempIntArr1[0]);
                    defended2.setText(splittempIntArr1[1]);
                    defended3.setText(splittempIntArr1[2]);
                } else if (tabletnumber <=3) {
                    defended1.setText(splittempIntArr1[3]);
                    defended2.setText(splittempIntArr1[4]);
                    defended3.setText(splittempIntArr1[5]);
                }
                if (teamAutofill.isChecked() && getTeams() != "") {
                    String[] tempIntArr = null;
                    String[] splittempIntArr = null;
                    tempIntArr = getTeams().split("\n");
                    roundfill = Integer.parseInt(round_input.getText().toString());
                    splittempIntArr = tempIntArr[roundfill-1].split(",");
                    team_input.setText(splittempIntArr[tabletnumbercomp]);
                }
                if (!teamAutofill.isChecked()) {
                    team_input.setText("");
                }
            }
        });
    }

    //Tells the code where to store the entries
    private File getDataDirectory() {
        File directory = Environment.getExternalStorageDirectory();
        File myDir = new File(directory + "/ScoutingData");
        myDir.mkdirs();
        return myDir;
    }
    //Makes sure that the back button is not going to clear data
    @Override
    public void onBackPressed() {}
    //This loads the external document to prepopulate the teams
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
    //This hides the keyboard after unfocusing on a textfield
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
