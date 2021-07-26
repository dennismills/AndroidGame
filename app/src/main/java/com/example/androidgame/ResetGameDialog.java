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

public class ResetGameDialog extends DialogFragment
{
    private boolean gameWinningStatus;
    private GameActivity activity;

    public ResetGameDialog(boolean gameWinningStatus, GameActivity activity)
    {
        this.gameWinningStatus = gameWinningStatus;
        this.activity = activity;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflator = getActivity().getLayoutInflater();

        View dialogView = inflator.inflate(R.layout.activity_reset_game, null);
        TextView gameStatusTextView = dialogView.findViewById(R.id.gameStatusText);

        Button resetGameButton = dialogView.findViewById(R.id.resetGameButton);
        Button mainMenuButton = dialogView.findViewById(R.id.mainMenuButton);

        resetGameButton.setOnClickListener(v ->
        {
            activity.resetGame();
            this.dismiss();
        });
        mainMenuButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            this.dismiss();
            startActivity(intent);
        });

        if (gameWinningStatus == true)
        {
            gameStatusTextView.setText("WON");
            gameStatusTextView.setTextColor(Color.GREEN);
        }
        else
        {
            gameStatusTextView.setText("LOST");
            gameStatusTextView.setTextColor(Color.RED);
        }
        builder.setView(dialogView);
        return builder.create();
    }
}
