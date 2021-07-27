package com.example.androidgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ResizeWarningDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        //Set attributes:
        builder.setTitle(R.string.resize_warning);
        builder.setMessage(R.string.resize_warning_message);
        builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Dismiss the dialog when the user clicks 'Okay':
                dismiss();
            }
        }); //End of onClick listener
        return builder.create();
    } //End of onCreateDialog()
} //End of ResizeWarningDialog Class