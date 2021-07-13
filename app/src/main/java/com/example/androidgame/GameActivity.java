package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    BoardView BoardView;

    //2D ArrayList where each inner list represents a row of grid squares:
    public ArrayList<ArrayList<ShapeDrawable>> floodGrid = new ArrayList<ArrayList<ShapeDrawable>>();
    int gridSize = 14; //gridSize is 14 by default
    //Color values as strings so we can use parseColor method to set each square's color:
    String colorGreen = "#00D500";
    String colorBlue = "#0C0CC0";
    String colorYellow = "#FFF700";
    String colorRed = "#FF0000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        BoardView = new BoardView(this, gridSize);
        //Display view:
        setContentView(BoardView); //This method display the custom view BoardView, but it takes up the entire screen
        //Need an alternative method to display/embed the custom view within the layout or an XML reference to the custom view
        //XML reference to this custom view causing program to crash (see bottom of activity_main.xml)
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //Tried making BoardView Class static to fix XML reference issue
    //In this version I have removed the static keyword
    //and local copies of color, gridSize, and 2D ArrayList variables necessary when using static keyword

    public class BoardView extends View { //Custom View Class
        private ShapeDrawable drawable; //ShapeDrawable object (represents 1 square in the grid)

        @Override
        public void onDraw(Canvas canvas) { //Inner class to draw the ShapeDrawable grid
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    floodGrid.get(i).get(j).draw(canvas);
                }
            }
        }

        //Tried to override onMeasure to limit the size of the custom view
        // but I don't think this has any effect while using setContentView method to display the custom view

        /*protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(560, 560);
        }*/

        //Tried adding these constructors to fix layout inflate error given when BoardView is made static (solution found online)
        // gave me a null pointer exception

        /*public  BoardView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
        public  BoardView(Context context) {
            super(context);
        }*/

        public BoardView(Context context, int gridSize) {
            super(context);
            int x = 30; //Upper left-hand corner x-coordinate
            int y = 120; //Upper right-hand corner y-coordinate
            //Default width and height (40 * 14 = 560):
            int width = 40;
            int height = 40;

            if (gridSize == 10) { // 560 / 10 = 56
                width = 56;
                height = 56;
            }
            else if (gridSize == 18) { //560 / 18 = 31
                width = 31;
                height = 31;
            }
            else { //Do nothing; gridSize is default 14
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
                    floodGrid.get(i).add(0, drawable);
                }
            }
        }
    }
}
