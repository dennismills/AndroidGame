package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    int gridSize = 14;
    private BoardView BoardView;
    private Button blueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        BoardView = findViewById(R.id.BoardView);
        blueButton = findViewById(R.id.blueButton);

        //Using this to test trying to update the gridSize and redraw the grid:
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardView.setGridSize(18);
                BoardView.invalidate(); //Redraws the object
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
