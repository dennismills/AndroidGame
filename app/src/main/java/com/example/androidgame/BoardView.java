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
    int gridSize = 14; //gridSize is 14 by default
    //Color values as strings so we can use parseColor method to set each square's color:
    String colorGreen = "#00D500";
    String colorBlue = "#0C0CC0";
    String colorYellow = "#FFF700";
    String colorRed = "#FF0000";

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
        int x = 30; //Upper left-hand corner x-coordinate
        int y = 120; //Upper right-hand corner y-coordinate
        //Default width and height (40 * 14 = 560):
        int width = 40;
        int height = 40;

        if (gridSize == 10) { // 560 / 10 = 56
            width = 56;
            height = 56;
        } else if (gridSize == 18) { //560 / 18 = 31
            width = 31;
            height = 31;
        } else { //Do nothing; gridSize is default 14
        }

        for (int i = 0; i < gridSize; i++) {
            x = 30; //Start all rows at same x-coordinate
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
                floodGrid.get(i).add(0, drawable);
            }
        }
    }

    public ArrayList<ArrayList<ShapeDrawable>> getFloodGrid() { //Should allow us to get a reference to each board square
        return floodGrid;
    }

    public void setGridSize(int size) { //Updates the grid size; creates new grid
        gridSize = size; //Update size
        floodGrid.clear(); //Clear the existing grid

        int x = 30; //Upper left-hand corner x-coordinate
        int y = 120; //Upper right-hand corner y-coordinate
        //Default width and height (40 * 14 = 560):
        int width = 40;
        int height = 40;

        if (gridSize == 10) { // 560 / 10 = 56
            width = 56;
            height = 56;
        } else if (gridSize == 18) { //560 / 18 = 31
            width = 31;
            height = 31;
        } else { //Do nothing; gridSize is default 14
        }

        for (int i = 0; i < gridSize; i++) {
            x = 30; //Start all rows at same x-coordinate
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
                floodGrid.get(i).add(0, drawable);
            }
        }

    }
}
