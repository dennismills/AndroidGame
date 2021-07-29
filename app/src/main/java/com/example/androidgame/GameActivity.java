package com.example.androidgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    //Visible elements & related variables:
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

    //Variables necessary to running the game play:
    private int numberOfTurns;
    private int turnsRemaining;
    private boolean hasWon = false;
    private boolean isGameOver = false;
    private boolean soundsOn = true;

    String colorGreen = "#FF00D500";
    String colorBlue = "#FF0C0CC0";
    String colorYellow = "#FFFFF700";
    String colorRed = "#FFFF0000";

    SharedPreferences sharedPref; //Shared preferences object
    SharedPreferences.Editor editor; //Shared preferences editor object


    /**
     * @desc: This function will perform the flood fill algorithm for the board when
     *  a color is changed.
     *
     * @param x: This is the column that we currently are targeting in the iteration
     *  of the flood fill algorithm.
     *
     * @param y: This is the row that we are currently targeting in the iteration of
     *  the flood fill algorithm.
     *
     * @param oldColor: This is the color of the grid at (0, 0) when the button is pressed.
     *  It is passed recursively down the subsequent calls because after the first
     *  call to this function, it will be overwritten.
     *
     * @param newColor: This is the new color that we are filling the grid with.
     */
    public void floodFill(int x, int y, String oldColor, String newColor) {
        /* Below I just do some basic bounds checking to make sure that
            we're inside of the grid when before we try to fill the current
            cell. */
        if (x < 0 || x >= BoardView.gridSize || y < 0 || y >= BoardView.gridSize) {
            return; //If we are not in bounds of the grid, return
        }

        /* In the event that we come across a square that doesn't match
            the old color, we know we are now at a boundary and must return. */
        if (!BoardView.getSquareColor(x, y).equals(oldColor)) {
            return; //Returns if we reach a new color boundary
        }

        /* In the event that we get to a square that is the same color
            as the one we trying to fill it with, there is no need to do
            anything else. */
        if (BoardView.getSquareColor(x, y).equals(newColor)) {
            return; //Returns if the reach the same color we are filling with
        }

        BoardView.setSquareColor(x, y, newColor); //Sets the target square color
        floodFill(x + 1, y, oldColor, newColor); //Recursively fills to the right
        floodFill(x - 1, y, oldColor, newColor); //Recursively fills to the left
        floodFill(x, y + 1, oldColor, newColor); //Recursively fills above
        floodFill(x, y - 1, oldColor, newColor); //Recursively fills below
    } //End of floodFill()

    /**
     * @desc: This function resets the state of the game. It should always be called to
     *  reset the game and also should always be called if the gridSize has been changed
     *  in the settings. This function will get the gridSize from the shared preferences
     *  and reset the BoardView accordingly, it will reset boolean variables used to track
     *  the game status, it will reset the total number of turns and the number of turns
     *  remaining according to the gridSize, and it will save the new game state in the
     *  shared preferences.
     */
    public void resetGame() { //Always called to reset game or if the board size has changed
        //Check the shared preferences to get the board size:
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        BoardView.setGridSize(sharedPref.getInt("gridSize", 10));
        BoardView.invalidate(); //Redraws grid after changes

        //Set isGameOver and hasWon to false:
        isGameOver = false;
        hasWon = false;

        //Set the number of turns & the number of turns remaining:
        switch (BoardView.gridSize) {
            case 14:
                numberOfTurns = 18;
                turnsRemaining = numberOfTurns;
                break;
            case 18:
                numberOfTurns = 24;
                turnsRemaining = numberOfTurns;
                break;
            default: //Covers gridSize == 10
                numberOfTurns = 13;
                turnsRemaining = numberOfTurns;
                break;
        }

        //Update displays:
        updateDisplays();

        saveGame(); /* Saved the reset game
                    (prevents out-of-bounds exception in BoardView.setProgress()) */
    } //End of resetGame()

    /**
     * @desc: This function will save the game state in the shared preferences.
     */
