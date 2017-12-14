package com.eirinitelevantou.drcy.util;

/**
 * Created by eirini.televantou on 14/12/2017.
 */

public class PrefsHelper {
    public final static String FIRST_RUN = "firstRun";
    public final static String LOGGED_IN = "isLoggedIn";
    public final static String ACCESS_TOKEN = "accessTokenHash";


    public static boolean isLoggedIn() {
        return Prefs.getBoolean(LOGGED_IN, false);
    }

    public static void setLoggedIn(boolean value) {
        Prefs.putBoolean(LOGGED_IN, value);
    }
}
