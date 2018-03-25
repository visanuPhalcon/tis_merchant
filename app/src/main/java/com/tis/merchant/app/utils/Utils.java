package com.tis.merchant.app.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.DisplayMetrics;
import com.tis.merchant.app.R;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Utils {
    private static AlertDialog dialog;
    private static ProgressDialog progressDialog;

    public static String encodeBitmapToBase64(Bitmap bitmap, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(compressFormat, quality, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64ToBitmap(String input) {
        byte[] decodeByte = Base64.decode(input, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
    }

    public static int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.scaledDensity);
    }


    public static void showMessageDialogOneButton(Context context,
                                                  String title,
                                                  String message,
                                                  @Nullable String btnPositive,
                                                  DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(btnPositive == null ? context.getResources().getString(R.string.label_ok) : btnPositive, onClickListener);



        dialog = builder.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));


    }

    public static void showAlertDialogTwoButton(Context context,
                                                String title,
                                                String message,
                                                @Nullable String btnPositive,
                                                @Nullable String btnNegative,
                                                DialogInterface.OnClickListener btnPositiveListener,
                                                DialogInterface.OnClickListener btnNegativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(btnPositive == null ? context.getResources().getString(R.string.label_ok) : btnPositive, btnPositiveListener)
                .setNegativeButton(btnNegative == null ? context.getResources().getString(R.string.label_cancel) : btnNegative, btnNegativeListener);
        dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }

    public static void showErrorDialog(Context context, String message, String errorCode, DialogInterface.OnClickListener btnPositiveListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setTitle(context.getResources().getString(R.string.label_error_message))
                .setMessage(message + " (" + errorCode + ")")
                .setCancelable(false)
                .setPositiveButton(context.getResources().getString(R.string.label_ok), btnPositiveListener);

        dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));


    }

    public static void showProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.label_loading));
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public static void showProgressDialog(Context context, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public static void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void writeStringToFile(String data) {
        File externalStorageDir = Environment.getExternalStorageDirectory();
        File myFile = new File(externalStorageDir, "MyFileName.txt");

        if (myFile.exists()) {
            try {
                FileOutputStream fostream = new FileOutputStream(myFile);
                OutputStreamWriter oswriter = new OutputStreamWriter(fostream);
                BufferedWriter bwriter = new BufferedWriter(oswriter);
                bwriter.write(data);
                bwriter.newLine();
                bwriter.close();
                oswriter.close();
                fostream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getDateFormatFromServer(String date) {
        SimpleDateFormat serverDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        Date formattedDate;
        try {
            formattedDate = serverDateFormat.parse(date);
        } catch (ParseException e) {
            formattedDate = new Date();
        }
        return simpleDateFormat.format(formattedDate);
    }
}
