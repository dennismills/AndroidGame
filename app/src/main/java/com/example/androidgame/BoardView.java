package com.example.androidgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class BoardView extends View { //Custom View Class
    private ShapeDrawable drawable; //ShapeDrawable object (represents 1 square in the grid)

    //2D ArrayList where each inner list represents a row of grid squares:
    public ArrayList<ArrayList<ShapeDrawable>> floodGrid = new ArrayList<ArrayList<ShapeDrawable>>();
    int gridSize = 10; //gridSize is 10 by default
    //Color values as strings so we can use parseColor method to set each square's color:
    String colorGreen = "#FF00D500";
    String colorBlue = "#FF0C0CC0";
    String colorYellow = "#FFFFF700";
    String colorRed = "#FFFF0000";

    @Override
    public void onDraw(Canvas canvas) { //Inner class to draw the ShapeDrawable grid
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                floodGrid.get(i).get(j).draw(canvas);
            }
        }
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int x; //Upper left-hand corner x-coordinate
        int y = 0; //Upper right-hand corner y-coordinate
        //Default width and height (84 * 10 = 840):
        int width = 84;
        int height = 84;

        if (gridSize == 14) { // 840 / 14 = 60
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
            floodGrid.add(new ArrayList<ShapeDrawable>()); //Add ArrayList to 2D ArrayList for each row
            Random rand = new Random();
            int randNum;

            for (int j = 0; j < gridSize; j++) {
                x = x + width; //Increment startings x-coordinate for each column
                drawable = new ShapeDrawable(new RectShape()); //Shape object (represents 1 square in the grid)
                randNum = rand.nextInt(4); //Generate random number between 0-3
                //Set the square color according to the random number generated:
                if (randNum == 0) { //Set color to green
                    drawable.getPaint().setColor(Color.parseColor(colorGreen));
                } else if (randNum == 1) { //Set color to blue
                    drawable.getPaint().setColor(Color.parseColor(colorBlue));
                } else if (randNum == 2) { //Set color to yellow
                    drawable.getPaint().setColor(Color.parseColor(colorYellow));
                } else { //Set color to red
                    drawable.getPaint().setColor(Color.parseColor(colorRed));
                }

                //Set the bounds:
                drawable.setBounds(x, y, x + width, y + height);
                //Add square to the 2D ArrayList:
                floodGrid.get(i).add(j, drawable);
            }
        }
    }

    public void setGridSize(int size) { //Updates the grid size; creates new grid
        gridSize = size; //Update size
        floodGrid.clear(); //Clear the existing grid

        int x; //Upper left-hand corner x-coordinate
        int y = 0; //Upper right-hand corner y-coordinate
        //Default width and height (60 * 14 = 840):
        int width = 84;
        int height = 84;

        if (gridSize == 14) { // 840 / 14 = 60
            width = 60;
            height = 60;
        } else if (gridSize == 18) { //840 / 18 = 47
            width = 47;
            height = 47;
        }

        for (int i = 0; i < gridSize; i++) {
            x = 50; //Start all rows at same x-coordinate
            y = y + height; //Increment starting y-coordinate for each row
            floodGrid.add(new ArrayList<ShapeDrawable>()); //Add ArrayList to 2D ArrayList for each row
            Random rand = new Random();
            int randNum;

            for (int j = 0; j < gridSize; j++) {
                x = x + width; //Increment startings x-coordinate for each column
                drawable = new ShapeDrawable(new RectShape()); //Shape object (represents 1 square in the grid)
                randNum = rand.nextInt(4); //Generate random number between 0-3
                //Set the square color according to the random number generated:
                if (randNum == 0) { //Set color to green
                    drawable.getPaint().setColor(Color.parseColor(colorGreen));
                }
                else if (randNum == 1) { //Set color to blue
                    drawable.getPaint().setColor(Color.parseColor(colorBlue));
                }
                else if (randNum == 2) { //Set color to yellow
                    drawable.getPaint().setColor(Color.parseColor(colorYellow));
                }
                else { //Set color to red
                    drawable.getPaint().setColor(Color.parseColor(colorRed));
                }

                //Set the bounds:
                drawable.setBounds(x, y, x + width, y + height);
                //Add square to the 2D ArrayList:
                floodGrid.get(i).add(j, drawable);
            }
        }

    }

    public String getSquareColor(int row, int column) { //Function to return the square color for any given square
        int colorInt = floodGrid.get(row).get(column).getPaint().getColor();
        String color = Integer.toHexString(colorInt);
        color = color.toUpperCase();
        color = "#" + color;
        return color;
    }

    public void setSquareColor(int row, int column, String newColor) { //Function to set the square color for any given square
        floodGrid.get(row).get(column).getPaint().setColor(Color.parseColor(newColor));

    }

    public String getProgress() { //Function to retrieve the progress on the board
        //Progress on the board is saved as a string where each char represents
        // the color of the corresponding square in the grid.
        StringBuilder boardProgress = new StringBuilder();
        String c;
        char v;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                c = getSquareColor(i, j);
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
                boardProgress.append(v);
            }
        }
        return boardProgress.toString();
    }

    public void setProgress(String boardProgress) { //Function to set the progress on the board
        //Progress on the board is saved as a string where each char represents
        // the color of the corresponding square in the grid. The string can be
        // 'decoded' to set the saved progress on the board.
        char c;
        int index = 0;
        Log.i("Info", Integer.toString(gridSize));
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                c = boardProgress.charAt(index);
                index++;
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
    }
}
