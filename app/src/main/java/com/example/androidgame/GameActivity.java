package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    private BoardView BoardView;
    private Button blueButton;
    private Button greenButton;
    private Button redButton;
    private Button yellowButton;

    String colorGreen = "#FF00D500";
    String colorBlue = "#FF0C0CC0";
    String colorYellow = "#FFFFF700";
    String colorRed = "#FFFF0000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        BoardView = findViewById(R.id.BoardView);
        blueButton = findViewById(R.id.blueButton);
        greenButton = findViewById(R.id.greenButton);
        redButton = findViewById(R.id.redButton);
        yellowButton = findViewById(R.id.yellowButton);

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardView.setSquareColor(0, 0, colorBlue);
                BoardView.invalidate(); //Redraws grid after changes
                //Do something when user clicks the blueButton
            }
        });

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardView.setSquareColor(0, 0, colorGreen);
                BoardView.invalidate(); //Redraws grid after changes
            }
        });

        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardView.setSquareColor(0, 0, colorRed);
                BoardView.invalidate(); //Redraws grid after changes
            }
        });

        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardView.setSquareColor(0, 0, colorYellow);
                BoardView.invalidate(); //Redraws grid after changes
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
