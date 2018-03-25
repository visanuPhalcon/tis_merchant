package com.tis.merchant.app.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nanthakorn on 12/27/2016.
 */

public class FormatUtility {

    private static final int CITIZEN_ID_LENGTH = 13;
    private static final int PHONE_NUMBER_LENGTH = 10;
    private static final int CARD_NUMBER_LENGTH = 16;

    public  boolean UsernameValidate(String username) {
        if (username.matches("^[-a-zA-Z0-9]+") && (username.length() > 3)){
            return true;
        }else{
            return false;
        }
    }

    public static String getBalanceFormat(String value) {
        value = value.substring(0,value.indexOf('.'));

        return value;
    }


    public static String currencyFormat(String value) {
        if (isNotEmpty(value)) {
            Double number = Double.parseDouble(value.replaceAll("[,. ]", ""));
            DecimalFormatSymbols symbol = new DecimalFormatSymbols(Locale.getDefault());
            symbol.setGroupingSeparator(',');
            DecimalFormat format = new DecimalFormat("#,###,###,###", symbol);
            return format.format(number);
        }
        return "0";
    }



    public  boolean PasssportValidate(String passSport){
        if (passSport.matches("^[-A-Z0-9]+") && (passSport.length() > 12) && (passSport.length() < 14)){
            return true;
        }else {
            return false;
        }
    }


    public static String CardNumberFormat(String cardNumber) {
        cardNumber = rewordText(cardNumber);
        if (isNotEmpty(cardNumber) && isCardNumberLength(cardNumber) && numberValidate(cardNumber)) {

            return new StringBuilder(cardNumber)
                    .insert(4, "-")
                    .insert(8, "-")
                    .insert(12, "-")
                    .toString();
        }
        return cardNumber;
    }

    public static String citizenIDFormat(String citizenId) {
        citizenId = rewordText(citizenId);

        if (isNotEmpty(citizenId)
                && isCitizenIdLength(citizenId)
                && numberValidate(citizenId)) {

            return new StringBuilder(citizenId)
                    .insert(1, "-")
                    .insert(6, "-")
                    .insert(12, "-")
                    .insert(15, "-")
                    .toString();
        }
        return citizenId;
    }

    public static String phoneNumberFormat(String phoneNumber) {
        phoneNumber = rewordText(phoneNumber);

        if (isNotEmpty(phoneNumber)
                && isPhoneNumberLength(phoneNumber)
                && numberValidate(phoneNumber)) {

            return new StringBuilder(phoneNumber)
                    .insert(3, "-")
                    .insert(7, "-")
                    .toString();
        }
        return phoneNumber;
    }

    private static boolean isCitizenIdLength(String citizenId) {
        return citizenId.length() == CITIZEN_ID_LENGTH;
    }

    private static boolean isCardNumberLength(String cardNumber) {
        return cardNumber.length() == CARD_NUMBER_LENGTH;
    }

    private static boolean isPhoneNumberLength(String phoneNumber) {
        return phoneNumber.length() == PHONE_NUMBER_LENGTH;
    }

    private static boolean isNotEmpty(String text) {
        return !text.isEmpty();
    }

    public static boolean isEmailFormat(String string) {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    private static boolean numberValidate(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (!Pattern.compile("[0-9]").matcher(String.valueOf(text.charAt(i))).matches()) {
                return false;
            }
        }
        return true;
    }

    public static String rewordText(String text) {
        return text.replaceAll("-", "");
    }
}
