package com.example.androidgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ResetGameDialog extends DialogFragment {

    //Visible elements:
    private Button resetGameButton;
    private Button mainMenuButton;
    private TextView gameStatusTextView;

    private boolean gameWinningStatus;
    private GameActivity activity;


    //Constructor for dialog:
    public ResetGameDialog(boolean gameWinningStatus, GameActivity activity) {
        this.gameWinningStatus = gameWinningStatus;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        //Display custom dialog layout:
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflator = getActivity().getLayoutInflater();
        View dialogView = inflator.inflate(R.layout.dialog_reset_game, null);

        //Getting references to items in the layout:
        resetGameButton = dialogView.findViewById(R.id.resetGameButton);
        mainMenuButton = dialogView.findViewById(R.id.mainMenuButton);
        gameStatusTextView = dialogView.findViewById(R.id.gameStatusText);

        resetGameButton.setOnClickListener(v -> {
            activity.resetGame(); //Reset the game if the user selects reset game
            this.dismiss(); //Dismiss the dialog
        }); //End of resetGameButton listener

        mainMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            this.dismiss(); //Dismiss the dialog
            startActivity(intent); //Take the user to the main menu if the user selects main menu
        }); //End of mainMenuButton listener

        //Set text to indicate win or loss:
        if (gameWinningStatus == true) {
            gameStatusTextView.setText("WON");
            gameStatusTextView.setTextColor(Color.GREEN);
        }
        else {
            gameStatusTextView.setText("LOST");
            gameStatusTextView.setTextColor(Color.RED);
        }
        builder.setView(dialogView);
        return builder.create();
    } //End of onCreateDialog()
} //End of ResetGameDialog Class