package com.nor.qldiemdanh.common;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.nor.qldiemdanh.R;

public class DialogUtils {
    private static Dialog dialog;
    public static void showDialogLoading(Context context){
        dismiss();
        dialog = new ProgressDialog.Builder(context)
                .setMessage(R.string.loading)
                .setCancelable(false)
                .create();
        dialog.show();
    }

    public static void showDialogConfirm(Context context, @StringRes int resId, DialogInterface.OnClickListener listener){
        dismiss();
        dialog = new AlertDialog.Builder(context)
                .setMessage(resId)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setCancelable(false)
                .setPositiveButton(R.string.ok, listener).create();
        dialog.show();
    }

    public static void showDialogMessage(Context context, @StringRes int resId, DialogInterface.OnClickListener listener){
        dismiss();
        dialog = new AlertDialog.Builder(context)
                .setMessage(resId)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, listener).create();
        dialog.show();
    }

    public static void dismiss(){
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
