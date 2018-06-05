package com.eirinitelevantou.drcy.util;

/**
 * Created by eirini.televantou on 14/12/2017.
 */

public class PrefsHelper {
    public static final int AUTH_TYPE_FIREBASE =0;
    public static final int AUTH_TYPE_GOOGLE =1;
    public static final int AUTH_TYPE_FACEBOOK =2;

    public final static String FIRST_RUN = "firstRun";
    public final static String LOGGED_IN = "isLoggedIn";
    public final static String ACCESS_TOKEN = "accessTokenHash";
    public final static String LOGGED_IN_TYPE = "loggedInType";
    public final static String IS_USER_ALWAYS_ANONYMOUS = "isUserAlwaysAnonymous";


       public static boolean isFirstRun() {
        return Prefs.getBoolean(FIRST_RUN, true);
    }

    public static void setFirstRun(boolean value) {
        Prefs.putBoolean(FIRST_RUN, value);
    }


    public static boolean isLoggedIn() {
        return Prefs.getBoolean(LOGGED_IN, false);
    }

    public static void setLoggedIn(boolean value, int type) {
        Prefs.putBoolean(LOGGED_IN, value);
        setLoggedInType(type);
    }


    public static boolean isUserAlwaysAnonymous() {
        return Prefs.getBoolean(IS_USER_ALWAYS_ANONYMOUS, false);
    }

    public static void setUserAlwaysAnonymous(boolean value) {
        Prefs.putBoolean(IS_USER_ALWAYS_ANONYMOUS, value);
    }

    public static int getLoggedInType() {
        return Prefs.getInt(LOGGED_IN_TYPE, -1);
    }

    public static void setLoggedInType(int value) {
        Prefs.putInt(LOGGED_IN_TYPE, value);
    }
}
