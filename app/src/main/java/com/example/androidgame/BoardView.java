package com.example.androidgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;

public class BoardView extends View { //Custom View Class

    private ShapeDrawable drawable; //ShapeDrawable object (represents 1 square in the grid)

    //2D ArrayList where each inner list represents a row of grid squares:
    public ArrayList<ArrayList<ShapeDrawable>> floodGrid = new ArrayList<ArrayList<ShapeDrawable>>();
    int gridSize = 10; //gridSize is 10 by default

    //Color values as strings so we can use parseColor() method to set each square's color:
    String colorGreen = "#FF00D500";
    String colorBlue = "#FF0C0CC0";
    String colorYellow = "#FFFFF700";
    String colorRed = "#FFFF0000";


    /**
     * @desc: The onDraw() method draws the drawable objects to the canvas.
     *
     * @param canvas: The canvas to be used for drawing.
     */
    @Override
    public void onDraw(Canvas canvas) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                floodGrid.get(i).get(j).draw(canvas);
            }
        }
    } //End of onDraw()

    /**
     * @desc: This is the constructor for the BoardView Class. It overrides the
     *  View(Context, AttributeSet) constructor. It is called when the custom view is
     *  inflated from XML.
     *
     * @param context: The Context the view is running in.
     *
     * @param attrs: The attributes of the XML tag ued to inflate the view.
     */
    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        int x; //Upper left-hand corner x-coordinate
        int y = 0; //Upper right-hand corner y-coordinate
        //Default width and height (84 * 10 = 840):
        int width = 84;
        int height = 84;

        if (gridSize == 14) { //840 / 14 = 60
            width = 60;
            height = 60;
        }
        else if (gridSize == 18) { //840 / 18 = 47
            width = 47;
            height = 47;
        }

        for (int i = 0; i < gridSize; i++) {
            x = 50; //Start all rows at same x-coordinate
            y = y + height; //Increment starting y-coordinate for each row
            //Add ArrayList to 2D ArrayList for each row:
            floodGrid.add(new ArrayList<ShapeDrawable>());
            Random rand = new Random(); //Random Class instance (for generating random numbers)
            int randNum;

            for (int j = 0; j < gridSize; j++) {
                x = x + width; //Increment starting x-coordinate for each column
                drawable = new ShapeDrawable(new RectShape()); //Represents 1 square in the grid
                randNum = rand.nextInt(4); //Generate random number between 0-3
                //Set the square color according to the random number generated:
                if (randNum == 0) { //Set color to green:
                    drawable.getPaint().setColor(Color.parseColor(colorGreen));
                }
                else if (randNum == 1) { //Set color to blue:
                    drawable.getPaint().setColor(Color.parseColor(colorBlue));
                }
                else if (randNum == 2) { //Set color to yellow:
                    drawable.getPaint().setColor(Color.parseColor(colorYellow));
                }
                else { //Set color to red:
                    drawable.getPaint().setColor(Color.parseColor(colorRed));
                }

                //Set the bounds:
                drawable.setBounds(x, y, x + width, y + height);
                //Add square to the 2D ArrayList:
                floodGrid.get(i).add(j, drawable);
            }
        }
    } //End of BoardView()

    /**
     * @desc: This method will update the gridSize for the custom view. This method creates
     *  a new grid using the size parameter.
     *
     * @param size: New size.
     */
    public void setGridSize(int size) {
        gridSize = size; //Update size from paramter
        floodGrid.clear(); //Clear the existing grid

        int x; //Upper left-hand corner x-coordinate
        int y = 0; //Upper right-hand corner y-coordinate
        //Default width and height (60 * 14 = 840):
        int width = 84;
        int height = 84;

        if (gridSize == 14) { //840 / 14 = 60
            width = 60;
            height = 60;
        }
        else if (gridSize == 18) { //840 / 18 = 47
            width = 47;
            height = 47;
        }

        for (int i = 0; i < gridSize; i++) {
            x = 50; //Start all rows at same x-coordinate
            y = y + height; //Increment starting y-coordinate for each row
            //Add ArrayList to 2D ArrayList for each row:
            floodGrid.add(new ArrayList<ShapeDrawable>());
            Random rand = new Random(); //Random Class instance (for generating random numbers)
            int randNum;

            for (int j = 0; j < gridSize; j++) {
                x = x + width; //Increment starting x-coordinate for each column
                drawable = new ShapeDrawable(new RectShape()); //Represents 1 square in the grid
                randNum = rand.nextInt(4); //Generate random number between 0-3
                //Set the square color according to the random number generated:
                if (randNum == 0) { //Set color to green:
                    drawable.getPaint().setColor(Color.parseColor(colorGreen));
                }
                else if (randNum == 1) { //Set color to blue:
                    drawable.getPaint().setColor(Color.parseColor(colorBlue));
                }
                else if (randNum == 2) { //Set color to yellow:
                    drawable.getPaint().setColor(Color.parseColor(colorYellow));
                }
                else { //Set color to red:
                    drawable.getPaint().setColor(Color.parseColor(colorRed));
                }

                //Set the bounds:
                drawable.setBounds(x, y, x + width, y + height);
                //Add square to the 2D ArrayList:
                floodGrid.get(i).add(j, drawable);
            }
        }
    } //End of setGridSize()

    /**
     * @desc: This function will return the square color for any given square as a string.
     *
     * @param row: Row coordinate of the given square.
     *
     * @param column: Column coordinate of the given square.
     */
    public String getSquareColor(int row, int column) {
        int colorInt = floodGrid.get(row).get(column).getPaint().getColor(); //Get the color
        //Convert the integer returned by getColor to a string:
        String color = Integer.toHexString(colorInt);
        color = color.toUpperCase(); //Convert to uppercase
        color = "#" + color; //Add # to get desired format
        return color;
    } //End of getSquareColor()

    /**
     * @desc: This function will set the square color for any given square.
     *
     * @param row: Row coordinate of the given square.
     *
     * @param column: Column coordinate of the given square.
     *
     * @param newColor: String tha represents the color to change the square to.
     *                The parseColor() function will be used to convert the string.
     */
    public void setSquareColor(int row, int column, String newColor) {
        floodGrid.get(row).get(column).getPaint().setColor(Color.parseColor(newColor));
    } //End of setSquareColor()

    /**
     * @desc: The getProgress() method gets the progress on the grid. In other words, it
     *  gets the current state of the grid. It returns a string that is a sequence of
     *  characters, each of which represents the color of a given square on the grid.
     *  This string can be 'decoded' to set the grid according to the progress saved.
     */
    public String getProgress() {
        StringBuilder boardProgress = new StringBuilder(); //Used to build a sequence of characters
        String c;
        char v;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                c = getSquareColor(i, j); //Get the color for each square on the board
                //Set the char in the sequence according to the color:
                if (c.equals(colorGreen)) { //Green
                    v = 'G';
                }
                else if (c.equals(colorBlue)) { //Blue
                    v = 'B';
                }
                else if (c.equals(colorYellow)) { //Yellow
                    v = 'Y';
                }
                else { //Red
                    v = 'R';
                }
                boardProgress.append(v); //Appends the char to the sequence
            }
        }
        return boardProgress.toString(); //Returns the sequence of chars as a string.
    } //End of getProgress()

    /**
     * @desc: This function will 'decode' the boardProgress string to set the saved progress
     *  on the grid. The boardProgress string is a sequence of chars where each char represents
     *  the color of a given square on the grid.
     *
     * @param boardProgress: String representing the saved progress on the grid.
     */
    public void setProgress(String boardProgress) {
        char c;
        int index = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                //Get the color for the given square from the sequence:
                c = boardProgress.charAt(index);
                index++; //Increments to next char in the sequence
                //Set the square color on the grid according to the char from the sequence:
                if (c == 'G') { //Green
                    setSquareColor(i, j, colorGreen);
                }
                else if (c == 'B') { //Blue
                    setSquareColor(i, j, colorBlue);
                }
                else if (c == 'Y') { //Yellow
                    setSquareColor(i, j, colorYellow);
                }
                else { //Red
                    setSquareColor(i, j, colorRed);
                }
            }
        }
    } //End of setProgress()
} //End of BoardView Class
