package com.nightvisionmedia.emergencyapp.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.nightvisionmedia.emergencyapp.R;

/**
 * Created by Omar (GAZAMAN) Myers on 6/13/2017.
 */

public class DialogHandler {
    public static class ErrorInForm extends DialogFragment {
        private String message;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            Bundle bundle = getArguments();
            message = bundle.getString("message");
            builder.setTitle("ERROR");
            builder.setMessage(message);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            Dialog dialog = builder.create();
            return dialog;
        }

    }


    public static class SaveAutomaticLogin extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Auto Login");
            builder.setMessage("Would you like to automatically login upon app start up?");
            builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPrefManager.getInstance(getActivity().getApplicationContext()).saveAutomaticLogin(0);
                    SharedPrefManager.getInstance(getActivity().getApplicationContext()).loginScreenSaveAutoLoginShown(1);
                    Message.longToast(getActivity().getApplicationContext(),"Automatic login can be enabled in the settings");
                }
            });
            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPrefManager.getInstance(getActivity().getApplicationContext()).saveAutomaticLogin(1);
                    SharedPrefManager.getInstance(getActivity().getApplicationContext()).loginScreenSaveAutoLoginShown(1);
                }
            });
            Dialog dialog = builder.create();

            return dialog;
        }
    }
}
