package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private BoardView BoardView;
    private Button blueButton;
    private Button greenButton;
    private Button redButton;
    private Button yellowButton;

    private int numberOfTurns;
    private int turnsRemaining;
    private boolean hasWon = false;
    private boolean isGameOver = false;

    String colorGreen = "#FF00D500";
    String colorBlue = "#FF0C0CC0";
    String colorYellow = "#FFFFF700";
    String colorRed = "#FFFF0000";

    /**
     * @desc:
     *  This function will perfom the flood fill algorithm for the board when
     *  a color is changed.
     *
     * @param x:
     *  This is the column that we currently are targeting in the iteration
     *  of the flood fill algorithm.
     *
     * @param y:
     *  This is the row that we are currently targeting in the iteration of
     *  the flood fill algorithm.
     *
     * @param oldColor:
     *  This is the color of the grid at (0, 0) when the button is pressed.
     *  It is passed recursively down the subsequent calls because after the first
     *  call to this function, it will be overwritten.
     *
     * @param newColor:
     *  This is the new color that we are filling the grid with.
     */
    public void floodFill(int x, int y, String oldColor, String newColor)
    {
        /*
            Below I just do some basic bounds checking to make sure that
            we're inside of the grid when before we try to fill the current
            cell.
        */
        if (x < 0 || x >= BoardView.gridSize || y < 0 || y >= BoardView.gridSize)
        {
            return; // If we are not in bounds of the grid, return
        }

        /*
            In the event that we come across a square that doesn't match
            the old color, we know we are now at a boundary and must return
        */
        if (!BoardView.getSquareColor(x, y).equals(oldColor))
        {
            return; // returns if we reach a new color boundary
        }

        /*
            In the event that we get to a square that is the same color
            as the one we trying to fill it with, there is no need to do
            anything else.
        */
        if (BoardView.getSquareColor(x, y).equals(newColor))
        {
            return; // Returns if the reach the same color we are filling with
        }

        BoardView.setSquareColor(x, y, newColor); // Sets the target square color
        floodFill(x + 1, y, oldColor, newColor); // Recursively fills to the right
        floodFill(x - 1, y, oldColor, newColor); // Recursively fills to the left
        floodFill(x, y + 1, oldColor, newColor); // Recursively fills above
        floodFill(x, y - 1, oldColor, newColor); // Recursively fills below
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        BoardView = findViewById(R.id.BoardView);
        blueButton = findViewById(R.id.blueButton);
        greenButton = findViewById(R.id.greenButton);
        redButton = findViewById(R.id.redButton);
        yellowButton = findViewById(R.id.yellowButton);

        TextView gridSizeText = findViewById(R.id.gridSizeDisplay);
        String stringRepOfGridSize = "" + BoardView.gridSize;
        gridSizeText.setText(stringRepOfGridSize);

        switch (BoardView.gridSize)
        {
            // TODO: Add the other grid sizes and the number of turns for each of them
            case 14:
                numberOfTurns = 11;
                turnsRemaining = numberOfTurns;
                break;
            default:
                numberOfTurns = 11;
                turnsRemaining = numberOfTurns;
                break;
        }

        TextView numberOfAllocatedTurns = findViewById(R.id.totTurnsDisplay);
        String stringRepOfAllocatedTurns = "" + numberOfTurns;
        numberOfAllocatedTurns.setText(stringRepOfAllocatedTurns);

        TextView turnsRemainingText = findViewById(R.id.numTurnsDisplay);

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGameOver)
                {
                    String oldColor = BoardView.getSquareColor(0, 0);
                    floodFill(0, 0, oldColor, colorBlue);
                    turnsRemaining--;
                }

                if (turnsRemaining > 0)
                {
                    String stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    //TODO: Check for winning game
                }
                else
                {
                    //TODO: Dialog fragment with game over screen
                    hasWon = false;
                    isGameOver = true;
                    String stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                }
                BoardView.invalidate(); //Redraws grid after changes
                //Do something when user clicks the blueButton
            }
        });

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGameOver)
                {
                    String oldColor = BoardView.getSquareColor(0, 0);
                    floodFill(0, 0, oldColor, colorGreen);
                    turnsRemaining--;
                }

                if (turnsRemaining > 0)
                {
                    String stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    //TODO: Check for winning game
                }
                else
                {
                    //TODO: Dialog fragment with game over screen
                    hasWon = false;
                    isGameOver = true;
                    String stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                }
                BoardView.invalidate(); //Redraws grid after changes
            }
        });

        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGameOver)
                {
                    String oldColor = BoardView.getSquareColor(0, 0);
                    floodFill(0, 0, oldColor, colorRed);
                    turnsRemaining--;
                }

                if (turnsRemaining > 0)
                {
                    String stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    //TODO: Check for winning game
                }
                else
                {
                    //TODO: Dialog fragment with game over screen
                    hasWon = false;
                    isGameOver = true;
                    String stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                }
                BoardView.invalidate(); //Redraws grid after changes
            }
        });

        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGameOver)
                {
                    String oldColor = BoardView.getSquareColor(0, 0);
                    floodFill(0, 0, oldColor, colorYellow);
                    turnsRemaining--;
                }

                if (turnsRemaining > 0)
                {
                    String stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    //TODO: Check for winning game
                }
                else
                {
                    //TODO: Dialog fragment with game over screen
                    hasWon = false;
                    isGameOver = true;
                    String stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                }
                BoardView.invalidate(); //Redraws grid after changes
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
