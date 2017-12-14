package com.eirinitelevantou.drcy.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eirini.televantou on 14/12/2017.
 */

public class ProjectUtils {

    public static String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capitalizeGr(capMatcher.appendTail(capBuffer).toString());
    }

    public static String capitalizeGr(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([α-ω])([ά-ώ]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

//        capMatcher = Pattern.compile("([ά-ώ])([ά-ώ]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
//        while (capMatcher.find()){
//            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
//        }


        return capMatcher.appendTail(capBuffer).toString();
    }
}
