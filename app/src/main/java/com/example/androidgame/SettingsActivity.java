package com.example.androidgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class SettingsActivity extends AppCompatActivity
{
    private RadioGroup gridSizeSelection; // This is a variable to hold a reference to the grid radio group
    SharedPreferences sharedPref; // Shared preferences object
    SharedPreferences.Editor editor; //Shared preferences editor object

    /*
        TODO: Add the color changing options
        TODO: Add the music options
    */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();

        // Show dialog warning user that resizing will result in any game being played being reset:
        ResizeWarningDialog dialog = new ResizeWarningDialog();
        dialog.show(getSupportFragmentManager(), "");

        gridSizeSelection = findViewById(R.id.gridSizeRadioGroup); // Gets the reference for the radio group

        /*
            This is the event listener that is going to wait for the
            radio button change. It will then take the new button that we clicked
            and it will apply the change to the grid.
        */

        gridSizeSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                /*
                    TODO: Only warn user about resetting the board size if a game is in progress.

                    It may be nice to add a dialog fragment here so that
                    the user is given a warning that they will lose their current
                    game progress if they choose to change the grid size mid game.

                    We then would check to see if they have selected either
                    the Yes or No radio buttons and then make a choice based
                    on that instead of just changing the grid automatically.

                    In the event that there is no game in progress, we shouldn't
                    display the warning to the user. This will also ensure
                    that the message doesn't pop up each subsequent time we change the
                    grid size after the initial change.
                */


                RadioButton checkedButton = findViewById(checkedId); // Gets the current radio button
                if (checkedButton.getText().equals("10 x 10")) // Checks if it was the 10 x 10 button
                {
                    // Save the gridSize as a shared preference to the default XML file:
                    editor.putInt("gridSize", 10);
                    editor.commit();

                }
                if (checkedButton.getText().equals("14 x 14")) // Checks if it was the 14 x 14 button
                {
                    // Save the gridSize as a shared preference to the default XML file:
                    editor.putInt("gridSize", 14);
                    editor.commit();
                }
                if (checkedButton.getText().equals("18 x 18")) // Checks if it was the 18 x 18 button
                {
                    // Save the gridSize as a shared preference to the default XML file:
                    editor.putInt("gridSize", 18);
                    editor.commit();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Menu bar
        getMenuInflater().inflate(R.menu.menu_bar_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item ) { //Menu bar options
        if (item.getItemId() == R.id.action_startmenu) {
            // Exit to start menu if user selects the start menu option from the app bar:
            // TODO: Add code to warn the user that returning to the start menu will reset any game currently being played
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.action_playgame) {
            //Go to game activity if user selects play game option from the app bar:
            // TODO: See above about ensuring that 1) the game state is saved and 2) only warning the user about resizing the board if a game is in progress
            Intent intent = new Intent (this, GameActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}