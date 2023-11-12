package com.example.otp1r4.model;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {
    private static ResourceBundle bundle;

    public static void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("TextResources", locale);
    }

    public static String getString(String key) {
        return bundle.getString(key);
    }
}
