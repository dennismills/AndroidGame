package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private Button settingsButton; // The variable that holds a reference to the settings menu button
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingsButton = findViewById(R.id.settingsButton);
        playButton = findViewById(R.id.playButton);
        /*
            This is the onClickListener that allows us to bind the navigation
            button to the function. The function will take the user to the settings
            page of the application.
        */
        settingsButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                /*
                    Creates an intent for the activity that we want to navigate to.
                    This will effectively take us to the settings page
                */
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent); // Launches the activity and takes us to the settings page
            }
        });

         playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent); // Launches the activity and takes us to the game page

            }

        });
    }

}
