package com.example.androidgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private BoardView BoardView;
    private Button blueButton;
    private Button greenButton;
    private Button redButton;
    private Button yellowButton;
    private TextView gridSizeDisplay;

    private TextView numberOfAllocatedTurns;
    private String stringRepOfAllocatedTurns;
    private TextView turnsRemainingText;
    private String stringRepOfTurnsRemaining;

    int gridSize = 10;
    private int numberOfTurns;
    private int turnsRemaining;
    private boolean hasWon = false;
    private boolean isGameOver = false;

    String colorGreen = "#FF00D500";
    String colorBlue = "#FF0C0CC0";
    String colorYellow = "#FFFFF700";
    String colorRed = "#FFFF0000";

    SharedPreferences sharedPref;

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
        gridSizeDisplay = findViewById(R.id.gridSizeDisplay);

        //Check the shared preferences for the grid size:
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        gridSize = sharedPref.getInt("gridSize", 10);
        BoardView.setGridSize(gridSize);
        BoardView.invalidate(); //Redraws grid after changes
        //Display grid size text according to the size:
        if (gridSize == 10) {
            gridSizeDisplay.setText(R.string.tenByTen);
        }
        else if (gridSize == 14) {
            gridSizeDisplay.setText(R.string.fourteenByFourteen);
        }
        else if (gridSize == 18) {
            gridSizeDisplay.setText(R.string.eighteenByEighteen);
        }

        //Determine and set the starting number of allocated turns:
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

        //Display the starting number of allocated turns:
        numberOfAllocatedTurns = findViewById(R.id.totTurnsDisplay);
        stringRepOfAllocatedTurns = "" + numberOfTurns;
        numberOfAllocatedTurns.setText(stringRepOfAllocatedTurns);

        //Display the number of remaining turns:
        turnsRemainingText = findViewById(R.id.numTurnsDisplay);
        stringRepOfTurnsRemaining = "" + numberOfTurns;
        turnsRemainingText.setText(stringRepOfTurnsRemaining);

        //Buttons run the game; when a user picks a color button, they are flooding the board with that color.
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
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    //TODO: Check for winning game
                }
                else
                {
                    //TODO: Dialog fragment with game over screen
                    hasWon = false;
                    isGameOver = true;
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                }
                BoardView.invalidate(); //Redraws grid after changes
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
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    //TODO: Check for winning game
                }
                else
                {
                    //TODO: Dialog fragment with game over screen
                    hasWon = false;
                    isGameOver = true;
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
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
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    //TODO: Check for winning game
                }
                else
                {
                    //TODO: Dialog fragment with game over screen
                    hasWon = false;
                    isGameOver = true;
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
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
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    //TODO: Check for winning game
                }
                else
                {
                    //TODO: Dialog fragment with game over screen
                    hasWon = false;
                    isGameOver = true;
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                }
                BoardView.invalidate(); //Redraws grid after changes
            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        gridSize = sharedPref.getInt("gridSize", 10);
        BoardView.setGridSize(gridSize);
        BoardView.invalidate(); //Redraws grid after changes
        if (gridSize == 10) {
            gridSizeDisplay.setText(R.string.tenByTen);
        }
        else if (gridSize == 14) {
            gridSizeDisplay.setText(R.string.fourteenByFourteen);
        }
        else if (gridSize == 18) {
            gridSizeDisplay.setText(R.string.eighteenByEighteen);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item ) {
        if (item.getItemId() == R.id.action_settings) {
            //Open settings if user selects the settings option from the app bar:
            //TODO: Add code to save the game state; game will be reset if user changes the board size in the settings
            Intent intent = new Intent (this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.action_startmenu) {
            //Exit to start menu if user selects the start menu option from the app bar:
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        //Log.d("OnOptionsItemSelected", "Returning False"); //Debugging
        return super.onOptionsItemSelected(item);
    }
}
