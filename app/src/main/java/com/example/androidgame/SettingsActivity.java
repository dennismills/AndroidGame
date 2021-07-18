package com.example.androidgame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity
{

    private Button returnToMainMenuButton; // The variable to hold a reference to the navigation button
    private RadioGroup gridSizeSelection; // This is a variable to hold a reference to the grid radio group
    private int gridSize = 10; // Defaults to a 10 x 10 sized grid each time the game starts

    /*
        TODO: Add the color changing options
        TODO: Add the music options
    */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        returnToMainMenuButton = findViewById(R.id.returnToMainMenuButton); // Gets the reference for the navigation button

        /*
            This is the onClickListener that allows us to bind the navigation
            button to the function. The function will return the user back to
            the main activity.
        */
        returnToMainMenuButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*
                    Creates an intent for the activity that we want to navigate to.
                    This will effectively take us back to the main menu.
                */
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent); // Launches the activity and takes us back
            }
        });

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
                    TODO: It may be nice to add a dialog fragment here so that
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
                    gridSize = 10; // Sets the grid size to 10
                }
                if (checkedButton.getText().equals("16 x 16")) // Checks if it was the 16 x 16 button
                {
                    gridSize = 16; // Sets the grid size to 16
                }
                if (checkedButton.getText().equals("24 x 24")) // Checks if it was the 24 x 24 button
                {
                    gridSize = 24; // Sets the grid size to 24
                }
                Log.i("Info", "The grid size is: " + gridSize);
            }
        });
    }
}