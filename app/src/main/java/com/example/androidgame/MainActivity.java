package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //Visible elements:
    private Button settingsButton;
    private Button playButton;
    private ImageView blueBox1;
    private ImageView blueBox2;
    private ImageView blueBox3;
    private ImageView blueBox4;
    private ImageView blueBox5;
    private ImageView greenBox1;
    private ImageView greenBox2;
    private ImageView greenBox3;
    private ImageView greenBox4;
    private ImageView greenBox5;
    private ImageView yellowBox1;
    private ImageView yellowBox2;
    private ImageView yellowBox3;
    private ImageView yellowBox4;
    private ImageView yellowBox5;
    private ImageView redBox1;
    private ImageView redBox2;
    private ImageView redBox3;
    private ImageView redBox4;
    private ImageView redBox5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        //Getting references to items in the layout:
        settingsButton = findViewById(R.id.settingsButton);
        playButton = findViewById(R.id.playButton);
        blueBox1 = findViewById(R.id.blueBox1);
        blueBox2 = findViewById(R.id.blueBox2);
        blueBox3 = findViewById(R.id.blueBox3);
        blueBox4 = findViewById(R.id.blueBox4);
        blueBox5 = findViewById(R.id.blueBox5);
        greenBox1 = findViewById(R.id.greenBox1);
        greenBox2 = findViewById(R.id.greenBox2);
        greenBox3 = findViewById(R.id.greenBox3);
        greenBox4 = findViewById(R.id.greenBox4);
        greenBox5 = findViewById(R.id.greenBox5);
        yellowBox1 = findViewById(R.id.yellowBox1);
        yellowBox2 = findViewById(R.id.yellowBox2);
        yellowBox3 = findViewById(R.id.yellowBox3);
        yellowBox4 = findViewById(R.id.yellowBox4);
        yellowBox5 = findViewById(R.id.yellowBox5);
        redBox1 = findViewById(R.id.redBox1);
        redBox2 = findViewById(R.id.redBox2);
        redBox3 = findViewById(R.id.redBox3);
        redBox4 = findViewById(R.id.redBox4);
        redBox5 = findViewById(R.id.redBox5);

        //Loading animations:
        Animation box1Anim = AnimationUtils.loadAnimation(this, R.anim.box1_anim);
        Animation box2Anim = AnimationUtils.loadAnimation(this, R.anim.box2_anim);
        Animation box3Anim = AnimationUtils.loadAnimation(this, R.anim.box3_anim);
        Animation box4Anim = AnimationUtils.loadAnimation(this, R.anim.box4_anim);

        //Starting animations:
        blueBox1.startAnimation(box4Anim);
        blueBox2.startAnimation(box4Anim);
        blueBox3.startAnimation(box4Anim);
        blueBox4.startAnimation(box4Anim);
        blueBox5.startAnimation(box4Anim);
        greenBox1.startAnimation(box2Anim);
        greenBox2.startAnimation(box2Anim);
        greenBox3.startAnimation(box2Anim);
        greenBox4.startAnimation(box2Anim);
        greenBox5.startAnimation(box2Anim);
        yellowBox1.startAnimation(box3Anim);
        yellowBox2.startAnimation(box3Anim);
        yellowBox3.startAnimation(box3Anim);
        yellowBox4.startAnimation(box3Anim);
        yellowBox5.startAnimation(box3Anim);
        redBox1.startAnimation(box1Anim);
        redBox2.startAnimation(box1Anim);
        redBox3.startAnimation(box1Anim);
        redBox4.startAnimation(box1Anim);
        redBox5.startAnimation(box1Anim);

        /* This is the onClickListener that allows us to bind the navigation
            button to the function. The function will take the user to the settings
            page of the application. */
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Creates an intent for the activity that we want to navigate to.
                    This will effectively take us to the settings activity. */
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent); //Launches the settings activity
            }
        }); //End of settingsButton listener

        /* This is the onClickListener that allows us to bind the navigation
            button to the function. The function will take the user to the game
            page of the application. */
         playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent); //Launches the game activity
            }
        }); //End of playButton listener
    } //End of onCreate()
} //End of MainActivity Class