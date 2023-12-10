package com.example.otp1r4.model;

import com.example.otp1r4.dao.UserDAO;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager {
    private static final LocaleManager instance;

    static {
        try {
            instance = new LocaleManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Locale currentLocale;
    private ResourceBundle bundle;
    UserDAO dao = new UserDAO();
    private UserData userData = UserData.getInstance();

    private LocaleManager() throws SQLException {
        setDefaultLocaleFromDatabase();
    }

    public static LocaleManager getInstance() {
        return instance;
    }

    public void setLocale(Locale locale) {
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("lang", currentLocale);
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    private void setDefaultLocaleFromDatabase() throws SQLException {
        try {
            String defaultLanguage = getLanguageFromDatabase();
            if (defaultLanguage != null && !defaultLanguage.isEmpty()) {
                currentLocale = new Locale(defaultLanguage);
                ResourceBundle.clearCache();
            } else {
                currentLocale = Locale.getDefault();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to set default locale from database", e);
        }

        bundle = ResourceBundle.getBundle("lang", currentLocale);
    }

    private String getLanguageFromDatabase() throws SQLException {
        if (dao.getLanguage(userData.getUsername()).equals("Finnish")) {
            return "fi";
        } else if (dao.getLanguage(userData.getUsername()).equals("English")) {
            return "en";
        } else if (dao.getLanguage(userData.getUsername()).equals("Chinese")) {
            return "cn";
        } else if (dao.getLanguage(userData.getUsername()).equals("Divehi")) {
            return "di";
        }
        return "fi";
    }

}
