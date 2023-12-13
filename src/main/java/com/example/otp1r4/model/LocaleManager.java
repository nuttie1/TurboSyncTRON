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
        String userLanguage = dao.getLanguage(userData.getUsername());
        if (userLanguage != null) {
            switch (userLanguage) {
                case "Finnish":
                    return "fi";
                case "English":
                    return "en";
                case "Chinese":
                    return "cn";
                case "Divehi":
                    return "di";
                default:
                    return "fi";
            }
        } else {
            return "fi";
        }
    }

}
