package com.tis.merchant.app.views;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Admin on 7/9/2560.
 */

public class NumberTextWatcherForThousand implements TextWatcher {

    EditText editText;
    Button btn;


    public NumberTextWatcherForThousand(EditText editText) {
        this.editText = editText;


    }

    public NumberTextWatcherForThousand(EditText editText,Button btn) {
        this.editText = editText;
        this.btn = btn;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s)
    {
        editText.removeTextChangedListener(this);

        try {
            String originalString = s.toString();

            Long longval;
            if (originalString.contains(",")) {
                originalString = originalString.replaceAll(",", "");
            }
            longval = Long.parseLong(originalString);

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,###,###,###");
            String formattedString = formatter.format(longval);

            //setting text after format to editText
            editText.setText(formattedString);
            editText.setSelection(editText.getText().length());





        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }


        editText.addTextChangedListener(this);

        String str =  s.toString().replaceAll(",","");
        int num=0 ;

        if(!str.equals(""))
            num =  Integer.parseInt( str ) ;

        if(num>0 && num<=999999999)
        {
            btn.setEnabled(true);
            btn.setAlpha(1f);
        }
        else
        {
            btn.setEnabled(false);
            btn.setAlpha(0.5f);
        }

    }

    public static String getDecimalFormattedString(String value)
    {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt( -1 + str1.length()) == '.')
        {
            j--;
            str3 = ".";
        }
        for (int k = j;; k--)
        {
            if (k < 0)
            {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (i == 3)
            {
                str3 = "," + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            i++;
        }

    }

    public static String trimCommaOfString(String string) {
//        String returnString;
        if(string.contains(",")){
            return string.replace(",","");}
        else {
            return string;
        }

    }
}