package com.example.androidgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    //Visible elements:
    private RadioGroup gridSizeSelection;
    private Switch soundSelection;
    private ImageView boxBlueView;
    private ImageView boxGreenView;
    private ImageView boxRedView;
    private ImageView boxYellowView;

    SharedPreferences sharedPref; //Shared preferences object
    SharedPreferences.Editor editor; //Shared preferences editor object


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Getting references to items in the layout:
        gridSizeSelection = findViewById(R.id.gridSizeRadioGroup);
        soundSelection = findViewById(R.id.soundSwitch);
        boxBlueView = findViewById(R.id.boxBlue);
        boxGreenView = findViewById(R.id.boxGreen);
        boxRedView = findViewById(R.id.boxRed);
        boxYellowView = findViewById(R.id.boxYellow);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();

        //Loading animations:
        Animation box1Anim = AnimationUtils.loadAnimation(this, R.anim.box1_anim);
        Animation box2Anim = AnimationUtils.loadAnimation(this, R.anim.box2_anim);
        Animation box3Anim = AnimationUtils.loadAnimation(this, R.anim.box3_anim);
        Animation box4Anim = AnimationUtils.loadAnimation(this, R.anim.box4_anim);

        //Starting animations:
        boxBlueView.startAnimation(box1Anim);
        boxGreenView.startAnimation(box2Anim);
        boxRedView.startAnimation(box3Anim);
        boxYellowView.startAnimation(box4Anim);

        //Settings text on the soundSelection Switch based on the shared preferences:
        if (sharedPref.getBoolean("soundsOn", true)) {
            soundSelection.setChecked(true);
            soundSelection.setText("ON");
        }
        else {
            soundSelection.setChecked(false);
            soundSelection.setText("OFF");
        }

        /* This is the event listener that is going to wait for the
            radio button change. It will then take the new button that we clicked
            and it will apply the change to the grid. */
        gridSizeSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedButton = findViewById(checkedId); //Gets the current radio button
                if (checkedButton.getText().equals("10 x 10 Size - 13 turns")) {
                    //Checks if it was the 10 x 10 button.
                    //Save the gridSize as a shared preference to the default XML file:
                    editor.putInt("gridSize", 10);
                }
                if (checkedButton.getText().equals("14 x 14 Size - 18 turns")) {
                    //Checks if it was the 14 x 14 button.
                    //Save the gridSize as a shared preference to the default XML file:
                    editor.putInt("gridSize", 14);
                }
                if (checkedButton.getText().equals("18 x 18 Size - 24 turns")) {
                    //Checks if it was the 18 x 18 button
                    //Save the gridSize as a shared preference to the default XML file:
                    editor.putInt("gridSize", 18);
                }
                editor.putBoolean("sizeChanged", true); /* They checked a button; indicate that
                                                            the board size has been changed*/
                editor.apply(); //Apply changes
            }
        }); //End of gridSizeSelection RadioGroup listener

         //This is the event listener that is going to wait for the switch change.
        soundSelection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { //Sounds on
                    editor.putBoolean("soundsOn", true);
                    soundSelection.setText("ON"); //Set the switch text
                }
                else { //Sounds off
                    editor.putBoolean("soundsOn", false);
                    soundSelection.setText("OFF"); //Set the switch text
                }
                editor.apply(); //Apply changes
            }
        }); //End of soundSelection Switch listener
    } //End of onCreate()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //Inflates the menu.
        getMenuInflater().inflate(R.menu.menu_bar_settings, menu);
        return true;
    } //End of onCreateOptionsMenu()

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item ) { //Handles menu actions.
        if (item.getItemId() == R.id.action_startmenu) {
            //Exit to start menu if user selects the start menu option from the app bar:
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.action_playgame) {
            //Go to game activity if user selects play game option from the app bar:
            Intent intent = new Intent (this, GameActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    } //End of onOptionsItemSelected()
} //End of SettingsActivity Class
