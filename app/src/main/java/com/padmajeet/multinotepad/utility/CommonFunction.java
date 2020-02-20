package com.padmajeet.multinotepad.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.padmajeet.multinotepad.R;

public class CommonFunction {
    private static CommonFunction instance;

    public CommonFunction() {
    }

    public static CommonFunction getInstance() {
        if (instance == null) {
            instance = new CommonFunction();
        }
        return instance;
    }

    public void showAlertDialog(Context activity, String alertMessage, String positiveButtonText, String negativeButtonText, final OnClickListener successClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(alertMessage);
        builder.setCancelable(false);
        builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                successClickListener.OnNegativeButtonClick();
            }
        });
        builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                successClickListener.OnPositiveButtonClick();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        Button nButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nButton.setTextColor(ContextCompat.getColor(activity, R.color.black));
        Button pButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pButton.setTextColor(ContextCompat.getColor(activity, R.color.black));
    }

}