public void saveGame() {
        /* Use the Preference Manager to open the default shared preference file,
            then create an editor: */
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();

        //Save the board size, the number of turns remaining. and the board progress:
        editor.putInt("gridSize", BoardView.gridSize);
        editor.putInt("turnsRemaining", turnsRemaining);
        String progress = BoardView.getProgress();
        editor.putString("boardProgress", progress);
        editor.apply(); //Apply changes
    } //End of saveGame()

    /**
     * @desc: This function will load a saved game from the shared preferences.
     */
    public void loadGame() {
        //Use the Preference Manager to open the default shared preference file:
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        /* Get the board size (if the board size has changed in the settings,
            resetGame will be called - checking the board size here  just ensures
            nothing weird happens with the drawable): */
        BoardView.setGridSize(sharedPref.getInt("gridSize", 10));

        //Determine the number of turns:
        switch (BoardView.gridSize) {
            case 14:
                numberOfTurns = 18;
                break;
            case 18:
                numberOfTurns = 24;
                break;
            default: //Covers gridSize == 10
                numberOfTurns = 13;
                break;
        }

        //Get the number of turns remaining:
        turnsRemaining = sharedPref.getInt("turnsRemaining", turnsRemaining);

        //Update displays:
        updateDisplays();

        //Get the board progress:
        String boardProgress = sharedPref.getString("boardProgress", "RESET");
        if (boardProgress.equals("RESET")) {
            //If default value is returned, board progress was not saved. Must reset.
            resetGame();
        }
        else {
            BoardView.setProgress(boardProgress);
        }

        BoardView.invalidate(); //Redraws grid after changes
    } //End of loadGame()

    /**
     * @desc: This function checks the settings to see if the gridSize has been changed and
     * to see if a saved game exists. This function should be called whenever the activity
     * is created or resumed to ensure 1) checking the settings for a gridSize change and
     * 2) checking the settings for an existing game.
     */
    public void checkSettings() {
        /* Use the Preference Manager to open the default shared preference file,
            then create an editor: */
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();

        //Set boolean used to control game sound effects:
        soundsOn = sharedPref.getBoolean("soundsOn", true);

        //Get the boolean that indicates the size was just changed from the shared preferences:
        boolean sizeChanged = sharedPref.getBoolean("sizeChanged", true);
        boolean sizeSame = !sizeChanged; //Opposite of sizeChanged
        //Get the number of saved turns from the shared preferences:
        int savedTurns = sharedPref.getInt("turnsRemaining", 0);

        if (sizeChanged) {
            //If the board size has been changed in the settings, always reset the game.
            resetGame();
            editor.putBoolean("sizeChanged", false); //Mark false the indicator the size was changed.
            editor.apply();
        }
        else if (sizeSame && (savedTurns > 0)) {
            /* If the board size hasn't changed, and a game is saved with more
                than 0 turns remaining, load the game. */
            loadGame();
        }
        else { //Otherwise reset.
            resetGame();
        }
    } //End of checkSettings()

    /**
     * @desc: The updateDisplays() function updates the displays for the activity.
     *  This function ensures that after resetting or loading a game, the displays
     *  always show the correct information.
     */
    public void updateDisplays() {
        //Display the grid size:
        if (BoardView.gridSize == 10) {
            gridSizeDisplay.setText(R.string.tenByTen);
        } else if (BoardView.gridSize == 14) {
            gridSizeDisplay.setText(R.string.fourteenByFourteen);
        } else if (BoardView.gridSize == 18) {
            gridSizeDisplay.setText(R.string.eighteenByEighteen);
        }

        //Display the starting number of allocated turns:
        numberOfAllocatedTurns = findViewById(R.id.totTurnsDisplay);
        stringRepOfAllocatedTurns = "" + numberOfTurns;
        numberOfAllocatedTurns.setText(stringRepOfAllocatedTurns);

        //Display the number of remaining turns:
        turnsRemainingText = findViewById(R.id.numTurnsDisplay);
        stringRepOfTurnsRemaining = "" + turnsRemaining;
        turnsRemainingText.setText(stringRepOfTurnsRemaining);
    } //End of updateDisplays

    /**
     * @desc: This function will check if the player has won by checking if the board is
     *  all the same color as the just-flooded color.
     *
     * @param color: Color the player just flooded the board with.
     */
    public void checkForWinner(String color) {
        boolean triggered = false;
        for (int i = 0; i < BoardView.gridSize; i++) {
            for (int j = 0; j < BoardView.gridSize; j++) {
                if (!BoardView.getSquareColor(j, i).equals(color)) {
                    hasWon = false;
                    triggered = true; /* Indicates a square on the board does not
                                            match the flood color. */
                }
            }
        }

        if (!triggered) { //If all squares on the board match the flood color, the player just won.
            hasWon = true;
        }
    } //End of checkForWinner()


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Getting references to items in the layout:
        BoardView = findViewById(R.id.BoardView);
        blueButton = findViewById(R.id.blueButton);
        greenButton = findViewById(R.id.greenButton);
        redButton = findViewById(R.id.redButton);
        yellowButton = findViewById(R.id.yellowButton);
        gridSizeDisplay = findViewById(R.id.gridSizeDisplay);

        //Sound effects:
        SoundPool soundPool; //SoundPool object
        AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        //Instantiate SoundPool object using AudioAttributes object to set the audio attributes:
        soundPool = new SoundPool.Builder().setAudioAttributes(attributes).build();
        //Get ID for the specific sound effect:
        int floodNoiseId = soundPool.load(this, R.raw.flood, 1);

        //Check if there is a saved game (loads the game data):
        checkSettings(); /* checkSettings() will check for a gridSize change and
                            an existing game in the shared preferences. If no game is
                            saved a 'default' game will be loaded via a call to resetGame(). */

        /* Buttons run the game; when a user picks a color button,
            they are flooding the board with that color. */

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGameOver) {
                    /* If the game is not over, flood the board with the chosen color and
                    decrement the number of turns remaining. */
                    String oldColor = BoardView.getSquareColor(0, 0);
                    floodFill(0, 0, oldColor, colorBlue);
                    turnsRemaining--;
                }

                if (turnsRemaining > 0) {
                    /* If the player has turns remaining, update the number of turns remaining
                        and check if they won: */
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    checkForWinner(colorBlue);

                    if (hasWon) {
                        /* If the player won, mark that the game is over,
                            set turns remaining equal to zero, and open the reset game dialog: */
                        isGameOver = true;
                        turnsRemaining = 0;
                        stringRepOfTurnsRemaining = "" + turnsRemaining;
                        turnsRemainingText.setText(stringRepOfTurnsRemaining);
                        ResetGameDialog dialog = new ResetGameDialog(hasWon, GameActivity.this);
                        dialog.show(getSupportFragmentManager(), " ");
                    }
                }
                else {
                    /* If the player has no turns remaining, check if they won,
                        set turns remaining equal to zero, and open the reset game dialog: */
                    checkForWinner(colorBlue);
                    isGameOver = true;
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    ResetGameDialog dialog = new ResetGameDialog(hasWon, GameActivity.this);
                    dialog.show(getSupportFragmentManager(), " ");
                }

                BoardView.invalidate(); //Redraws grid after changes
                saveGame(); //Save game after changes

                if (soundsOn) { //If the sounds are on, play sound effect:
                    soundPool.play(floodNoiseId, 1, 1, 1, 0, 1);
                }
            }
        }); //End of blueButton listener

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGameOver) {
                    /* If the game is not over, flood the board with the chosen color and
                    decrement the number of turns remaining. */
                    String oldColor = BoardView.getSquareColor(0, 0);
                    floodFill(0, 0, oldColor, colorGreen);
                    turnsRemaining--;
                }

                if (turnsRemaining > 0) {
                    /* If the player has turns remaining, update the number of turns remaining
                        and check if they won: */
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    checkForWinner(colorGreen);

                    if (hasWon) {
                        /* If the player won, mark that the game is over,
                            set turns remaining equal to zero, and open the reset game dialog: */
                        isGameOver = true;
                        turnsRemaining = 0;
                        stringRepOfTurnsRemaining = "" + turnsRemaining;
                        turnsRemainingText.setText(stringRepOfTurnsRemaining);
                        ResetGameDialog dialog = new ResetGameDialog(hasWon, GameActivity.this);
                        dialog.show(getSupportFragmentManager(), " ");
                    }
                }
                else {
                    /* If the player has no turns remaining, check if they won,
                        set turns remaining equal to zero, and open the reset game dialog: */
                    checkForWinner(colorGreen);
                    isGameOver = true;
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    ResetGameDialog dialog = new ResetGameDialog(hasWon, GameActivity.this);
                    dialog.show(getSupportFragmentManager(), " ");
                }

                BoardView.invalidate(); //Redraws grid after changes
                saveGame(); //Save game after changes

                if (soundsOn) { //If the sounds are on, play sound effect:
                    soundPool.play(floodNoiseId, 1, 1, 1, 0, 1);
                }
            }
        }); //End of greenButton listener

        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGameOver) {
                    /* If the game is not over, flood the board with the chosen color and
                    decrement the number of turns remaining. */
                    String oldColor = BoardView.getSquareColor(0, 0);
                    floodFill(0, 0, oldColor, colorRed);
                    turnsRemaining--;
                }

                if (turnsRemaining > 0) {
                    /* If the player has turns remaining, update the number of turns remaining
                        and check if they won: */
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    checkForWinner(colorRed);

                    if (hasWon) {
                        /* If the player won, mark that the game is over,
                            set turns remaining equal to zero, and open the reset game dialog: */
                        isGameOver = true;
                        turnsRemaining = 0;
                        stringRepOfTurnsRemaining = "" + turnsRemaining;
                        turnsRemainingText.setText(stringRepOfTurnsRemaining);
                        ResetGameDialog dialog = new ResetGameDialog(hasWon, GameActivity.this);
                        dialog.show(getSupportFragmentManager(), " ");
                    }
                }
                else {
                    /* If the player has no turns remaining, check if they won,
                        set turns remaining equal to zero, and open the reset game dialog: */
                    checkForWinner(colorRed);
                    isGameOver = true;
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    ResetGameDialog dialog = new ResetGameDialog(hasWon, GameActivity.this);
                    dialog.show(getSupportFragmentManager(), " ");
                }
                BoardView.invalidate(); //Redraws grid after changes
                saveGame(); //Save game after changes

                if (soundsOn) {  //If the sounds are on, play sound effect:
                    soundPool.play(floodNoiseId, 1, 1, 1, 0, 1);
                }
            }
        }); //End of redButton listener

        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGameOver) {
                    /* If the game is not over, flood the board with the chosen color and
                    decrement the number of turns remaining. */
                    String oldColor = BoardView.getSquareColor(0, 0);
                    floodFill(0, 0, oldColor, colorYellow);
                    turnsRemaining--;
                }

                if (turnsRemaining > 0) {
                    /* If the player has turns remaining, update the number of turns remaining
                        and check if they won: */
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    checkForWinner(colorYellow);

                    if (hasWon) {
                        /* If the player won, mark that the game is over,
                            set turns remaining equal to zero, and open the reset game dialog: */
                        isGameOver = true;
                        turnsRemaining = 0;
                        stringRepOfTurnsRemaining = "" + turnsRemaining;
                        turnsRemainingText.setText(stringRepOfTurnsRemaining);
                        ResetGameDialog dialog = new ResetGameDialog(hasWon, GameActivity.this);
                        dialog.show(getSupportFragmentManager(), " ");
                    }
                }
                else {
                    /* If the player has no turns remaining, check if they won,
                        set turns remaining equal to zero, and open the reset game dialog: */
                    checkForWinner(colorYellow);
                    isGameOver = true;
                    stringRepOfTurnsRemaining = "" + turnsRemaining;
                    turnsRemainingText.setText(stringRepOfTurnsRemaining);
                    ResetGameDialog dialog = new ResetGameDialog(hasWon, GameActivity.this);
                    dialog.show(getSupportFragmentManager(), " ");
                }

                BoardView.invalidate(); //Redraws grid after changes
                saveGame(); //Save game after changes

                if (soundsOn) { //If the sounds are on, play sound effect:
                    soundPool.play(floodNoiseId, 1, 1, 1, 0, 1);
                }
            }
        }); //End of yellowButton listener
    } //End of onCreate()

    @Override
    protected void onResume() {
        super.onResume();
        checkSettings();
    } //End of onResume()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //Inflates the menu.
        getMenuInflater().inflate(R.menu.menu_bar_game, menu);
        return true;
    } //End of onCreateOptionsMenu()

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item ) { //Handles menu actions.
        if (item.getItemId() == R.id.action_newgame) {
            //Reset the game if the user selects the new game option from the app bar:
            resetGame();
            return true;
        }
        else if (item.getItemId() == R.id.action_settings) {
            //Save the game:
            saveGame();
            //Open settings if user selects the settings option from the app bar:
            Intent intent = new Intent (this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.action_startmenu) {
            //Save the game:
            saveGame();
            //Exit to start menu if user selects the start menu option from the app bar:
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    } //End of onOptionsItemSelected()
} //End of GameActivity Class
